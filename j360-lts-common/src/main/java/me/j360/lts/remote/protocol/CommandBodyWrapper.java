package me.j360.lts.remote.protocol;

import me.j360.lts.common.protocol.AbstractRemotingCommandBody;
import me.j360.lts.common.support.Application;
import me.j360.lts.common.support.Config;

/**
 * 用于设置CommandBody 的基础信息
 * Robert HG (254963746@qq.com) on 3/13/15.
 */
public class CommandBodyWrapper {

    private Config config;

    public CommandBodyWrapper(Config config) {
        this.config = config;
    }

    public <T extends AbstractRemotingCommandBody> T wrapper(T commandBody) {
        commandBody.setNodeGroup(config.getNodeGroup());
        commandBody.setNodeType(config.getNodeType().name());
        commandBody.setIdentity(config.getIdentity());
        return commandBody;
    }

    public static <T extends AbstractRemotingCommandBody> T wrapper(Application application, T commandBody) {
        return application.getCommandBodyWrapper().wrapper(commandBody);
    }

}
