package me.j360.lts.common.register;

import me.j360.lts.common.register.redis.RedisRegistry;
import me.j360.lts.common.register.zookeeper.ZookeeperRegistry;
import me.j360.lts.common.support.Application;
import me.j360.lts.common.utils.StringUtils;

/**
 * @author Robert HG (254963746@qq.com) on 5/17/15.
 */
public class RegistryFactory {

    public static Registry getRegistry(Application application) {

        String address = application.getConfig().getRegistryAddress();
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("address is null£¡");
        }
        if (address.startsWith("zookeeper://")) {
            return new ZookeeperRegistry(application);
        } else if (address.startsWith("redis://")) {
            return new RedisRegistry(application);
        } else if (address.startsWith("multicast://")) {
//            return new MulticastRegistry(config);
        }
        throw new IllegalArgumentException("illegal address protocol");
    }

}
