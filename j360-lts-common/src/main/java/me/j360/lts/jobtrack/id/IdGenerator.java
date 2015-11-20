package me.j360.lts.jobtrack.id;

import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;
import me.j360.lts.queue.domain.JobPo;

/**
 * Robert HG (254963746@qq.com) on 5/27/15.
 */
@SPI("md5")
public interface IdGenerator {

    /**
     * Éú³ÉID
     */
    @Adaptive("id.generator")
    public String generate(Config config, JobPo jobPo);

}
