package me.j360.lts.common.test.remote.server;

import me.j360.lts.biz.logger.JobLogger;
import me.j360.lts.common.remoting.RemotingServerDelegate;
import me.j360.lts.common.support.Application;
import me.j360.lts.common.test.remote.server.channel.ChannelManager;

/**
 * JobTracker Application
 *
 * @author Robert HG (254963746@qq.com) on 3/30/15.
 */
public class JobTrackerApplication extends Application {

    private RemotingServerDelegate remotingServer;
    // channel manager
    private ChannelManager channelManager;

    // biz logger
    private JobLogger jobLogger;


    public JobLogger getJobLogger() {
        return jobLogger;
    }

    public void setJobLogger(JobLogger jobLogger) {
        this.jobLogger = jobLogger;
    }



    public RemotingServerDelegate getRemotingServer() {
        return remotingServer;
    }

    public void setRemotingServer(RemotingServerDelegate remotingServer) {
        this.remotingServer = remotingServer;
    }

    public ChannelManager getChannelManager() {
        return channelManager;
    }

    public void setChannelManager(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }

}
