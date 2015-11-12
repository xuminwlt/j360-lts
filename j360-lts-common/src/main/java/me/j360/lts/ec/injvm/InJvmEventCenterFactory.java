package me.j360.lts.ec.injvm;


import me.j360.lts.common.support.Config;
import me.j360.lts.ec.EventCenter;
import me.j360.lts.ec.EventCenterFactory;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
public class InJvmEventCenterFactory implements EventCenterFactory {

    @Override
    public EventCenter getEventCenter(Config config) {
        return new InjvmEventCenter();
    }
}
