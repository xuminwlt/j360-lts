package me.j360.lts.common.test.remote;

import me.j360.lts.biz.logger.JobLoggerDelegate;
import me.j360.lts.command.CommandCenter;
import me.j360.lts.command.Commands;
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
import me.j360.lts.jobtrack.JobTrackerApplication;
import me.j360.lts.common.utils.NamedThreadFactory;
import me.j360.lts.common.utils.StringUtils;
import me.j360.lts.ec.EventCenter;
import me.j360.lts.ec.EventInfo;
import me.j360.lts.ec.EventSubscriber;
import me.j360.lts.ec.Observer;
import me.j360.lts.ec.injvm.InJvmEventCenterFactory;
import me.j360.lts.jobtrack.channel.ChannelManager;
import me.j360.lts.jobtrack.command.AddJobCommand;
import me.j360.lts.jobtrack.command.LoadJobCommand;
import me.j360.lts.queue.PreLoaderFactory;
import me.j360.lts.remote.*;
import me.j360.lts.remote.protocol.CommandBodyWrapper;
import com.lts.core.protocol.command.JobSubmitRequest;
import com.lts.core.protocol.command.JobSubmitResponse;
import me.j360.lts.remote.protocol.RemotingCommand;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with j360-lts -> me.j360.lts.common.test.remote.
 * User: min_xu
 * Date: 2015/11/18
 * Time: 22:58
 * 说明：
 */
public class RemoteServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServerTest.class);

    //必须使用main方法启动
    //模拟AbstractServerNode调用RemoteServerDeleagate
    public static void main(String[] args) throws InterruptedException {
        RemotingTransporter remotingTransporter = ExtensionLoader
                .getExtensionLoader(RemotingTransporter.class).getAdaptiveExtension();
        PreLoaderFactory preLoaderFactory =
                ExtensionLoader.getExtensionLoader(PreLoaderFactory.class).getAdaptiveExtension();

        Config config = new Config();
        config.setListenPort(35001);
        config.setNodeType(NodeType.JOB_TRACKER);
        config.setNodeGroup("lts");
        config.setClusterName("test_cluster");
        config.setIdentity(StringUtils.generateUUID());
        config.setInvokeTimeoutMillis(60000);

        config.setParameter("job.queue", "mysql");
        config.setParameter("jdbc.url", "jdbc:mysql://127.0.0.1:3306/lts");
        config.setParameter("jdbc.username", "root");
        config.setParameter("jdbc.password", "root");

        JobTrackerApplication application = new JobTrackerApplication();
        application.setConfig(config);


        // 监控中心
        //application.setMonitor(new JobTrackerMonitor(application));
        // channel 管理者
        application.setChannelManager(new ChannelManager());
        // JobClient 管理者
        //application.setJobClientManager(new JobClientManager(application));
        // TaskTracker 管理者
        //application.setTaskTrackerManager(new TaskTrackerManager(application));
        // 命令中心
        application.setCommandCenter(new CommandCenter(application.getConfig()));

        RemotingServerConfig remotingServerConfig = new RemotingServerConfig();
        // config 配置
        if (config.getListenPort() == 0) {
            config.setListenPort(Constants.JOB_TRACKER_DEFAULT_LISTEN_PORT);
            //node.setPort(config.getListenPort());
        }
        remotingServerConfig.setListenPort(config.getListenPort());

        RemotingServerDelegate remotingServer = new RemotingServerDelegate(remotingTransporter.getRemotingServer(config, remotingServerConfig), application);

        //JobTracker -> beforeStart();
        application.setRemotingServer(remotingServer);
        application.setJobLogger(new JobLoggerDelegate(config));
        application.setPreLoader(preLoaderFactory.getPreLoader(config, application));

        //register CommandCenter start
        application.getCommandCenter().start();

        // 设置command端口，会暴露到注册中心上
        //node.setCommandPort(application.getCommandCenter().getPort());
        // 手动加载任务
        application.getCommandCenter().registerCommand(Commands.LOAD_JOB, new LoadJobCommand(application));
        // 添加任务
        application.getCommandCenter().registerCommand(Commands.ADD_JOB, new AddJobCommand(application));

        //remote start
        remotingServer.start();

        RemotingProcessor defaultProcessor = new me.j360.lts.common.test.remote.server.RemotingDispatcher(application);
        if (defaultProcessor != null) {
            int processorSize = config.getParameter(Constants.PROCESSOR_THREAD, Constants.DEFAULT_PROCESSOR_THREAD);
            remotingServer.registerDefaultProcessor(defaultProcessor,
                    Executors.newFixedThreadPool(processorSize, new NamedThreadFactory(RemoteServerTest.class.getSimpleName())));
        }


        //after remote start
        application.getChannelManager().start();
        //application.getMonitor().start();
        TimeUnit.SECONDS.sleep(60);


    }

    private PreLoaderFactory preLoaderFactory =
            ExtensionLoader.getExtensionLoader(PreLoaderFactory.class).getAdaptiveExtension();

}
