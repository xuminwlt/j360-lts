package me.j360.lts.common.zookeeper;


import me.j360.lts.common.constant.Constants;
import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

@SPI("zkclient")
public interface ZookeeperTransporter {

    @Adaptive({Constants.ZK_CLIENT_KEY})
    ZookeeperClient connect(Config config);

}
