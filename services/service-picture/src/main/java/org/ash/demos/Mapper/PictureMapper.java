package org.ash.demos.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.demos.Entity.Picture;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface PictureMapper {

    // 定义公共的 ResultMap
    @Results(id = "pictureResultMap", value = {
            @Result(property = "eid", column = "eid"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "path", column = "path"),
            @Result(property = "url", column = "url"),
            @Result(property = "size", column = "size"),
            @Result(property = "createDatetime", column = "create_datetime"),
            @Result(property = "account", column = "account"),
            @Result(property = "updateDatetime", column = "update_datetime"),
            @Result(property = "level", column = "level")
    })
    @Select("SELECT * FROM note_picture WHERE eid = #{eid}")
    Picture getPictureById(@Param("eid") Long eid);  // 用于注册结果集映射的虚拟方法

    @Insert("insert into note_picture (file_name, path, url, size, account, level) values (#{fileName}, #{path}, #{url}, #{size}, #{account}, #{level})")
    int addNewNode(Picture picture);

    @Select("select * from note_picture where account = #{account} AND url IS NULL")
    @ResultMap("pictureResultMap")
    List<Picture> findNodeByAccountAndNullUrl(@Param("account") Long account);

    @Select("SELECT * FROM note_picture WHERE account = #{account} AND level = #{level}")
    @ResultMap("pictureResultMap")
    Picture findNodeByAccountAndLevel(@Param("account") Long account, @Param("level") String level);

    @Select("SELECT COUNT(*) FROM note_picture WHERE path = #{path}")
    int checkPathExists(@Param("path") String path);

    @Update("UPDATE note_picture SET file_name = #{fileName}, path = #{path}, url = #{url}, size = #{size}, level = #{level} WHERE account = #{account} AND level = #{level}")
    int updateNodeByAccountAndLevel(Picture picture);

    @Update("UPDATE note_picture SET level = #{level} WHERE account = #{account} AND path = #{path}")
    int updateLevelByAccountAndPath(@Param("account") Long account, @Param("path") String path, @Param("level") String level);

    @Select("SELECT * FROM note_picture WHERE LEFT(level, #{prefixLength}) = #{prefixValue} AND account = #{account}")
    @ResultMap("pictureResultMap")
    List<Picture> findByLevelPrefix(@Param("prefixLength") int prefixLength, @Param("prefixValue") String prefixValue, @Param("account") Long account);

    @Delete("DELETE FROM note_picture WHERE eid = #{eid}")
    int deleteNodeById(@Param("eid") Long eid);

    @Select("SELECT * FROM note_picture WHERE SUBSTRING(level, 1, LENGTH(#{level})) > #{level}")
    @ResultMap("pictureResultMap")
    List<Picture> findModifyNodeByLevelPrefix(@Param("level") String level);

    @Delete("DELETE FROM note_picture WHERE account = #{account} AND file_name = #{fileName}")
    int deletePictureByAccountAndFileName(@Param("account") Long account, @Param("fileName") String fileName);

    @Select("SELECT * FROM note_picture WHERE account = #{account} AND file_name = #{fileName}")
    @ResultMap("pictureResultMap")
    Picture findNodeByAccountAndFileName(@Param("account") Long account, @Param("fileName") String fileName);

    @Select("SELECT COUNT(*) FROM note_picture WHERE url = #{url}")
    int checkUrlExists(@Param("url") String url);

    @Update("UPDATE note_picture SET file_name = #{fileName}, path = #{path}, url = #{url}, size = #{size}, level = #{level}, update_datetime = #{updateDatetime} WHERE eid = #{eid}")
    int updateNodeById(Picture picture);

    @Update("UPDATE note_picture SET file_name = #{newName}, path = #{newPath}, url = #{newUrl}, update_datetime = #{updateDatetime} WHERE account = #{account} AND file_name = #{oldName}")
    int updateNodeByAccountAndOldName(@Param("account") Long account, 
                                    @Param("oldName") String oldName,
                                    @Param("newName") String newName,
                                    @Param("newPath") String newPath,
                                    @Param("newUrl") String newUrl,
                                    @Param("updateDatetime") Date updateDatetime);
    @Select("SELECT file_name FROM note_picture WHERE account = #{account} AND url IS NOT NULL")
    List<String> getPictureNames(@Param("account") Long account);
}