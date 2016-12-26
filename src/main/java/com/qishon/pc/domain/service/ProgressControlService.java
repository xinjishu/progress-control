package com.qishon.pc.domain.service;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import com.qishon.pc.domain.util.GirardEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuquan.hu on 2016/12/23.
 */
@Service
public class ProgressControlService {
    //获取渲染文件路径
    @Value("${run.path}")
    private String filePath;
    @Autowired
    ProgressControlRepository progressControlRepository;

    public List<ProgressControl> findByGirard(Map<String, Object> params){
        return progressControlRepository.findByGirard(params);
    }

    /**
     * add ProgressControl List
     * @return progressControls
     */
    public List<ProgressControl> addProgressControls(){
        List<ProgressControl> progressControlList = new ArrayList<>();
        String girardFilePtah = GirardEnum.findByKey(1).getFilePtah();
        File file = new File(filePath+"\\"+girardFilePtah);
        //遍历CloudRender/01.Model目录下所有文件
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++){
            File[] modelFiles = files[i].listFiles();
            ProgressControl progressControl = new ProgressControl();
            progressControl.setGirard(files[i].getName());
            progressControl.setProgress("0%");
            progressControl.setVersion(0);
            progressControl.setCurNode(1);
            progressControlRepository.addProgressControl(progressControl);
            progressControlList.add(progressControl);
        }
        return progressControlList;
    }

    /**
     * update progress
     * @param progressControl
     * @param progressValue
     * @return progressControl
     */
    public ProgressControl updateProgress(ProgressControl progressControl,String progressValue){

        progressControl.setProgress(progressValue);
        return progressControlRepository.updateProgress(progressControl);
    }
    public ProgressControl updateVersion(ProgressControl progressControl,int value){

        progressControl.setVersion(value);
        return progressControlRepository.updateProgress(progressControl);
    }
    public ProgressControl updateCurNode(ProgressControl progressControl,int value){

        progressControl.setCurNode(value);
        return progressControlRepository.updateProgress(progressControl);
    }

}
