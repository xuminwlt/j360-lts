package me.j360.lts.queue;


import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 6/7/15.
 */
@SPI("mysql")
public interface NodeGroupStoreFactory {

    @Adaptive("job.queue")
    NodeGroupStore getStore(Config config);
}
