package me.j360.lts.queue.mysql;

import me.j360.lts.common.support.Config;
import me.j360.lts.queue.NodeGroupStore;
import me.j360.lts.queue.NodeGroupStoreFactory;

/**
 * @author Robert HG (254963746@qq.com) on 6/7/15.
 */
public class MysqlNodeGroupStoreFactory implements NodeGroupStoreFactory {

    @Override
    public NodeGroupStore getStore(Config config) {
        return new MysqlNodeGroupStore(config);
    }
}
