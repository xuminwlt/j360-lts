package me.j360.lts.common.test.remote.server;

import me.j360.lts.jobtrack.JobTrackerApplication;
import me.j360.lts.remote.RemotingProcessor;

/**
 * @author Robert HG (254963746@qq.com) on 8/16/14.
 */
public abstract class AbstractRemotingProcessor implements RemotingProcessor {

    protected JobTrackerApplication application;

    public AbstractRemotingProcessor(JobTrackerApplication application) {
        this.application = application;
    }

}
