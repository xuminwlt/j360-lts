package me.j360.lts.common.test.remote;

import me.j360.lts.common.cluster.Node;
import me.j360.lts.common.cluster.NodeType;
import me.j360.lts.common.cluster.SubscribedNodeManager;
import me.j360.lts.common.constant.Constants;
import me.j360.lts.common.exception.JobTrackerNotFoundException;
import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.common.protocol.JobProtos;
import me.j360.lts.common.remoting.RemotingClientDelegate;
import me.j360.lts.common.remoting.RemotingServerDelegate;
import me.j360.lts.common.support.Config;
import me.j360.lts.common.support.Job;
import me.j360.lts.common.test.remote.client.JobClientApplication;
import me.j360.lts.common.test.remote.client.RemotingDispatcher;
import me.j360.lts.common.test.remote.client.Response;
import me.j360.lts.common.test.remote.client.SubmitCallback;
import me.j360.lts.common.test.remote.server.JobTrackerApplication;
import me.j360.lts.common.utils.NamedThreadFactory;
import me.j360.lts.common.utils.StringUtils;
import me.j360.lts.ec.EventCenter;
import me.j360.lts.ec.EventInfo;
import me.j360.lts.ec.EventSubscriber;
import me.j360.lts.ec.Observer;
import me.j360.lts.ec.injvm.InJvmEventCenterFactory;
import me.j360.lts.remote.*;
import me.j360.lts.remote.protocol.CommandBodyWrapper;
import com.lts.core.protocol.command.JobSubmitRequest;
import com.lts.core.protocol.command.JobSubmitResponse;
import me.j360.lts.remote.protocol.RemotingCommand;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created with j360-lts -> me.j360.lts.common.test.remote.
 * User: min_xu
 * Date: 2015/11/18
 * Time: 22:58
 * 说明：
 */
