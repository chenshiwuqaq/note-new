package org.ash.Service;

import org.ash.DTO.TodoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface TodoService {
    TodoDTO getTodo(long todoId);
    List<TodoDTO> getTodoByStatus(String taskStatus);
    List<TodoDTO> getAllTodo();
    List<TodoDTO> getTodayTodoByAccount(long account, LocalDateTime date);
    List<TodoDTO> getMilestones(long account);
    boolean addTodo(TodoDTO todoDTO);
    boolean changeStatusById(long[] ids,String status);
}
