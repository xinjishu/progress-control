package com.qishon.pc.domain.Application;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.service.ProgressStepsSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/26.
 */
@Component
public class ProgressStepsManager {

    @Autowired
    ProgressStepsSerivce stepsSerivce;
    public List<ProgressSteps> findByGirardId(Map<String, Object> params){
        return  stepsSerivce.findByGirardId(params);
    }
    public ProgressSteps addProCtrlSteps(int key,ProgressControl progressControl){
        return stepsSerivce.addProCtrlSteps(key,progressControl);
    }
    public ProgressSteps updateCompletedFiles(ProgressSteps progressSteps){
        return stepsSerivce.updateCompletedFiles(progressSteps);
    }
}
