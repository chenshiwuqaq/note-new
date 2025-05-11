package org.ash.DTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class TodoDTO {
    private long todoId;

    private String todoStatus;

    private String taskTitle;

    private String taskContent;

    private String taskTags;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private long userAccount;

    private LocalDateTime createTime;

    private String priority;

    public TodoDTO(long todoId, String todoStatus, String taskTitle, String taskContent, String taskTags, LocalDateTime startTime, LocalDateTime endTime, long userAccount, LocalDateTime createTime, String priority) {
        this.todoId = todoId;
        this.todoStatus = todoStatus;
        this.taskTitle = taskTitle;
        this.taskContent = taskContent;
        this.taskTags = taskTags;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userAccount = userAccount;
        this.createTime = createTime;
        this.priority = priority;
    }
    public TodoDTO() {
    }

    @Override
    public String toString() {
        return "TodoDTO{" +
                "todoId=" + todoId +
                ", todoStatus='" + todoStatus + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", taskContent='" + taskContent + '\'' +
                ", taskTags='" + taskTags + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userAccount=" + userAccount +
                ", createTime=" + createTime +
                ", priority='" + priority + '\'' +
                '}';
    }
}
