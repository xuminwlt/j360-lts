package me.j360.lts.runner;

import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.runner.domain.TaskTrackerApplication;

/**
 * @author Robert HG (254963746@qq.com) on 3/6/15.
 */
public class DefaultRunnerFactory implements RunnerFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunnerFactory.class);
    private TaskTrackerApplication application;

    public DefaultRunnerFactory(TaskTrackerApplication application) {
        this.application = application;
    }

    public JobRunner newRunner() {
        try {
            return (JobRunner) application.getJobRunnerClass().newInstance();
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
