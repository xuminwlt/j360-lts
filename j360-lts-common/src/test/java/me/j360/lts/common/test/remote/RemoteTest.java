package me.j360.lts.common.test.remote;

import me.j360.lts.common.cluster.SubscribedNodeManager;
import me.j360.lts.common.constant.Constants;
import me.j360.lts.common.exception.JobTrackerNotFoundException;
import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.remoting.RemotingClientDelegate;
import me.j360.lts.common.remoting.RemotingServerDelegate;
import me.j360.lts.common.support.Config;
import me.j360.lts.common.test.remote.client.JobClientApplication;
import me.j360.lts.common.test.remote.client.RemotingDispatcher;
import me.j360.lts.common.test.remote.server.JobTrackerApplication;
import me.j360.lts.common.utils.NamedThreadFactory;
import me.j360.lts.ec.EventCenter;
import me.j360.lts.ec.EventInfo;
import me.j360.lts.ec.EventSubscriber;
import me.j360.lts.ec.Observer;
import me.j360.lts.ec.injvm.InJvmEventCenterFactory;
import me.j360.lts.remote.*;
import me.j360.lts.remote.protocol.RemotingCommand;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with j360-lts -> me.j360.lts.common.test.remote.
 * User: min_xu
 * Date: 2015/11/18
 * Time: 22:58
 * 说明：
 */
public class RemoteTest {

    protected RemotingTransporter remotingTransporter = ExtensionLoader
            .getExtensionLoader(RemotingTransporter.class).getAdaptiveExtension();

    //模拟AbstractClientNode调用RemoteClientDeleagate
    @Test
    public void clientRemoteTest() throws JobTrackerNotFoundException {
        System.setProperty("lts.logger","jdk");

        Config config = new Config();
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

        //发送
        RemotingCommand requestCommand = RemotingCommand.createResponseCommand(1);
        remotingClient.invokeSync(requestCommand);


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
