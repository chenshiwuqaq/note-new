package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.Entity.TreeRelation;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface TreeNodeMapper {
    @Select("SELECT * FROM tree_relation WHERE ancestor = #{nodeId}")
    List<TreeRelation> findByAncestor(@Param("nodeId") int nodeId);

    @Select("SELECT * FROM tree_relation WHERE descendant = #{nodeId}")
    List<TreeRelation> findByDescendant(@Param("nodeId") int nodeId);
    @Select("SELECT * FROM tree_relation WHERE user_account = #{userAccount} ANd depth = 0")
    List<TreeRelation> findRootNodeByAccount(@Param("userAccount") int userAccount);
    @Select("SELECT node_label FROM tree_nodes WHERE node_id = #{nodeId}")
    String SearchLabelById(@Param("nodeId") int nodeId);
    @Update("UPDATE tree_nodes SET article_content_url = #{fileUrl} WHERE node_id = #{nodeId}")
    boolean SetFileUrlByNodeId(@Param("nodeId")int noteId,@Param("fileUrl")String fileUrl);
    @Insert("INSERT INTO tree_nodes (node_id, node_label, user_account) VALUES (#{nodeId}, #{nodeLabel}, #{UserAccount})")
    boolean addNode(@Param("nodeId")long nodeId,@Param("nodeLabel")String nodeLabel,@Param("UserAccount")int UserAccount);
    @Insert("INSERT INTO tree_relation (ancestor, descendant, depth,user_account) VALUES (#{ancestor}, #{descendant}, #{depth}, #{UserAccount})")
    boolean addNodeRelation(@Param("ancestor")long ancestor,@Param("descendant")long descendant,@Param("depth")int depth,@Param("UserAccount")int UserAccount);
    @Update("UPDATE tree_nodes SET node_label = #{nodeLabel} WHERE node_id = #{nodeId}")
    boolean setNodeLabelById(@Param("nodeLabel")String nodeLabel,@Param("nodeId")long nodeId);
}
