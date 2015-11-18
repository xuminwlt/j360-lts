package me.j360.lts.common.loadbalance;

import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

import java.util.List;

/**
 * Robert HG (254963746@qq.com) on 3/25/15.
 */
@SPI("random")
public interface LoadBalance {

    @Adaptive("loadbalance")
    public <S> S select(Config config, List<S> shards, String seed);

}
