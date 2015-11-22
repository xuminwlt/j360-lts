package me.j360.lts.common.zookeeper.curator;

import me.j360.lts.common.support.Config;
import me.j360.lts.common.zookeeper.ZookeeperClient;
import me.j360.lts.common.zookeeper.ZookeeperTransporter;

public class CuratorZookeeperTransporter implements ZookeeperTransporter {

    public ZookeeperClient connect(Config config) {
        return new CuratorZookeeperClient(config);
    }

}