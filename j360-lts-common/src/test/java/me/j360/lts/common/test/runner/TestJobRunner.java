package me.j360.lts.common.test.runner;


import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.common.support.Job;
import me.j360.lts.runner.Action;
import me.j360.lts.runner.JobRunner;
import me.j360.lts.runner.Result;

/**
 * @author Robert HG (254963746@qq.com) on 8/19/14.
 */
public class TestJobRunner implements JobRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestJobRunner.class);
    //private final static BizLogger bizLogger = LtsLoggerFactory.getBizLogger();
    //private final static BizLogger bizLogger = LtsLoggerFactory.getBizLogger();

    @Override
    public Result run(Job job) throws Throwable {
        try {
//            Thread.sleep(1000L);
//
//            if (job.getRetryTimes() > 5) {
//                return new Result(Action.EXECUTE_FAILED, "重试次数超过5次了，放过你吧!");
//            }
//
//            if (SystemClock.now() % 2 == 1) {
//                return new Result(Action.EXECUTE_LATER, "稍后执行");
//            }

            // TODO 业务逻辑
            LOGGER.info("我要执行：" + job);
            // 会发送到 LTS (JobTracker上)
            //bizLogger.info("测试，业务日志啊啊啊啊啊");

        } catch (Exception e) {
            LOGGER.info("Run job failed!", e);
            return new Result(Action.EXECUTE_FAILED, e.getMessage());
        }
        return new Result(Action.EXECUTE_SUCCESS, "执行成功了，哈哈");
    }
}
