package com.ink.workflowactiviti.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import javax.annotation.Resource;

/**
 * @author MT
 * @date 2021/11/2 15:27
 */
public class MyService implements JavaDelegate {
    @Resource
    private BuzService buzService;

    @Override
    public void execute(DelegateExecution execution) {
        buzService.updateStatus();
        System.out.println("更改父级");
    }
}
