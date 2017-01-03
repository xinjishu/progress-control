package com.qishon.pc.domain.util;

/**
 * 枚举工具类
 * Created by yuquan.hu on 2016/12/24.
 */
public enum GirardEnum {
    /**
     * 款号
     */
    MODEL(1,"01.Model","款号"),
    /**
     * 渲染
     */
    Render(2,"02.Render","渲染"),
    /**
     * 拼接
     */
    JOINT (3,"03.Joint","拼接"),
    /**
     * 压缩
     */
    COMPRESS(4,"04.Compress","压缩"),
    /**
     *上传文件服务器
     */
    UPLOAD(5,"05.Upload","上传文件服务器"),
    /**
     * 保存到数据库
     */
    DATABASE(6,"06.Database","保存到数据库"),
    /**
     * 已完成
     */
    DONE(7,"07.Done","已完成");
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
    private int fileKey;

    public int getFileNo() {
        return fileKey;
    }

    public void setFileNo(int fileNo) {
        this.fileKey = fileNo;
    }

    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @param name fileName
     * @param key fileKey
     */
    private GirardEnum(int key, String path,String name) {
        filePtah = path;
        fileName = name;
        fileKey = key;
    }

    /**
     * 根据文件key获取文件路径
     * @param key
     * @return girardEnum
     */
    public static GirardEnum findByKey(int key){
        GirardEnum[] ges = GirardEnum.values();
        for(GirardEnum girardEnum :ges){
            if (girardEnum.getFileNo() == key);
            return  girardEnum;
        }
        throw new IllegalArgumentException("Key值非法，无法返回正确的枚举对象");
    }

    /**
     * 根据文件编码查询
     * @param code
     * @return
     */
    public static GirardEnum findByPath(String code){
        GirardEnum[] ges = GirardEnum.values();
        for(GirardEnum girardEnum :ges){
            if (girardEnum.getFilePtah().equalsIgnoreCase(code));
            return  girardEnum;
        }
        throw new IllegalArgumentException("Key值非法，无法返回正确的枚举对象");
    }
}
