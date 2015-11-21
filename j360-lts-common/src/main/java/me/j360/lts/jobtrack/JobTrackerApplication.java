package me.j360.lts.jobtrack;

import me.j360.lts.biz.logger.JobLogger;
import me.j360.lts.common.remoting.RemotingServerDelegate;
import me.j360.lts.common.support.Application;
import me.j360.lts.jobtrack.channel.ChannelManager;
import me.j360.lts.queue.PreLoader;

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

    public PreLoader getPreLoader() {
        return preLoader;
    }

    public void setPreLoader(PreLoader preLoader) {
        this.preLoader = preLoader;
    }

    private PreLoader preLoader;
}
