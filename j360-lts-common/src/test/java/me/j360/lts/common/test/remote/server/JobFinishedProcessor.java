package me.j360.lts.common.test.remote.server;

import me.j360.lts.common.constant.Constants;
import me.j360.lts.common.utils.CollectionUtils;
import me.j360.lts.remote.Channel;
import me.j360.lts.remote.exception.RemotingCommandException;
import me.j360.lts.remote.protocol.RemotingCommand;
import me.j360.lts.remote.protocol.RemotingProtos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert HG (254963746@qq.com) on 8/17/14.
 *         TaskTracker 完成任务 的处理器
 */
public class JobFinishedProcessor extends AbstractRemotingProcessor {


    private static final Logger LOGGER = LoggerFactory.getLogger(JobFinishedProcessor.class);
    // 任务的最大重试次数
    private final Integer maxRetryTimes;

    public JobFinishedProcessor(final JobTrackerApplication application) {
        super(application);
        this.maxRetryTimes = application.getConfig().getParameter(Constants.JOB_MAX_RETRY_TIMES,
                Constants.DEFAULT_JOB_MAX_RETRY_TIMES);

    }

    @Override
    public RemotingCommand processRequest(Channel channel, RemotingCommand request)
            throws RemotingCommandException {


        // 4. process
        return RemotingCommand.createResponseCommand(RemotingProtos
                .ResponseCode.SUCCESS.code());
    }


}
