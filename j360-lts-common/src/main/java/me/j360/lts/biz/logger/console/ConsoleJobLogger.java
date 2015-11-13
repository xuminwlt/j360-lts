package me.j360.lts.biz.logger.console;


import me.j360.lts.biz.logger.JobLogPo;
import me.j360.lts.biz.logger.JobLogger;
import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.common.utils.JSONUtils;

import java.util.List;

/**
 * @author Robert HG (254963746@qq.com) on 3/27/15.
 */
public class ConsoleJobLogger implements JobLogger {

    private Logger LOGGER = LoggerFactory.getLogger(ConsoleJobLogger.class.getSimpleName());

    @Override
    public void log(JobLogPo jobLogPo) {
        LOGGER.info(JSONUtils.toJSONString(jobLogPo));
    }

    @Override
    public void log(List<JobLogPo> jobLogPos) {
        for (JobLogPo jobLogPo : jobLogPos) {
            log(jobLogPo);
        }
    }


}
