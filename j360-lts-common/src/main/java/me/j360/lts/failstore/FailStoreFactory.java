package me.j360.lts.failstore;

import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

/**
 * Robert HG (254963746@qq.com) on 5/21/15.
 */
@SPI("leveldb")
public interface FailStoreFactory {

    @Adaptive("job.fail.store")
    public FailStore getFailStore(Config config, String storePath);

}
