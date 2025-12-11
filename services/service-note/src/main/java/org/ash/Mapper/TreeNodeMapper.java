package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.Dto.TreeNodeDto;
import org.ash.Entity.TreeRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TreeNodeMapper {
    @Select("SELECT * FROM tree_relation WHERE ancestor = #{nodeId}")
    List<TreeRelation> findByAncestor(@Param("nodeId") String nodeId);

    @Select("SELECT * FROM tree_relation WHERE descendant = #{nodeId}")
    List<TreeRelation> findByDescendant(@Param("nodeId") String nodeId);
    @Select("SELECT * FROM tree_relation WHERE user_account = #{userAccount} ANd depth = 0")
    List<TreeRelation> findRootNodeByAccount(@Param("userAccount") long userAccount);
    @Select("SELECT node_label FROM tree_nodes WHERE node_id = #{nodeId}")
    String SearchLabelById(@Param("nodeId") String nodeId);
    @Update("UPDATE tree_nodes SET article_content_url = #{fileUrl} WHERE node_id = #{nodeId}")
    boolean SetFileUrlByNodeId(@Param("nodeId")String noteId,@Param("fileUrl")String fileUrl);
    @Insert("INSERT INTO tree_nodes (node_id, node_label, user_account,isCollected) VALUES (#{nodeId}, #{nodeLabel}, #{UserAccount},0)")
    boolean addNode(@Param("nodeId")String nodeId,@Param("nodeLabel")String nodeLabel,@Param("UserAccount")long  UserAccount);
    @Insert("INSERT INTO tree_relation (ancestor, descendant, depth,user_account) VALUES (#{ancestor}, #{descendant}, #{depth}, #{UserAccount})")
    boolean addNodeRelation(@Param("ancestor")String ancestor,@Param("descendant")String descendant,@Param("depth")int depth,@Param("UserAccount")long UserAccount);
    @Update("UPDATE tree_nodes SET node_label = #{nodeLabel} WHERE node_id = #{nodeId}")
    boolean setNodeLabelById(@Param("nodeLabel")String nodeLabel,@Param("nodeId")String nodeId);
    @Delete("DELETE FROM tree_relation WHERE ancestor = #{nodeId} OR descendant = #{nodeId}")
    boolean deleteNodeRelation(@Param("nodeId")String nodeId);
    @Delete("DELETE FROM tree_nodes WHERE node_id = #{nodeId}")
    boolean deleteNodeById(@Param("nodeId")String nodeId);
    @Select("SELECT isCollected from tree_nodes where node_id = #{nodeId}")
    Integer CheckIsCollectedById(@Param("nodeId")String nodeId);
    @Update("update tree_nodes set isCollected = #{isCollected} WHERE node_id = #{nodeId}")
    boolean updateIsCollected(@Param("isCollected")int isCollected,@Param("nodeId")String noteId);
    @Select("select * from tree_nodes WHERE user_account = #{account} AND isCollected = 1")
    List<TreeNodeDto> getAllCollectedNode(@Param("account") long account);
    /**
     * 分批查询
     */
    @Select("""
        SELECT node_id, node_label
        FROM tree_nodes
        WHERE user_account = #{account}
            AND isCollected = 1
        ORDER BY node_id
        LIMIT #{limit} OFFSET #{offset}
         """)
    List<Map<String, Object>> getCollectedNodesByPage(
            @Param("account") Long account,
            @Param("offset") int offset,
            @Param("limit") int limit);

    /**
     * 获取单个节点的路径
     */
    @Select("""
        SELECT
            GROUP_CONCAT(anc.node_label ORDER BY tr.depth SEPARATOR '/') as path
        FROM tree_relation tr
        INNER JOIN tree_nodes anc ON tr.ancestor = anc.node_id
            AND anc.user_account = tr.user_account
            WHERE tr.descendant = #{nodeId}
            AND tr.user_account = #{account}
            AND tr.ancestor != tr.descendant
    """)
    String getNodePath(@Param("nodeId") String nodeId, @Param("account") Long account);
    @Select("""
        SELECT COUNT(*)
        FROM tree_nodes
        WHERE user_account = #{account}
            AND isCollected = 1
    """)
    int getCollectedCount(@Param("account") Long account);
}
