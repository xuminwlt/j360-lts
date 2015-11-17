package me.j360.lts.common.support;


import me.j360.lts.ec.EventCenter;

/**
 * @author Robert HG (254963746@qq.com) on 8/17/14.
 *         用来存储 程序的数据
 */
public abstract class Application {

    // 节点配置信息
    private Config config;


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

}
