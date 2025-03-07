package com.example.makerchecker.controller;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

        import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class WorkflowController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    // Start the process instance
    @PostMapping("/start-payment")
    public String startProcess(@RequestBody Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey("paymentProcess", variables);
        return "Payment process started!";
    }

    // Get tasks for a specific user
    @GetMapping("/tasks/{assignee}")
    public List<String> getTasks(@PathVariable String assignee) {
        return taskService.createTaskQuery()
                .taskAssignee(assignee)
                .list()
                .stream()
                .map(Task::getName)
                .collect(Collectors.toList());
    }

    // Complete a task
    @PostMapping("/complete-task/{taskId}")
    public String completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        taskService.complete(taskId, variables);
        return "Task completed!";
    }
}
