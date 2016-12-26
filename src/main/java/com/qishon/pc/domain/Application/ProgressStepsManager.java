package com.qishon.pc.domain.Application;

import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.repository.ProgressStepsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by xm on 2016/12/26.
 */
@Component
public class ProgressStepsManager {

    @Autowired
    ProgressStepsRepository stepsRepository;
    public List<ProgressSteps> findByGirardId(Map<String, Object> params){
        return  stepsRepository.findByGirardId(params);
    }
    public ProgressSteps addProCtrlSteps(ProgressSteps progressSteps){
        return stepsRepository.addProCtrlSteps(progressSteps);
    }
    public ProgressSteps updateCompletedFiles(ProgressSteps progressSteps){
        return stepsRepository.updateCompletedFiles(progressSteps);
    }
}
