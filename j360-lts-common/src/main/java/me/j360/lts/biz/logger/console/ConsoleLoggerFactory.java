package me.j360.lts.biz.logger.console;


import me.j360.lts.biz.logger.JobLogger;
import me.j360.lts.biz.logger.JobLoggerFactory;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
public class ConsoleLoggerFactory implements JobLoggerFactory {
    @Override
    public JobLogger getJobLogger(Config config) {
        return new ConsoleJobLogger();
    }
}
