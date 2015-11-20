package me.j360.lts.jobtrack.id;

import me.j360.lts.common.support.Config;
import me.j360.lts.common.utils.Md5Encrypt;
import me.j360.lts.queue.domain.JobPo;

/**
 * Robert HG (254963746@qq.com) on 5/27/15.
 */
public class Md5Generator implements IdGenerator{
    @Override
    public String generate(Config config, JobPo jobPo) {
        return Md5Encrypt.md5(jobPo.getTaskId() + jobPo.getSubmitNodeGroup() + jobPo.getGmtCreated());
    }
}
