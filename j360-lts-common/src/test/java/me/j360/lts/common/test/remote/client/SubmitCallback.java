package me.j360.lts.common.test.remote.client;

import me.j360.lts.remote.protocol.RemotingCommand;

/**
 * @author Robert HG (254963746@qq.com) on 5/30/15.
 */
public interface SubmitCallback {

    public void call(final RemotingCommand responseCommand);

}
