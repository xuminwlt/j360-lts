package me.j360.lts.common.support;


import me.j360.lts.common.cluster.SubscribedNodeManager;
import me.j360.lts.ec.EventCenter;
import me.j360.lts.remote.protocol.CommandBodyWrapper;

/**
 * @author Robert HG (254963746@qq.com) on 8/17/14.
 *         用来存储 程序的数据
 */
public abstract class Application {

    // 节点配置信息
    private Config config;


    public CommandBodyWrapper getCommandBodyWrapper() {
        return commandBodyWrapper;
    }

    public void setCommandBodyWrapper(CommandBodyWrapper commandBodyWrapper) {
        this.commandBodyWrapper = commandBodyWrapper;
    }

    // 节点通信CommandBody包装器
    private CommandBodyWrapper commandBodyWrapper;

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public EventCenter getEventCenter() {
        return eventCenter;
    }

    public void setEventCenter(EventCenter eventCenter) {
        this.eventCenter = eventCenter;
    }

    // 事件中心
    private EventCenter eventCenter;


    public SubscribedNodeManager getSubscribedNodeManager() {
        return subscribedNodeManager;
    }

    public void setSubscribedNodeManager(SubscribedNodeManager subscribedNodeManager) {
        this.subscribedNodeManager = subscribedNodeManager;
    }

    // 节点管理
    private SubscribedNodeManager subscribedNodeManager;

}
