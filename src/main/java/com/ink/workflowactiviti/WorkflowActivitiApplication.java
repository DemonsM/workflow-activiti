package com.ink.workflowactiviti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WorkflowActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkflowActivitiApplication.class, args);
    }

}
