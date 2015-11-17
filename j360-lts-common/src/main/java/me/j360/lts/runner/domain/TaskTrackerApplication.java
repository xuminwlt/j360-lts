package me.j360.lts.runner.domain;


import me.j360.lts.common.constant.Level;
import me.j360.lts.common.support.Application;
import me.j360.lts.runner.RunnerFactory;
import me.j360.lts.runner.RunnerPool;
import me.j360.lts.runner.support.JobPullMachine;

/**
 * @author Robert HG (254963746@qq.com) on 3/30/15.
 */
public class TaskTrackerApplication extends Application {

    // runner 线程池
    private RunnerPool runnerPool;
    //
    private RunnerFactory runnerFactory;
    // Pull Job Machine
    private JobPullMachine jobPullMachine;
    /**
     * 业务日志记录级别
     */
    private Level bizLogLevel;
    /**
     * 执行任务的class
     */
    private Class jobRunnerClass;


    public RunnerPool getRunnerPool() {
        return runnerPool;
    }

    public void setRunnerPool(RunnerPool runnerPool) {
        this.runnerPool = runnerPool;
    }

    public Level getBizLogLevel() {
        return bizLogLevel;
    }

    public void setBizLogLevel(Level bizLogLevel) {
        this.bizLogLevel = bizLogLevel;
    }

    public Class getJobRunnerClass() {
        return jobRunnerClass;
    }

    public void setJobRunnerClass(Class jobRunnerClass) {
        this.jobRunnerClass = jobRunnerClass;
    }

    public RunnerFactory getRunnerFactory() {
        return runnerFactory;
    }

    public void setRunnerFactory(RunnerFactory runnerFactory) {
        this.runnerFactory = runnerFactory;
    }

    public JobPullMachine getJobPullMachine() {
        return jobPullMachine;
    }

    public void setJobPullMachine(JobPullMachine jobPullMachine) {
        this.jobPullMachine = jobPullMachine;
    }

}