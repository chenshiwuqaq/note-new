package org.ash.Service;

import org.ash.DTO.TodoDTO;
import org.ash.Mapper.TodoMapper;
import org.com.utils.DateUtils;
import org.com.utils.IdUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value = "todo", key = "#todoId")
    public TodoDTO getTodo(long todoId) {
        return todoMapper.selectTodoById(todoId);
    }

    @Override
    @Cacheable(value = "todoList", key = "#taskStatus")
    public List<TodoDTO> getTodoByStatus(String taskStatus) {
        return todoMapper.selectTodoByStatus(taskStatus);
    }

    @Override
    @Cacheable(value = "todoList", key = "'all'")
    public List<TodoDTO> getAllTodo() {
        return todoMapper.getALlTodo();
    }

    //某日待办，查询某天下的日期
    @Override
    @Cacheable(
            value = "todoList",
            // Key规则：账号 + 指定日期的年月日（剥离时分秒，统一格式）
            key = "#account + '-' + T(java.time.LocalDate).from(#date.atZone(T(java.time.ZoneId).systemDefault()))"
    )
    public List<TodoDTO> getTodayTodoByAccount(long account, LocalDateTime date) {
        // 对传入的 date 做兜底：如果为 null，默认查今天
        if (date == null) {
            date = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        List<TodoDTO> todayTodos = new ArrayList<>();
        List<LocalDateTime> dateRange = DateUtils.getDayRange(date); // 获取当天时间区间
        LocalDateTime startOfDay = dateRange.get(0);
        LocalDateTime endOfDay = dateRange.get(1);
        List<TodoDTO> todos = todoMapper.getTodoByAccount(account);

        for (TodoDTO todo : todos) {
            LocalDateTime taskStart = todo.getStartTime();
            LocalDateTime taskEnd = todo.getEndTime();

            // 判断任务时间是否与当天有交集
            if (taskStart != null && taskEnd != null) {
                // 判断任务是否在今天：
                // 1. 任务开始时间在今天结束时间之前
                // 2. 任务结束时间在今天开始时间之后
                boolean isToday = (taskStart.isBefore(endOfDay) || taskStart.isEqual(endOfDay)) && 
                                (taskEnd.isAfter(startOfDay) || taskEnd.isEqual(startOfDay));
                
                System.out.println("待办ID: " + todo.getTodoId() + 
                    ", 开始时间: " + taskStart + 
                    ", 结束时间: " + taskEnd + 
                    ", 是否在今天: " + isToday);
                if (isToday) {
                    todayTodos.add(todo);
                }
            } else {
                System.out.println("任务的开始或结束时间为 null，跳过：" + todo.getTodoId());
            }
        }
        return todayTodos;
    }
    //标准的今日待办
    @Override
    @Cacheable(
            value = "todoList",
            key = "#account + '-' + T(java.time.LocalDate).now()"
    )
    public List<TodoDTO> getTodayTodoByAccount(long account) {
        List<TodoDTO> todayTodos = new ArrayList<>();
        //生成当前系统时间的当天日期（获取当前天的零点）
        LocalDateTime date = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<LocalDateTime> dateRange = DateUtils.getDayRange(date);
        LocalDateTime startOfDay = dateRange.get(0);
        LocalDateTime endOfDay = dateRange.get(1);

        System.out.println("查询日期范围：" + startOfDay + " 到 " + endOfDay);

        List<TodoDTO> todos = todoMapper.getTodoByAccount(account);

        for (TodoDTO todo : todos) {
            LocalDateTime taskStart = todo.getStartTime();
            LocalDateTime taskEnd = todo.getEndTime();
            if (taskStart != null && taskEnd != null) {
                boolean isToday = (taskStart.isBefore(endOfDay) || taskStart.isEqual(endOfDay)) &&
                        (taskEnd.isAfter(startOfDay) || taskEnd.isEqual(startOfDay));
                if (isToday) {
                    todayTodos.add(todo);
                }
            } else {
                System.out.println("任务的开始或结束时间为 null，跳过：" + todo.getTodoId());
            }
        }
        System.out.println("今日待办数量：" + todayTodos.size());
        return todayTodos;
    }
//阶段性事项
@Override
@Cacheable(value = "todoList", key = "'milestones-' + #account")
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
    @CacheEvict(value = {"todo", "todoList"}, allEntries = true)
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
    @CacheEvict(value = {"todo", "todoList"}, allEntries = true)
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

    @Override
    @CacheEvict(value = {"todo", "todoList"}, allEntries = true)
    public boolean deleteTodo(long[] ids) {
        boolean flag = true;
        for (long id : ids){
            if (todoMapper.deleteTodoById(id)){
                flag = false;
            }
        }
        return flag;
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
