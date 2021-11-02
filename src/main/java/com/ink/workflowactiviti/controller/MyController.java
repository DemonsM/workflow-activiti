package com.ink.workflowactiviti.controller;

import com.ink.workflowactiviti.ActivitiUtils;
import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author MT
 * @date 2021/11/2 14:44
 */
@RestController
public class MyController {
    @Resource
    private ProcessEngine processEngine;

    @GetMapping("/set")
    public String setProcession() throws IOException {
        BpmnModel bpmnModel = new BpmnModel();
        Process process = new Process();
        process.setId("update-status-process");
        process.setName("更改子级任务状态");
        bpmnModel.addProcess(process);

        //创建bpmn元素
        process.addFlowElement(ActivitiUtils.CREATESTARTEVENT());
        process.addFlowElement(ActivitiUtils.CREATEUSERTASK("update-child-status-to-1-process", "子级任务更改为1", null));
        process.addFlowElement(ActivitiUtils.CREATEUSERTASK("update-parent-status-to-1-process", "父级任务更改为1", null));
        process.addFlowElement(ActivitiUtils.CREATEENDEVENT());
        //将各个任务通过连接线连接在一起
        process.addFlowElement(ActivitiUtils.CREATESEQUEBCEFLOW("start", "update-child-status-to-1-process"));
        process.addFlowElement(ActivitiUtils.CREATESEQUEBCEFLOW("update-child-status-to-1-process", "update-parent-status-to-1-process"));
        //不同意处理
        process.addFlowElement(ActivitiUtils.CREATESEQUEBCEFLOW("update-parent-status-to-1-process", "end", "同步父级状态", "${condition=='1'}"));


        // 2.生成bpmn自动布局
        new BpmnAutoLayout(bpmnModel).execute();

        // 3. 部署bpmn模型
        Deployment deployment = processEngine.getRepositoryService().createDeployment()
                .addBpmnModel("dynamic-model.bpmn", bpmnModel).deploy();

        System.out.println("部署流程成功");

        // 4. 启动流程实例   启动永远是最新版的流程
        ProcessInstance processInstance = processEngine.getRuntimeService()
                .startProcessInstanceByKey("update-status-process");
        // 5.发起任务   任务查询（通过任务Id查询任务）
        List<Task> tasks = processEngine.getTaskService().createTaskQuery()
                .processInstanceId(processInstance.getId()).list();
        tasks.forEach(task -> {
            System.out.println("任务ID:" + task.getId());
            System.out.println("执行实例ID:" + task.getExecutionId());
            System.out.println("流程实例ID:" + task.getProcessInstanceId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务定义的Key:" + task.getTaskDefinitionKey());
            System.out.println("任务办理人:" + task.getAssignee());
            System.out.println("#####################");
        });

        // 6.保存bpmn流程图
        InputStream processDiagram = processEngine.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());

        FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));

        // 7. 保存bpmn.xml的xml类型文件
        InputStream processBpmn = processEngine.getRepositoryService().getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
        FileUtils.copyInputStreamToFile(processBpmn, new File("target/process.bpmn20.xml"));

        return "流程保存成功";
    }
}
