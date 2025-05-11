package org.ash.Mapper;

import org.apache.ibatis.annotations.*;
import org.ash.DTO.TodoDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TodoMapper {
    @Select("SELECT * FROM todos WHERE todo_id = #{todoId}")
    TodoDTO selectTodoById(long todoId);
    @Select("SELECT * FROM todos WHERE todo_status = #{taskStatus}")
    List<TodoDTO> selectTodoByStatus(String taskStatus);
    @Select("SELECT * FROM todos")
    List<TodoDTO> getALlTodo();
    @Select("SELECT * FROM todos WHERE user_account = #{account}")
    List<TodoDTO> getTodoByAccount(long account);
    @Insert("INSERT INTO todos " +
            "(todo_id,todo_status, task_title, task_content, task_tags, start_time, end_time, user_account, create_time, priority) " +
            "VALUES (#{todoId},#{todoStatus}, #{taskTitle}, #{taskContent}, #{taskTags}, #{startTime}, #{endTime}, #{userAccount}, #{createTime}, #{priority})")
    boolean insertTodo(TodoDTO todoDTO);
    @Select("SELECT COUNT(1) FROM todos WHERE todo_id = #{todoId}")
    Long isExistByTodoId(@Param("todoId") Long todoId);
    @Update("UPDATE todos SET todo_status = '进行中' WHERE todo_id = #{id}")
    boolean changeStatusToProcessById(@Param("id") long id);
    @Update("UPDATE todos SET todo_status = '完成' WHERE todo_id = #{id}")
    boolean changeStatusToCompleteById(@Param("id") long id);
}
