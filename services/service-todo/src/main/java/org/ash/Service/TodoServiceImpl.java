package org.ash.Service;

import org.ash.DTO.TodoDTO;
import org.ash.Mapper.TodoMapper;
import org.com.utils.DateUtils;
import org.com.utils.IdUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TodoServiceImpl implements TodoService {
    final
    TodoMapper todoMapper;

    public TodoServiceImpl(TodoMapper todoMapper) {
        this.todoMapper = todoMapper;
    }

    @Override
    public TodoDTO getTodo(long todoId) {
        return todoMapper.selectTodoById(todoId);
    }

    @Override
    public List<TodoDTO> getTodoByStatus(String taskStatus) {
        return todoMapper.selectTodoByStatus(taskStatus);
    }

    @Override
    public List<TodoDTO> getAllTodo() {
        return todoMapper.getALlTodo();
    }

    @Override
    public List<TodoDTO> getTodayTodoByAccount(long account, LocalDateTime date) {
        List<TodoDTO> todayTodos = new ArrayList<>();
        List<LocalDateTime> dateRange = DateUtils.getDayRange(date); // 获取当天时间区间

        List<TodoDTO> todos = todoMapper.getTodoByAccount(account);
        for (TodoDTO todo : todos) {
            LocalDateTime taskStart = todo.getStartTime();
            LocalDateTime taskEnd = todo.getEndTime();

            // 判断任务时间是否与当天有交集，增加空值校验
            if (taskStart != null && taskEnd != null) {
                boolean isOverlap = taskStart.isBefore(dateRange.get(1)) &&
                        taskEnd.isAfter(dateRange.get(0));
                if (isOverlap) {
                    todayTodos.add(todo);
                }
            } else {
                // 可选：记录日志或跳过该条数据
                System.out.println("任务的开始或结束时间为 null，跳过：" + todo.getTodoId());
            }
        }
        return todayTodos;
    }
//阶段性事项
@Override
public List<TodoDTO> getMilestones(long account) {
    List<TodoDTO> milestones = new ArrayList<>();
    List<LocalDateTime> calendars = getCalendar(); // 获取今天和明天的日期时间
    LocalDateTime tomorrow = calendars.get(1); // 获取明天的日期时间

    List<TodoDTO> todoByAccount = todoMapper.getTodoByAccount(account);
    for (TodoDTO todoDTO : todoByAccount) {
        LocalDateTime endTime = todoDTO.getEndTime();
        // 判断任务结束时间是否在明天之后
        if (endTime.isAfter(tomorrow)) {
            milestones.add(todoDTO);
        }
    }
    return milestones;
}

    @Override
    public boolean addTodo(TodoDTO todoDTO) {
        System.out.println(todoDTO.toString());
        while (true){
            long todoId = IdUtils.generateTenDigitalId();
            if(todoMapper.isExistByTodoId(todoId) == 0){
                todoDTO.setTodoId(todoId);
                return todoMapper.insertTodo(todoDTO);
            }
        }
    }

    @Override
    public boolean changeStatusById(long[] ids,String status) {
        for (long id : ids){
            if ("待办".equals(status)){
                todoMapper.changeStatusToProcessById(id);
                return true;
            } else if ("进行中".equals(status)) {
                todoMapper.changeStatusToCompleteById(id);
                return true;
            }
        }
        return false;
    }

    public List<LocalDateTime> getCalendar() {
        List<LocalDateTime> list = new ArrayList<>();

        // 获取当前日期时间（不包含时区）
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        // 获取明天的日期时间
        LocalDateTime tomorrow = today.plusDays(1);
        list.add(today);
        list.add(tomorrow);
        return list;
    }
}
