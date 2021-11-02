package com.ink.workflowactiviti;

import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.springframework.util.StringUtils;

public class ActivitiUtils {
    /**
     * 创建用户任务
     *
     * @param id       任务id
     * @param name     任务名称
     * @param assignee 任务所属人员
     * @return
     */
    public static UserTask CREATEUSERTASK(String id, String name, String assignee) {
        UserTask userTask = new UserTask();
        userTask.setName(name);
        userTask.setId(id);
        userTask.setAssignee(assignee);
        return userTask;
    }


    public static ServiceTask CREATE_SERVICE_TASK(String id, String name, String assignee, String implementation) {
        ServiceTask serviceTask = new ServiceTask();
        serviceTask.setId(id);
        serviceTask.setName(name);
        //serviceTask.setOperationRef();
        serviceTask.setImplementation(implementation);
        serviceTask.setName(assignee);
        return serviceTask;
    }

    /**
     * 连接线
     *
     * @param from 开始任务id
     * @param to   连接任务id
     * @return
     */
    public static SequenceFlow CREATESEQUEBCEFLOW(String from, String to, String name, String condition) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        flow.setName(name);

        if (!StringUtils.isEmpty(condition)) {
            flow.setConditionExpression(condition);
        }
        return flow;
    }

    public static SequenceFlow CREATESEQUEBCEFLOW(String from, String to) {
        SequenceFlow flow = new SequenceFlow();
        flow.setSourceRef(from);
        flow.setTargetRef(to);
        return flow;
    }

    /**
     * 开始任务
     *
     * @return
     */
    public static StartEvent CREATESTARTEVENT() {
        StartEvent startEvent = new StartEvent();
        startEvent.setId("start");
        return startEvent;
    }

    /**
     * 结束任务
     *
     * @return
     */
    public static EndEvent CREATEENDEVENT() {
        EndEvent endEvent = new EndEvent();
        endEvent.setId("end");
        return endEvent;
    }
}
