package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.Entity.TreeRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    @Insert("INSERT INTO tree_nodes (node_id, node_label, user_account) VALUES (#{nodeId}, #{nodeLabel}, #{UserAccount})")
    boolean addNode(@Param("nodeId")String nodeId,@Param("nodeLabel")String nodeLabel,@Param("UserAccount")long  UserAccount);
    @Insert("INSERT INTO tree_relation (ancestor, descendant, depth,user_account) VALUES (#{ancestor}, #{descendant}, #{depth}, #{UserAccount})")
    boolean addNodeRelation(@Param("ancestor")String ancestor,@Param("descendant")String descendant,@Param("depth")int depth,@Param("UserAccount")long UserAccount);
    @Update("UPDATE tree_nodes SET node_label = #{nodeLabel} WHERE node_id = #{nodeId}")
    boolean setNodeLabelById(@Param("nodeLabel")String nodeLabel,@Param("nodeId")String nodeId);
    @Delete("DELETE FROM tree_relation WHERE ancestor = #{nodeId} OR descendant = #{nodeId}")
    boolean deleteNodeRelation(@Param("nodeId")String nodeId);
    @Delete("DELETE FROM tree_nodes WHERE node_id = #{nodeId}")
    boolean deleteNodeById(@Param("nodeId")String nodeId);
}
