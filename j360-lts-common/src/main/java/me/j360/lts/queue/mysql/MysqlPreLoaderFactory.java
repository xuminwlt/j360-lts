package me.j360.lts.queue.mysql;


import me.j360.lts.common.support.Application;
import me.j360.lts.common.support.Config;
import me.j360.lts.queue.PreLoader;
import me.j360.lts.queue.PreLoaderFactory;

/**
 * @author Robert HG (254963746@qq.com) on 8/14/15.
 */
public class MysqlPreLoaderFactory implements PreLoaderFactory {
    @Override
    public PreLoader getPreLoader(Config config, Application application) {
        return new MysqlPreLoader(application);
    }
}
