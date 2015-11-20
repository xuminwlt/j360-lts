package me.j360.lts.remote.protocol;

import me.j360.lts.common.protocol.AbstractRemotingCommandBody;
import me.j360.lts.common.support.Job;
import me.j360.lts.remote.annotation.NotNull;

import java.util.List;

/**
 * @author Robert HG (254963746@qq.com) on 7/24/14.
 *         任务传递信息
 */
public class JobSubmitRequest extends AbstractRemotingCommandBody {

    @NotNull
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}
