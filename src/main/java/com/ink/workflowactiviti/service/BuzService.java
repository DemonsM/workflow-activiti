package com.ink.workflowactiviti.service;

import com.ink.workflowactiviti.mapper.NodelistMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author MT
 * @date 2021/11/2 15:39
 */
@Service
public class BuzService {
    @Resource
    private NodelistMapper nodelistMapper;

    public void updateStatus() {
        nodelistMapper.updateStatusById(1, 1);
    }
}
