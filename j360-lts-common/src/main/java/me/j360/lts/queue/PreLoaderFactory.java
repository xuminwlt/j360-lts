package me.j360.lts.queue;

import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Application;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 8/14/15.
 */
@SPI("mysql")
public interface PreLoaderFactory {

    @Adaptive("job.queue")
    public PreLoader getPreLoader(Config config, Application application);

}
