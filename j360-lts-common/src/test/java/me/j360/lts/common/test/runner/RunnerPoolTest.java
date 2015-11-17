package me.j360.lts.common.test.runner;

import me.j360.lts.biz.logger.JobLoggerFactory;
import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.support.Config;
import me.j360.lts.common.support.Job;
import me.j360.lts.common.support.JobWrapper;
import me.j360.lts.common.support.SystemClock;
import me.j360.lts.ec.EventCenterFactory;
import me.j360.lts.runner.DefaultRunnerFactory;
import me.j360.lts.runner.RunnerCallback;
import me.j360.lts.runner.RunnerFactory;
import me.j360.lts.runner.RunnerPool;
import me.j360.lts.runner.domain.Response;
import me.j360.lts.runner.domain.TaskTrackerApplication;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with j360-lts -> me.j360.lts.common.test.runner.
 * User: min_xu
 * Date: 2015/11/17
 * Time: 10:55
 * 说明：执行顺序
 * 1、初始化线程池 RunningPool
 * 2、创建一个待执行的job
 * 3、线程池执行Job，并执行job中的RunningDelegate
 * 4、RunningDelegate会去执行JobRunner的实现类TestJobRunner
 * 5、执行回调callback
 */
public class RunnerPoolTest {

    private AtomicInteger c = new AtomicInteger();

    @Test
    public void poolTest() throws InterruptedException {
        System.setProperty("lts.logger","jdk");

        Config config = new Config();
        config.setWorkThreads(2);
        config.setIdentity("node-001");
        TaskTrackerApplication application = new TaskTrackerApplication();
        application.setConfig(config);
        application.setJobRunnerClass(new TestJobRunner().getClass());
        application.setRunnerFactory(new DefaultRunnerFactory(application));

        EventCenterFactory eventCenterFactory = ExtensionLoader
                .getExtensionLoader(EventCenterFactory.class).getAdaptiveExtension();
        application.setEventCenter(eventCenterFactory.getEventCenter(config));

        application.setRunnerPool(new RunnerPool(application));
        //初始化好pool

        //开始执行--job--
        // 线程安全的
        JobRunnerCallback jobRunnerCallback = new JobRunnerCallback();
        final JobWrapper jobWrapper = new JobWrapper();
        Job job = new Job();
        job.setCronExpression("1,2,3");
        job.setParam("234","23");
        job.setRetryTimes(3);
        job.setTaskId("232323");
        jobWrapper.setJob(job);
        jobWrapper.setJobId("job-001");
        application.getRunnerPool().execute(jobWrapper,jobRunnerCallback);
        //拉去runner中的队列
        boolean running = application.getRunnerPool().getRunningJobManager().running("job-001");
        System.out.println(running);

        TimeUnit.SECONDS.sleep(1);
        application.getRunnerPool().execute(jobWrapper,jobRunnerCallback);
        TimeUnit.SECONDS.sleep(1);



        TimeUnit.SECONDS.sleep(1);
    }


    /**
     * 任务执行的回调(任务执行完之后线程回调这个函数)
     */
    private class JobRunnerCallback implements RunnerCallback {
        @Override
        public JobWrapper runComplete(Response response) {
            final Response returnResponse = new Response();

            System.out.println(c.incrementAndGet());
            return returnResponse.getJobWrapper();
        }
    }

    /**
     * 设置JobRunner工场类，一般用户不用调用

    public void setRunnerFactory(RunnerFactory factory) {
        application.setRunnerFactory(factory);
    }*/
}
