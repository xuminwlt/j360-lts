package me.j360.lts.jobtrack.domain;

import me.j360.lts.common.cluster.Node;
import me.j360.lts.common.cluster.NodeType;

/**
 * @author Robert HG (254963746@qq.com) on 7/23/14.
 * Job Tracker ½Úµã
 */
public class JobTrackerNode extends Node {

    public JobTrackerNode() {
        this.setNodeType(NodeType.JOB_TRACKER);
        this.addListenNodeType(NodeType.JOB_CLIENT);
        this.addListenNodeType(NodeType.TASK_TRACKER);
        this.addListenNodeType(NodeType.JOB_TRACKER);
    }
}
