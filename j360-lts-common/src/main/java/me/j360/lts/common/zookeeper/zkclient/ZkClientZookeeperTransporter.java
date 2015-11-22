package me.j360.lts.common.zookeeper.zkclient;


import me.j360.lts.common.support.Config;
import me.j360.lts.common.zookeeper.ZookeeperClient;
import me.j360.lts.common.zookeeper.ZookeeperTransporter;

public class ZkClientZookeeperTransporter implements ZookeeperTransporter {

    public ZookeeperClient connect(Config config) {
        return new ZkClientZookeeperClient(config);
    }

}
