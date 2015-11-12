package me.j360.lts.ec;


import me.j360.lts.common.extension.Adaptive;
import me.j360.lts.common.extension.SPI;
import me.j360.lts.common.support.Config;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
@SPI("injvm")
public interface EventCenterFactory {

    @Adaptive("event.center")
    EventCenter getEventCenter(Config config);

}
