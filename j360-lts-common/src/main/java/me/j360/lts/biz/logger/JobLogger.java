package me.j360.lts.biz.logger;


import me.j360.lts.common.extension.SPI;

import java.util.List;

/**
 * 执行任务日志记录器
 *
 * @author Robert HG (254963746@qq.com) on 3/24/15.
 */
@SPI("console")
public interface JobLogger {

    public void log(JobLogPo jobLogPo);

    public void log(List<JobLogPo> jobLogPos);

}