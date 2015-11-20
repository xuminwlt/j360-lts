package me.j360.lts.jobtrack.command;

import me.j360.lts.command.CommandProcessor;
import me.j360.lts.command.CommandRequest;
import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.jobtrack.JobTrackerApplication;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 给JobTracker发送信号，加载任务
 *
 * @author Robert HG (254963746@qq.com) on 10/26/15.
 */
public class LoadJobCommand implements CommandProcessor {

    private final Logger LOGGER = LoggerFactory.getLogger(LoadJobCommand.class);

    private JobTrackerApplication application;

    public LoadJobCommand(JobTrackerApplication application) {
        this.application = application;
    }

    @Override
    public void execute(OutputStream out, CommandRequest request) throws Exception {
        PrintWriter writer = new PrintWriter(out);

        String taskTrackerNodeGroup = request.getParam("nodeGroup");
        //application.getPreLoader().load(taskTrackerNodeGroup);

        LOGGER.info("load job succeed : nodeGroup={}", taskTrackerNodeGroup);

        writer.println("true");

        writer.flush();
    }
}
