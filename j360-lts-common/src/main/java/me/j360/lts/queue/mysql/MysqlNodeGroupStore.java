package me.j360.lts.queue.mysql;

import me.j360.lts.common.cluster.NodeType;
import me.j360.lts.common.constant.Constants;
import me.j360.lts.common.support.Config;
import me.j360.lts.common.support.JobQueueUtils;
import me.j360.lts.common.support.NodeGroupGetRequest;
import me.j360.lts.common.support.SystemClock;
import me.j360.lts.common.utils.FileUtils;
import me.j360.lts.common.web.response.PageResponse;
import me.j360.lts.queue.NodeGroupStore;
import me.j360.lts.queue.domain.NodeGroupPo;
import me.j360.lts.queue.exception.JobQueueException;
import me.j360.lts.store.jdbc.JdbcRepository;
import me.j360.lts.store.jdbc.SqlBuilder;
import org.apache.commons.dbutils.ResultSetHandler;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Robert HG (254963746@qq.com) on 6/7/15.
 */
public class MysqlNodeGroupStore extends JdbcRepository implements NodeGroupStore {

    public MysqlNodeGroupStore(Config config) {
        super(config);
        createStore();
    }

    public void createStore() {
        // create table
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("sql/lts_node_group_store.sql");
            String sql = FileUtils.read(is, Constants.CHARSET);
            sql = sql.replace("{tableName}", JobQueueUtils.NODE_GROUP_STORE);
            getSqlTemplate().update(sql);
        } catch (Exception e) {
            throw new JobQueueException("create table error!", e);
        }
    }

    private String insertSQL = "INSERT INTO " + JobQueueUtils.NODE_GROUP_STORE
            + " (node_type, name, gmt_created) VALUES (?,?,?)";

    private String removeSQL = "DELETE FROM " + JobQueueUtils.NODE_GROUP_STORE
            + " WHERE node_type = ? and name = ?";

    private String selectSQL = "SELECT * FROM " + JobQueueUtils.NODE_GROUP_STORE
            + " WHERE node_type = ?";

    private String selectOneSQL = "SELECT count(1) FROM " + JobQueueUtils.NODE_GROUP_STORE
            + " WHERE node_type = ? and name = ?";

    private ResultSetHandler<List<NodeGroupPo>> RESULT_SET_HANDLER = new ResultSetHandler<List<NodeGroupPo>>() {
        @Override
        public List<NodeGroupPo> handle(ResultSet rs) throws SQLException {
            List<NodeGroupPo> list = new ArrayList<NodeGroupPo>();
            while (rs.next()) {
                NodeGroupPo nodeGroupPo = new NodeGroupPo();
                nodeGroupPo.setNodeType(NodeType.valueOf(rs.getString("node_type")));
                nodeGroupPo.setName(rs.getString("name"));
                nodeGroupPo.setGmtCreated(rs.getLong("gmt_created"));
                list.add(nodeGroupPo);
            }
            return list;
        }
    };

    @Override
    public void addNodeGroup(NodeType nodeType, String name) {
        try {
            Long count = getSqlTemplate().queryForValue(selectOneSQL, nodeType.name(), name);
            if (count > 0) {
                //  already exist
                return;
            }
            getSqlTemplate().update(insertSQL, nodeType.name(), name, SystemClock.now());
        } catch (SQLException e) {
            throw new JobQueueException(e);
        }
    }

    @Override
    public void removeNodeGroup(NodeType nodeType, String name) {
        try {
            getSqlTemplate().update(removeSQL, nodeType.name(), name);
        } catch (SQLException e) {
            throw new JobQueueException(e);
        }
    }

    @Override
    public List<NodeGroupPo> getNodeGroup(NodeType nodeType) {
        try {
            return getSqlTemplate().query(selectSQL, RESULT_SET_HANDLER, nodeType.name());
        } catch (SQLException e) {
            throw new JobQueueException(e);
        }
    }

    public PageResponse<NodeGroupPo> getNodeGroup(NodeGroupGetRequest request){
        PageResponse<NodeGroupPo> response = new PageResponse<NodeGroupPo>();

        try {
            // count
            SqlBuilder countSQL = new SqlBuilder("SELECT count(1) FROM " + JobQueueUtils.NODE_GROUP_STORE);
            countSQL.addCondition("node_type", request.getNodeType() == null ? null : request.getNodeType().name());
            countSQL.addCondition("name", request.getNodeGroup());
            Long results = getSqlTemplate().queryForValue(countSQL.getSQL(), countSQL.getParams().toArray());
            response.setResults(results.intValue());
            if(results == 0){
                return response;
            }

            SqlBuilder rowsSQL = new SqlBuilder("SELECT * FROM " + JobQueueUtils.NODE_GROUP_STORE);
            rowsSQL.addCondition("node_type", request.getNodeType() == null ? null : request.getNodeType().name());
            rowsSQL.addCondition("name", request.getNodeGroup());
            rowsSQL.addOrderBy("gmt_created", "DESC");
            rowsSQL.addLimit(request.getStart(), request.getLimit());
            List<NodeGroupPo> rows = getSqlTemplate().query(rowsSQL.getSQL(), RESULT_SET_HANDLER, rowsSQL.getParams().toArray());
            response.setRows(rows);

            return response;
        } catch (SQLException e) {
            throw new JobQueueException(e);
        }
    }
}
