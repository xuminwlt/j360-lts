package me.j360.lts.common.support;


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


}
