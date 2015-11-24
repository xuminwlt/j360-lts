package me.j360.lts.common.test.register;

import me.j360.lts.common.cluster.Node;
import me.j360.lts.common.extension.ExtensionLoader;
import me.j360.lts.common.listener.MasterChangeListener;
import me.j360.lts.common.listener.NodeChangeListener;
import me.j360.lts.common.logger.Logger;
import me.j360.lts.common.logger.LoggerFactory;
import me.j360.lts.common.register.*;
import me.j360.lts.common.register.zookeeper.ZookeeperRegistry;
import me.j360.lts.common.support.Config;
import me.j360.lts.common.test.remote.client.JobClientApplication;
import me.j360.lts.common.utils.CollectionUtils;
import me.j360.lts.common.utils.StringUtils;
import me.j360.lts.ec.EventCenterFactory;
import me.j360.lts.jobtrack.domain.JobClientNode;
import me.j360.lts.remote.RemotingTransporter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created with j360-lts -> me.j360.lts.common.test.
 * User: min_xu
 * Date: 2015/11/22
 * Time: 11:50
 * 说明：
 */
public class RegisterTest {
    public static void main(String[] args){
        EventCenterFactory eventCenterFactory = ExtensionLoader
                .getExtensionLoader(EventCenterFactory.class).getAdaptiveExtension();

        JobClientApplication application = new JobClientApplication();
        Config config = new Config();
        config.setRegistryAddress("zookeeper://192.168.247.128:2181");
        application.setConfig(config);
        List<NodeChangeListener> nodeChangeListeners = new ArrayList<NodeChangeListener>();
        //masterChangeListeners = new ArrayList<MasterChangeListener>();
        application.setRegistryStatMonitor(new RegistryStatMonitor(application));
        application.setEventCenter(eventCenterFactory.getEventCenter(config));

        ZookeeperRegistry registry = new ZookeeperRegistry(application);
        Node node = new Node();
        initRegistry(application,registry, node,nodeChangeListeners);
        registry.register(node);

    }

    private List<NodeChangeListener> nodeChangeListeners;
    protected RemotingTransporter remotingTransporter = ExtensionLoader
            .getExtensionLoader(RemotingTransporter.class).getAdaptiveExtension();
    private AtomicBoolean started = new AtomicBoolean(false);

    private static void initRegistry(JobClientApplication application,Registry registry,Node node,final List<NodeChangeListener> nodeChangeListeners) {

        if (registry instanceof AbstractRegistry) {
            ((AbstractRegistry) registry).setNode(node);
        }
        registry.subscribe(node, new NotifyListener() {
            private final Logger NOTIFY_LOGGER = LoggerFactory.getLogger(NotifyListener.class);

            @Override
            public void notify(NotifyEvent event, List<Node> nodes) {
                if (CollectionUtils.isEmpty(nodes)) {
                    return;
                }
                switch (event) {
                    case ADD:
                        for (NodeChangeListener listener : nodeChangeListeners) {
                            try {
                                listener.addNodes(nodes);
                            } catch (Throwable t) {
                                NOTIFY_LOGGER.error("{} add nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                    case REMOVE:
                        for (NodeChangeListener listener : nodeChangeListeners) {
                            try {
                                listener.removeNodes(nodes);
                            } catch (Throwable t) {
                                NOTIFY_LOGGER.error("{} remove nodes failed , cause: {}", listener.getClass().getName(), t.getMessage(), t);
                            }
                        }
                        break;
                }
            }
        });
    }
}
