package me.j360.lts.jobtrack.id;

import me.j360.lts.common.support.Config;
import me.j360.lts.common.utils.StringUtils;
import me.j360.lts.queue.domain.JobPo;

/**
 * Robert HG (254963746@qq.com) on 5/27/15.
 */
public class UUIDGenerator implements IdGenerator{
    @Override
    public String generate(Config config, JobPo jobPo) {
        return StringUtils.generateUUID();
    }
}
