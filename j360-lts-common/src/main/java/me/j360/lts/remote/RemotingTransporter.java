package me.j360.lts.remote;

import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 11/6/15.
 */
@SPI("netty")
public interface RemotingTransporter {

    @Adaptive("lts.remoting")
    RemotingServer getRemotingServer(Config config, RemotingServerConfig remotingServerConfig);

    @Adaptive("lts.remoting")
    RemotingClient getRemotingClient(Config config, RemotingClientConfig remotingClientConfig);

}
