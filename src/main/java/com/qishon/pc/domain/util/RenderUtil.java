package com.qishon.pc.domain.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yongwei.chen on 2016/12/26.
 * 图片拼接工具类
 */
public class RenderUtil {

    /**
     *
     * @Description: 拼接图片集（水平或垂直）
     * @param inputFileNameList      待拼接的图片集
     * @param outputFileName      生成的图片
     * @param isX                       （水平：true; 垂直：false）
     * @author zhibin.zhao
     * @date 2016年10月26日 上午11:20:17
     */
    public static List<String> inputFileNameList;
    public static String outputFileName;
    public static String FileName;
    public static void mergePicture(List<String> inputFileNameList, String outputFileName, boolean isX) {
        if (inputFileNameList == null || inputFileNameList.size() == 0) {
            return;
        }
        try {
            boolean isFirstPng = true;
            BufferedImage outputImg = null;
            int outputImgW = 0;
            int outputImgH = 0;
            for (String pngFileName : inputFileNameList) {
                if (isFirstPng) {
                    isFirstPng = false;
                    outputImg = ImageIO.read(new File(pngFileName));
                    outputImgW = outputImg.getWidth();
                    outputImgH = outputImg.getHeight();
                } else {
                    BufferedImage appendImg = ImageIO.read(new File(pngFileName));
                    int appendImgW = appendImg.getWidth();
                    int appendImgH = appendImg.getHeight();

                    if (isX) {
                        outputImgW = outputImgW + appendImgW;
                        outputImgH = outputImgH > appendImgH ? outputImgH : appendImgH;
                    } else {
                        outputImgW = outputImgW > appendImgW ? outputImgW : appendImgW;
                        outputImgH = outputImgH + appendImgH;
                    }

                    // create basic image
                    Graphics2D g2d = outputImg.createGraphics();
                    BufferedImage imageNew = g2d.getDeviceConfiguration().createCompatibleImage(outputImgW, outputImgH,
                            Transparency.TRANSLUCENT);
                    g2d.dispose();
                    g2d = imageNew.createGraphics();

                    int oldImgW = outputImg.getWidth();
                    int oldImgH = outputImg.getHeight();
                    g2d.drawImage(outputImg, 0, 0, oldImgW, oldImgH, null);
                    if (isX) {
                        g2d.drawImage(appendImg, oldImgW, 0, appendImgW, appendImgH, null);
                    } else {
                        g2d.drawImage(appendImg, 0, oldImgH, appendImgW, appendImgH, null);
                    }

                    g2d.dispose();
                    outputImg = imageNew;
                }
            }
            writeImageLocal(outputFileName, outputImg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void writeImageLocal(String fileName, BufferedImage image/*, JTextArea printJTextArea*/) {
        if (fileName != null && image != null) {
            try {
                File file = new File(fileName);
                ImageIO.write(image, "png ".trim(), file); //配合checkstyle
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void SplitJoint(String path,boolean isX){
        File f =new File(path);
        showDir(f,isX);
    }

    public static void showDir(File path,boolean isX){
        inputFileNameList = new ArrayList<String>();
        boolean isFirstPic = true;
        boolean iseffective;
        File[] files =path.listFiles();
        for(File file:files){
            if(file.isDirectory())
                showDir(file,isX);
            else {
                String regex = "([0-9]|[a-z]|[A-Z]|_)*_[0-2][0-9].png";
                iseffective = file.getName().matches(regex);
                if(iseffective)
                {
                    if(isFirstPic){
                        isFirstPic = false;
                        String division[] = file.getName().split("_");
                        FileName = division[0] + "_";
                        for(int j = 1;j < division.length - 1;j++){
                            FileName += division[j];
                            FileName += "_";
                        }
                        FileName += "Full.png";
                        outputFileName = file.getParentFile() + "\\" + FileName;
                        System.out.println(outputFileName);
                    }
                    inputFileNameList.add(file.toString());
                }
            }
        }
        if(inputFileNameList.size() == 24){
            mergePicture(inputFileNameList, outputFileName, isX);
        }
    }

}
