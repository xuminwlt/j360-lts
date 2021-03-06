package me.j360.lts.store.jdbc;

import me.j360.lts.common.support.Config;
import me.j360.lts.store.jdbc.datasource.DataSourceProviderFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Robert HG (254963746@qq.com) on 5/19/15.
 */
public abstract class JdbcRepository {

    private SqlTemplate sqlTemplate;
    private AtomicBoolean init = new AtomicBoolean(false);

    public JdbcRepository(Config config) {
        if (init.compareAndSet(false, true)) {
            sqlTemplate = new SqlTemplate(
                    DataSourceProviderFactory.create(config)
                            .getDataSource(config));
        }
    }

    public SqlTemplate getSqlTemplate() {
        return sqlTemplate;
    }

}
