package com.qishon.pc.domain.service;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import com.qishon.pc.domain.util.GirardEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
    public ProgressControl findByGirardAndProgress(String girard){
        return progressControlRepository.findByGirardAndProgress(girard);
    }

    /**
     * add ProgressControl List
     * @param typeKey
     * @return progressControls
     */
    public List<ProgressControl> addProgressControls(int typeKey){
        List<ProgressControl> progressControlList = new ArrayList<>();
        String girardFilePtah = GirardEnum.findByKey(typeKey).getFilePtah();
        File file = new File(filePath+"\\"+girardFilePtah);
        //遍历CloudRender/01.Model目录下所有文件
        File[] files = file.listFiles();
        for(int i = 0; i < files.length; i++){
            File[] modelFiles = files[i].listFiles();
            ProgressControl progressControl = new ProgressControl();
            if (!checkUniqueForGirard(files[i].getName())){
                progressControl.setGirard(files[i].getName());
                progressControl.setProgress("0%");
                progressControl.setVersion(0);
                progressControl.setCurNode(1);
                progressControlRepository.addProgressControl(progressControl);
            }else{
                //数据库中每个款号进度<>100%的记录只有1笔
                progressControl = findByGirardAndProgress(files[i].getName());
            }
            progressControlList.add(progressControl);
        }
        return progressControlList;
    }

    /**
     * 校验款号是否已经存在(progress=100%则是历史记录，否则不是)
     * @param giradCode 款号
     * @return result
     */
    public boolean checkUniqueForGirard(String giradCode){
        boolean result = false;
        Map<String,Object> map = new HashMap<>();
        map.put("girard",giradCode);
        List<ProgressControl> progressControls = progressControlRepository.findByGirard(map);
        if (progressControls.isEmpty()){
            result = true;
        }else {
            //判断是否是历史记录
            for (int i = 0; i < progressControls.size();i++){
                //只要有不等于100%的，直接返回False
                if(!progressControls.get(i).getProgress().equals("100%")){
                    break;
                }
            }
        }
        return result;
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
