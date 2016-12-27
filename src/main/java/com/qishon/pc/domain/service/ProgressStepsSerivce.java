package com.qishon.pc.domain.service;

import com.qishon.pc.domain.model.ProgressControl;
import com.qishon.pc.domain.model.ProgressSteps;
import com.qishon.pc.domain.repository.ProgressControlRepository;
import com.qishon.pc.domain.repository.ProgressStepsRepository;
import com.qishon.pc.domain.util.GirardEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xm on 2016/12/23.
 */
@Service
public class ProgressStepsSerivce {

    //获取渲染文件路径
    @Value("${run.path}")
    private String filePath;
    @Autowired
    ProgressStepsRepository stepsRepository;

    @Autowired
    ProgressControlRepository controlRepository;

    public List<ProgressSteps> findByGirardId(Map<String, Object> params){
        return  stepsRepository.findByGirardId(params);
    }

    public ProgressSteps updateCompletedFiles(ProgressSteps progressSteps){
        return stepsRepository.updateCompletedFiles(progressSteps);
    }
    /**
     * 添加步骤记录
     * @param stepKey 步骤枚举key
     * @param progressControl 所属款号
     * @return progressSteps
     */
    public ProgressSteps addProCtrlSteps(int stepKey, ProgressControl progressControl){
        ProgressSteps progressSteps = new ProgressSteps();
        progressSteps.setGirardId(progressControl.getId());
        progressSteps.setStepNo(GirardEnum.findByKey(stepKey).getFilePtah());
        progressSteps.setStepName(GirardEnum.findByKey(stepKey).getFileName());
        int sumFiles = getAllFiles(progressControl);
        progressSteps.setAllFiles(sumFiles);
        progressSteps.setCompletedFiles(0);
        return stepsRepository.addProCtrlSteps(progressSteps);
    }

    /**
     * 获取总文件数
     * @param progressControl
     * @return
     */
    public int getAllFiles(ProgressControl progressControl){
        String _filePath = filePath + "\\" + GirardEnum.findByKey(1).getFilePtah()+"\\"+progressControl.getGirard();
        File file = new File(_filePath);
        if (!file.exists()){
            throw new IllegalArgumentException("文件不存在，无法读取");
        }
        File[] files = file.listFiles();
        String fileName = "";
        for(int i = 0; i < files.length; i++){
            if (files[i].getName().indexOf("_02.csv") != -1){
                fileName = files[i].getName();
                break;
            }
        }
        if (fileName.equals("")){
            throw new IllegalArgumentException("文件不存在，无法读取");
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(_filePath+"\\"+fileName),"GBK"));
            String line;
            int num = 0;
            int sum = 0;
            String row = "";
            while ((line = br.readLine()) != null){
                String[] colunm = line.split(",");
                for (int i = 0; i < colunm.length;i++){
                    if (colunm[i].equals("帧数")){
                        num = i;
                        break;
                    }
                }
                row +=  line.split(",")[num].toString()+",";
            }
            row = row.substring(0,row.length()-1);
            row = row.replaceAll("帧数,","");
            String[] alls = row.split(",");
            int allFiles = 0;
            for(int j = 0;j < alls.length; j++){
                allFiles += Integer.parseInt(alls[j]);
            }
            br.close();
            return allFiles;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("文件不存在，无法读取");
        }
    }

    /**
     * 获取每一步骤已完成的文件数量
     * @param progressSteps
     * @return Integer
     */
    public int getCompressFiles(ProgressSteps progressSteps){
        //获取款号
        ProgressControl progressControl = controlRepository.findById(progressSteps.getGirardId());
        //获取当前步骤文件路径: D:\\CloudRender\\步骤编码\\款号\\xx_Color文件夹\\图片文件
        String curPath = filePath + "\\"+progressSteps.getStepNo()+"\\"+progressControl.getGirard();
        File file = new File(curPath);
        if (!file.exists()){
            throw new IllegalArgumentException("文件路径报错");
        }
        File[] files = file.listFiles();
        int result = 0;
        for (int k =0;k < files.length;k++){
            if (files[k].getName().toUpperCase().indexOf("_COLOR") > -1){
                File renderFile = new File(curPath + "\\"+files[k].getName());
                result += renderFile.listFiles().length;
            }
        }
        return result;
    }
}
