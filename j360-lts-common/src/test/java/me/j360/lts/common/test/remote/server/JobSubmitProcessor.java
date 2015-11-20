package me.j360.lts.common.test.remote.server;

import me.j360.lts.common.protocol.JobProtos;
import me.j360.lts.jobtrack.JobTrackerApplication;
import me.j360.lts.remote.Channel;
import me.j360.lts.remote.exception.RemotingCommandException;
import me.j360.lts.remote.protocol.RemotingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert HG (254963746@qq.com) on 7/24/14.
 *         客户端提交任务的处理器
 */
public class JobSubmitProcessor extends AbstractRemotingProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobSubmitProcessor.class);

    public JobSubmitProcessor(JobTrackerApplication application) {
        super(application);
    }

    @Override
    public RemotingCommand processRequest(Channel channel, RemotingCommand request) throws RemotingCommandException {

        RemotingCommand response = null;
        response = RemotingCommand.createResponseCommand(
                JobProtos.ResponseCode.JOB_RECEIVE_SUCCESS.code(), "job submit success!", null);
        return response;
    }
}
