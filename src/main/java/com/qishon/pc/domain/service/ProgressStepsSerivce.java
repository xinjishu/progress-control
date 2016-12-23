package com.qishon.pc.domain.service;

import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.repository.ProgressStepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by xm on 2016/12/23.
 */
@Service
public class ProgressStepsSerivce {

    @Autowired
    ProgressStepsRepository stepsRepository;

    public List<ProgressSteps> findByGirardId(Map<String, Object> params){
        return  stepsRepository.findByGirardId(params);
    }
}
