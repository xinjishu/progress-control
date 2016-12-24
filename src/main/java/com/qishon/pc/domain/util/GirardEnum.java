package com.qishon.pc.domain.util;

/**
 * 枚举工具类
 * Created by yuquan.hu on 2016/12/24.
 */
public enum GirardEnum {
    /**
     * 款号
     */
    MODEL(1,"01.Model"),
    /**
     * 渲染
     */
    Render(2,"02.Render"),
    /**
     * 拼接
     */
    JOINT (3,"03.Joint"),
    /**
     * 压缩
     */
    COMPRESS(4,"04.Compress"),
    /**
     *上传文件服务器
     */
    UPLOAD(5,"05.Upload"),
    /**
     * 保存到数据库
     */
    DATABASE(6,"06.Database"),
    /**
     * 已完成
     */
    DONE(7,"07.Done");
    /**
     * 文件路径
     */
    private String filePtah;

    public String getFilePtah() {
        return filePtah;
    }

    public void setFilePtah(String filePtah) {
        this.filePtah = filePtah;
    }

    /**
     * 文件key
     */
    private int fileNo;

    public int getFileNo() {
        return fileNo;
    }

    public void setFileNo(int fileNo) {
        this.fileNo = fileNo;
    }

    /**
     * @param fileName fileName
     * @param key fileKey
     */
    private GirardEnum(int key, String fileName) {
        fileName = filePtah;
        key = fileNo;
    }

    /**
     * 根据文件key获取文件路径
     * @param key
     * @return girardEnum
     */
    public static String findByKey(int key){
        GirardEnum[] ges = GirardEnum.values();
        for(GirardEnum girardEnum :ges){
            if (girardEnum.getFileNo() == key);
            return  girardEnum.getFilePtah();
        }
        throw new IllegalArgumentException("Key值非法，无法返回正确的枚举对象");
    }
}
