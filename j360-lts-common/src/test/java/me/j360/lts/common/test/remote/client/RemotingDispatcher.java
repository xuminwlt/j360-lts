package me.j360.lts.common.test.remote.client;

import me.j360.lts.common.protocol.JobProtos;
import me.j360.lts.remote.Channel;
import me.j360.lts.remote.RemotingProcessor;
import me.j360.lts.remote.exception.RemotingCommandException;
import me.j360.lts.remote.protocol.RemotingCommand;
import me.j360.lts.remote.protocol.RemotingProtos;

import java.util.HashMap;
import java.util.Map;


import static me.j360.lts.common.protocol.JobProtos.RequestCode.JOB_FINISHED;
import static me.j360.lts.common.protocol.JobProtos.RequestCode.valueOf;

/**
 * @author Robert HG (254963746@qq.com) on 7/25/14.
 *         客户端默认通信处理器
 */
public class RemotingDispatcher implements RemotingProcessor {

    private final Map<JobProtos.RequestCode, RemotingProcessor> processors = new HashMap<JobProtos.RequestCode, RemotingProcessor>();

    public RemotingDispatcher(JobClientApplication application) {
        processors.put(JOB_FINISHED, new JobFinishedProcessor(application));
    }

    @Override
    public RemotingCommand processRequest(Channel channel, RemotingCommand request) throws RemotingCommandException {
        JobProtos.RequestCode code = valueOf(request.getCode());
        RemotingProcessor processor = processors.get(code);
        if (processor == null) {
            return RemotingCommand.createResponseCommand(RemotingProtos.ResponseCode.REQUEST_CODE_NOT_SUPPORTED.code(), "request code not supported!");
        }
        return processor.processRequest(channel, request);
    }
}
