package org.ash.Controller;

import org.ash.DTO.ChangeStatusDTO;
import org.ash.DTO.TodoDTO;
import org.ash.Service.TodoServiceImpl;
import org.com.Entity.Result;
import org.com.utils.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/todo")
public class todoController {
    final
    TodoServiceImpl todoService;

    public todoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/getTodoById")
    public Result getTodo(@RequestParam("todoId") long todoId){
        System.out.println("2" + todoService.getTodo(todoId));
        return Result.success(todoService.getTodo(todoId));
    }
    @GetMapping("/getTodoByStatus")
    public Result getTodoByStatus(@RequestParam("taskStatus") String taskStatus){
        return Result.success(todoService.getTodoByStatus(taskStatus));
    }
    @GetMapping("/getAllTodo")
    public Result getAllTodo(){
        return Result.success(todoService.getAllTodo());
    }
    @GetMapping("/getTodayTodoByAccount")
    public Result getTodayTodoByAccount(
            @RequestParam("account") long account, @RequestParam LocalDateTime date) {
        return Result.success(todoService.getTodayTodoByAccount(account, date));
    }
    @GetMapping("/getMilestones")
    public Result getMilestones(@RequestParam("account") long account){
        return Result.success(todoService.getMilestones(account));
    }
    @PostMapping("/addTodo")
    public Result addTodo(@RequestBody TodoDTO todoDTO){
        return Result.success(todoService.addTodo(todoDTO));
    }
    @PostMapping("/changeStatusById")
    public Result changeStatusById(@RequestBody ChangeStatusDTO changeStatusDTO){
        return Result.success(todoService.changeStatusById(changeStatusDTO.getIds(), changeStatusDTO.getStatus()));
    }
}
