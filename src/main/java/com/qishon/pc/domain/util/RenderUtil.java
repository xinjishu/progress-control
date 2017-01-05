package com.qishon.pc.domain.util;

import com.qishon.pc.domain.model.ProgressControl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by yuquan.hu on 2017/1/5.
 */
@Slf4j
@Component
public class RenderUtil{
    @Value("${maya.script.file}")
    private String scriptFile;
    @Value("${run.path}")
    private String filePath ;
    /**
     * 生成.mel文件
     * @param progressControl
     * @throws Exception
     */
    public void writeScriptFile(ProgressControl progressControl) throws  Exception {
        String mbFileName = "";//获取mb文件名
        String csv01FileName = "";//获取款号_01.csv
        String mbFilePath = filePath+"\\01.Model"+"\\"+progressControl.getGirard();
        File mbFile = new File(mbFilePath);
        File[] mbFiles = mbFile.listFiles();
        for(int i = 0;i < mbFiles.length;i++){
            if(mbFiles[i].getName().indexOf(progressControl.getGirard()+".mb") != -1){
                mbFileName = mbFiles[i].getName();
            }
            if (mbFiles[i].getName().indexOf("_01.csv") != -1){
                csv01FileName = mbFiles[i].getName();
            }
        }
        File _file = new File(scriptFile);
        if (!_file.exists()){
            _file.mkdir();
        }
        File file = new File(scriptFile+"\\"+"userSetup.mel");
        if (file.exists()){
            file.delete();
            file.createNewFile();
        }else {
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(("source \"DeadlineMayaClient.mel\";\r\n").getBytes());
        fileOutputStream.write(("file -f -options \"v=0;\"  -esn false  -ignoreVersion " +
                " -typ \"mayaBinary\" -o \""+mbFilePath+"\\"+mbFileName+"\";\r\n").getBytes());
        fileOutputStream.write(("addRecentFile(\""+mbFilePath+"\\"+mbFileName+"\", \"mayaBinary\");\r\n").getBytes());
        fileOutputStream.write(("global proc tjhUI();\r\n").getBytes());
        fileOutputStream.write(("{\r\n").getBytes());
        fileOutputStream.write(("     python \""+mbFilePath+"\\"+csv01FileName+"\";\r\n").getBytes());
        fileOutputStream.write(("}\r\n").getBytes());
        fileOutputStream.write(("tjhUI;\r\n").getBytes());
        fileOutputStream.close();
        //执行.mel文件
        String commandStr = "cmd /c "+scriptFile;
        if (this.exeCmd(commandStr)){
            log.info("userSetup.mel文件执行成功");
        }else {
            log.info("userSetup.mel文件执行失败");
        }
    }

    /**
     * 执行.mel文件
     * @param commandStr
     * @return
     */
    public Boolean exeCmd(String commandStr) {
        BufferedReader br = null;
        Boolean flag = false;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
            flag = true;
        } catch (Throwable e) {
            log.info("implement:" + e.getMessage());
            flag = false;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    log.info("implement2:" + e.getMessage());
                    flag = false;
                }
            }
        }
        return flag;
    }
}
