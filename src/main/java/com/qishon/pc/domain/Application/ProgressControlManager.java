package com.qishon.pc.domain.Application;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import com.qishon.pc.domain.repository.ProgressStepsRepository;
import com.qishon.pc.domain.service.ProgressControlService;
import com.qishon.pc.domain.util.GirardEnum;
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
    ProgressStepsRepository stepsRepository;
    @Autowired
    ProgressControlRepository progressControlRepository;
    @Autowired
    ProgressControlService progressControlService;
    public List<ProgressControl> findByGirard(Map<String,Object> map) {
        map.put("girard","file");
        return progressControlRepository.findByGirard(map);
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
     * 遍历目录
     */
    public void ergodicCatalog(){
        //第一、二步：遍历Cloud Render目录,根据firstStep去遍历01.Model，并添加到progress_control表中
       List<ProgressControl> progressControls = progressControlService.addProgressControls(1);
        //第三步：定时器调用执行maya脚本，执行成功之后返回结果1；--脚本尚未准备完成
       if (!progressControls.isEmpty()){
           //第四步：调用图片拼接功能(JAVA)，拼接结果保存到03.Joint(AO->SHADOW\yyy_Full.png、xxx_Color->PART\xxx\yyy_Full.png)
            for (int i = 0;i < progressControls.size();i++){
                if(progressControls.get(i).getProgress().equals("0%")){//判断这笔记录是新增的记录

                }else if (!progressControls.get(i).getProgress().equals("0%")
                        && !progressControls.get(i).getProgress().equals("100%")){//这笔记录是已经存在的
                    //查看该记录处于当前的哪一个步骤，并更新进度

                }
            }
            //第五步
       }
    }

}
