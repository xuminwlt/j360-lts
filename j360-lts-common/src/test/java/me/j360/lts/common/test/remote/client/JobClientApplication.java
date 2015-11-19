package me.j360.lts.common.test.remote.client;

import me.j360.lts.common.remoting.RemotingClientDelegate;
import me.j360.lts.common.support.Application;

/**
 * @author Robert HG (254963746@qq.com) on 3/30/15.
 */
public class JobClientApplication extends Application {

    private RemotingClientDelegate remotingClient;

    public RemotingClientDelegate getRemotingClient() {
        return remotingClient;
    }

    public void setRemotingClient(RemotingClientDelegate remotingClient) {
        this.remotingClient = remotingClient;
    }
}

