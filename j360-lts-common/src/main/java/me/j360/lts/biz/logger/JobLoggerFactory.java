package me.j360.lts.biz.logger;


import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
@SPI("console")
public interface JobLoggerFactory {

    @Adaptive("job.logger")
    JobLogger getJobLogger(Config config);

}
