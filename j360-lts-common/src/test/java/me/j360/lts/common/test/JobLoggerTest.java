package me.j360.lts.common.test;

import me.j360.lts.biz.logger.JobLogPo;
import me.j360.lts.biz.logger.JobLogger;
import me.j360.lts.biz.logger.JobLoggerDelegate;
import me.j360.lts.biz.logger.JobLoggerFactory;
import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.support.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with j360-lts -> me.j360.lts.common.test.
 * User: min_xu
 * Date: 2015/11/12
 * Time: 15:54
 * 说明：
 */
public class JobLoggerTest {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("lts.logger","jdk");

        JobLoggerFactory jobLoggerFactory = ExtensionLoader.getExtensionLoader(JobLoggerFactory.class).getAdaptiveExtension();
        Config config = new Config();
        config.setParameter("job.logger","console");

        JobLogger jobLogger = jobLoggerFactory.getJobLogger(config);

        List<JobLogPo> list = new ArrayList<JobLogPo>();
        for(int i =0;i<=10;i++){
            JobLogPo jobLogPo = new JobLogPo();
            jobLogPo.setMsg("hello");
            list.add(jobLogPo);
        }
        jobLogger.log(list);
        TimeUnit.SECONDS.sleep(5);
        JobLoggerDelegate jobLoggerDelegate = new JobLoggerDelegate(config);
        jobLoggerDelegate.log(list);


    }
}