public class RemoteTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteTest.class);

    protected RemotingTransporter remotingTransporter = ExtensionLoader
            .getExtensionLoader(RemotingTransporter.class).getAdaptiveExtension();

    //模拟AbstractClientNode调用RemoteClientDeleagate
    @Test
    public void clientRemoteTest() throws JobTrackerNotFoundException {
        Config config = new Config();
        config.setNodeType(NodeType.JOB_CLIENT);
        config.setNodeGroup("test_jobClient");
        config.setClusterName("test_cluster");
        config.setIdentity(StringUtils.generateUUID());
        config.setInvokeTimeoutMillis(60000);

        JobClientApplication application = new JobClientApplication();
        application.setConfig(config);

        InJvmEventCenterFactory injvmEventCenterFactory = new InJvmEventCenterFactory();
        EventCenter eventCenter = injvmEventCenterFactory.getEventCenter(config);

        EventSubscriber eventSubscriber = new EventSubscriber("1", new Observer() {
            @Override
            public void onObserved(EventInfo eventInfo) {
                System.out.println(eventInfo.getTopic());
            }
        });
        application.setEventCenter(eventCenter);
        // 订阅的node管理
        SubscribedNodeManager subscribedNodeManager = new SubscribedNodeManager(application);
        application.setSubscribedNodeManager(subscribedNodeManager);

        RemotingClientDelegate remotingClient = new RemotingClientDelegate(remotingTransporter.getRemotingClient(application.getConfig(), new RemotingClientConfig()), application);

        remotingClient.start();

        RemotingProcessor defaultProcessor = new RemotingDispatcher(application);
        if (defaultProcessor != null) {
            int processorSize = application.getConfig().getParameter(Constants.PROCESSOR_THREAD, Constants.DEFAULT_PROCESSOR_THREAD);
            remotingClient.registerDefaultProcessor(defaultProcessor,
                    Executors.newFixedThreadPool(processorSize,
                            new NamedThreadFactory(RemoteTest.class.getSimpleName())));
        }

        //模拟JobTracker注册到RemoteClient里面
        Node jobTracker = new Node();
        jobTracker.setIp("192.168.247.1");
        jobTracker.setPort(35001);
        jobTracker.setAvailable(true);
        jobTracker.setClusterName("test_cluster");
        jobTracker.setGroup("lts");
        jobTracker.setNodeType(NodeType.JOB_TRACKER);
        jobTracker.setIdentity("80EA21AF5DDE4293AAE9853779B80837");

        remotingClient.addJobTracker(jobTracker);

        //封装job
        Job job = new Job();
        job.setTaskId(StringUtils.generateUUID());
        job.setTaskTrackerNodeGroup("test_trade_TaskTracker_0");
        job.setParam("shopId", "111");
        job.setNeedFeedback(false);

        application.setCommandBodyWrapper(new CommandBodyWrapper(config));

        final List<Job> jobs = Collections.singletonList(job);

        //nettyclient发送job到jobtrack（Node jobTracker）==nettyserver
        final Response response = new Response();
        try {
            JobSubmitRequest jobSubmitRequest = CommandBodyWrapper.wrapper(application, new JobSubmitRequest());
            jobSubmitRequest.setJobs(jobs);

            RemotingCommand requestCommand = RemotingCommand.createRequestCommand(
                    JobProtos.RequestCode.SUBMIT_JOB.code(), jobSubmitRequest);

            SubmitCallback submitCallback = new SubmitCallback() {
                @Override
                public void call(RemotingCommand responseCommand) {
                    if (responseCommand == null) {
                        response.setFailedJobs(jobs);
                        response.setSuccess(false);
                        LOGGER.warn("Submit job failed: {}, {}", jobs, "JobTracker is broken");
                        return;
                    }
                    if (JobProtos.ResponseCode.JOB_RECEIVE_SUCCESS.code() == responseCommand.getCode()) {
                        LOGGER.info("Submit job success: {}", jobs);
                        response.setSuccess(true);
                        return;
                    }
                    // 失败的job
                    JobSubmitResponse jobSubmitResponse = responseCommand.getBody();
                    response.setFailedJobs(jobSubmitResponse.getFailedJobs());
                    response.setSuccess(false);
                    response.setCode(JobProtos.ResponseCode.valueOf(responseCommand.getCode()).name());
                    LOGGER.warn("Submit job failed: {}, {}, {}", jobs, responseCommand.getRemark(), jobSubmitResponse.getMsg());
                }
            };

            submitCallback.call(remotingClient.invokeSync(requestCommand));
        }catch (Exception e){
            LOGGER.error(e);
        }
    }

    //模拟AbstractServerNode调用RemoteServerDeleagate
    @Test
    public void serverRemoteTest(){
        Config config = new Config();
        config.setListenPort(50001);
        JobTrackerApplication application = new JobTrackerApplication();
        application.setConfig(config);

        RemotingServerConfig remotingServerConfig = new RemotingServerConfig();
        // config 配置
        if (config.getListenPort() == 0) {
            config.setListenPort(Constants.JOB_TRACKER_DEFAULT_LISTEN_PORT);
            //node.setPort(config.getListenPort());
        }
        remotingServerConfig.setListenPort(config.getListenPort());

        RemotingServerDelegate remotingServer = new RemotingServerDelegate(remotingTransporter.getRemotingServer(config, remotingServerConfig), application);

        remotingServer.start();

        RemotingProcessor defaultProcessor = new me.j360.lts.common.test.remote.server.RemotingDispatcher(application);
        if (defaultProcessor != null) {
            int processorSize = config.getParameter(Constants.PROCESSOR_THREAD, Constants.DEFAULT_PROCESSOR_THREAD);
            remotingServer.registerDefaultProcessor(defaultProcessor,
                    Executors.newFixedThreadPool(processorSize, new NamedThreadFactory(RemoteTest.class.getSimpleName())));
        }

        //
        RemotingCommand requestCommand = RemotingCommand.createResponseCommand(1);
        //remotingServer.invokeSync(null,null)
    }


}
