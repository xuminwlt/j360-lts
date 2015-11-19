package me.j360.lts.common.listener;

import me.j360.lts.common.cluster.Node;

import java.util.List;

/**
 * @author Robert HG (254963746@qq.com) on 5/18/15.
 */
public interface NodeChangeListener {

    /**
     * 添加节点
     *
     * @param nodes
     */
    public void addNodes(List<Node> nodes);

    /**
     * 移除节点
     * @param nodes
     */
    public void removeNodes(List<Node> nodes);

}
