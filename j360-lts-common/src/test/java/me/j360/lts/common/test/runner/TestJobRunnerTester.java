package me.j360.lts.common.test.runner;


import me.j360.lts.common.support.Job;
import me.j360.lts.common.utils.JSONUtils;
import me.j360.lts.runner.JobRunner;
import me.j360.lts.runner.JobRunnerTester;
import me.j360.lts.runner.Result;

/**
 * @author Robert HG (254963746@qq.com) on 9/13/15.
 */
public class TestJobRunnerTester extends JobRunnerTester {

    public static void main(String[] args) throws Throwable {
        //  Mock Job 数据
        Job job = new Job();
        job.setTaskId("2313213");
        // 运行测试
        TestJobRunnerTester tester = new TestJobRunnerTester();
        Result result = tester.run(job);
        System.out.println(JSONUtils.toJSONString(result));
    }

    @Override
    protected void initContext() {
        // TODO 初始化Spring容器
    }

    @Override
    protected JobRunner newJobRunner() {
        return new TestJobRunner();
    }
}
