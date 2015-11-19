package me.j360.lts.common.test.remote.client;

import me.j360.lts.common.protocol.JobProtos;
import me.j360.lts.remote.Channel;
import me.j360.lts.remote.RemotingProcessor;
import me.j360.lts.remote.exception.RemotingCommandException;
import me.j360.lts.remote.protocol.RemotingCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Robert HG (254963746@qq.com) on 8/18/14.
 */
public class JobFinishedProcessor implements RemotingProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobFinishedProcessor.class);

    private JobClientApplication application;

    public JobFinishedProcessor(JobClientApplication application) {
        this.application = application;
    }

    @Override
    public RemotingCommand processRequest(Channel Channel, RemotingCommand request)
            throws RemotingCommandException {

        /*JobFinishedRequest requestBody = request.getBody();
        try {
            if (application.getJobFinishedHandler() != null) {
                application.getJobFinishedHandler().handle(requestBody.getJobResults());
            }
        } catch (Exception t) {
            LOGGER.error(t.getMessage(), t);
        }*/

        return RemotingCommand.createResponseCommand(JobProtos.ResponseCode.JOB_NOTIFY_SUCCESS.code(),
                "received successful");
    }
}
