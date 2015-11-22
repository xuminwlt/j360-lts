package me.j360.lts.common.register;

import me.j360.lts.common.constant.EcTopic;
import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.common.support.Application;
import me.j360.lts.ec.EventInfo;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Robert HG (254963746@qq.com) on 9/8/15.
 */
public class RegistryStatMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistryStatMonitor.class);
    private Application application;
    private AtomicBoolean available = new AtomicBoolean(false);

    public RegistryStatMonitor(Application application) {
        this.application = application;
    }

    public void setAvailable(boolean available) {
        this.available.set(available);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Registry {}", available ? "available" : "unavailable");
        }
        // 发布事件
        application.getEventCenter().publishAsync(new EventInfo(
                available ? EcTopic.REGISTRY_AVAILABLE : EcTopic.REGISTRY_UN_AVAILABLE));
    }

    public boolean isAvailable() {
        return this.available.get();
    }

}
