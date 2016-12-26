package com.qishon.pc;

import com.qishon.pc.domain.util.GirardEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

/**
 * Created by xm on 2016/12/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileColunmTest {

    @Test
    public void test(){
        String _filePath = "D:\\CloudRender" + "\\" + "01.Model"+"\\"+"ANTA24";
        File file = new File(_filePath);
        File[] files = file.listFiles();
        String fileName = "";
        for(int i = 0; i < files.length; i++){
            if (files[i].getName().indexOf("_02.csv") != -1){
                fileName = files[i].getName();
                break;
            }
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(_filePath+"\\"+fileName),"GBK"));
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
            System.out.println("总数 = "+allFiles);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
