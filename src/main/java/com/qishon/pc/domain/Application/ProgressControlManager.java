package com.qishon.pc.domain.Application;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import com.qishon.pc.domain.repository.ProgressStepsRepository;
import com.qishon.pc.domain.service.ProgressControlService;
import com.qishon.pc.domain.service.ProgressStepsSerivce;
import com.qishon.pc.domain.util.GirardEnum;
import com.qishon.pc.domain.util.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Component
public class ProgressControlManager {
    //获取渲染文件路径
    @Value("${run.path}")
    private String filePath;
    @Autowired
    private ProgressStepsSerivce stepsSerivce;
    @Autowired
    private ProgressControlService progressControlService;
    public List<ProgressControl> findByGirard(Map<String,Object> map) {
        map.put("girard","file");
        return progressControlService.findByGirard(map);
    }
//    public ProgressControl addProgressControl(ProgressControl progressControl){
//        return progressControlRepository.addProgressControl(progressControl);
//    }
//    public ProgressControl updateProgress(ProgressControl progressControl){
//        return progressControlRepository.updateProgress(progressControl);
//    }
//
//    public ProgressControl updateVersion(ProgressControl progressControl){
//        return progressControlRepository.updateVersion(progressControl);
//    }
//
//    public ProgressControl updateCurNode(ProgressControl progressControl){
//        return progressControlRepository.updateCurNode(progressControl);
//    }
//    public ProgressControl findById(int id){
//        return progressControlRepository.findById(id);
//    }


    /**
     * 开始执行渲染过程
     */
    public void startRendering(){
        Sender sender = new Sender();
        //第一、二步：遍历Cloud Render目录,根据firstStep去遍历01.Model，并添加到progress_control表中
       List<ProgressControl> progressControls = progressControlService.addProgressControls(1);
       if (!progressControls.isEmpty()){
            for (int i = 0;i < progressControls.size();i++){
                ProgressControl progressControl = progressControls.get(i);
                if(progressControl.getProgress().equals("0%")){//判断这笔记录是新增的记录
                    //新增所有子表记录
                    for (int step = 2; step < 8;step++){
                        ProgressSteps progressSteps = stepsSerivce.addProCtrlSteps(step,progressControls.get(i));
                    }
                    //第三步：调用执行maya脚本，执行成功之后返回结果1；
                    sender.send(2,progressControl.getGirard(),"渲染","renderReceiver");
                    //修改款号当前节点
                    progressControlService.updateCurNode(progressControl,2);
                }else {//这笔记录是已经存在的,而且不是历史记录
                    //查继续执行后续的任务
                    this.continueRendering(progressControl);
                }
            }
       }
    }

    /**
     * 根据当前步骤继续执行渲染
     * @param progressControl 当前款号记录
     */
    public void continueRendering(ProgressControl progressControl){
        Sender sender = new Sender();
        //获取当前步骤
        String stepName = GirardEnum.findByKey(progressControl.getCurNode()).getFileName();
        ProgressSteps steps = stepsSerivce.findByGirardIdAndStepName(progressControl.getId(),stepName);
        if (steps.getAllFiles() == steps.getCompletedFiles()){//当前步骤已完成，执行下一步骤
            int nextNode = progressControl.getCurNode()+1;
            //判断是否是最后一步，不是的话继续执行下一步，是的话修改Progress Control当前完成度
            if (!steps.getStepName().toUpperCase().equals("DONE")){
                progressControlService.updateCurNode(progressControl,nextNode);
                String sendType = GirardEnum.findByKey(nextNode).getSendCode();
                String taskName = GirardEnum.findByKey(nextNode).getFileName();
                sender.send(progressControl.getCurNode(),progressControl.getGirard(),taskName,sendType);
            }else {
                progressControlService.updateProgress(progressControl,"100");
                int version = progressControlService.countVersion()+1;
                progressControlService.updateVersion(progressControl,version);
            }
        }else{//更新已完成的文件
            int compressFiles = stepsSerivce.getCompressFiles(steps);
            steps.setCompletedFiles(compressFiles);
            stepsSerivce.updateCompletedFiles(steps);
        }
    }
}
