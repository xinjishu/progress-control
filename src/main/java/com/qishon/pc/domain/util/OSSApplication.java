package com.qishon.pc.domain.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.aliyun.openservices.ClientConfiguration;
import com.aliyun.openservices.ClientException;
import com.aliyun.openservices.ServiceException;
import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.OSSErrorCode;
import com.aliyun.openservices.oss.OSSException;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

public class OSSApplication
{
    /**
     * 阿里云ACCESS_ID
     */
    private static String ACCESS_ID = "LTAIASxJkpdt4v0j";
    /**
     * 阿里云ACCESS_KEY
     */
    private  static String ACCESS_KEY = "0lC8ERdHliB3UbGqZq9cJBhsuy2wmG";
    /**
     * 阿里云OSS_ENDPOINT  青岛Url
     */
    private static String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";

    /**
     * 阿里云BUCKET_NAME  OSS
     */
    private static String BUCKET_NAME = "qishonpic";





    /**
     * 创建Bucket
     *
     * @param client  OSSClient对象
     * @param bucketName  BUCKET名
     * @throws OSSException
     * @throws ClientException
     */
    public static void ensureBucket(OSSClient client, String bucketName)throws OSSException, ClientException{
        try{
            if (!client.doesBucketExist(bucketName)){
                client.createBucket(bucketName);
            }
        }catch(ServiceException e){
            if(!OSSErrorCode.BUCKES_ALREADY_EXISTS.equals(e.getErrorCode())){
                throw e;
            }
        }
    }

    /**
     * 删除一个Bucket和其中的Objects
     *
     * @param client  OSSClient对象
     * @param bucketName  Bucket名
     * @throws OSSException
     * @throws ClientException
     */
    private static void deleteBucket(OSSClient client, String bucketName)throws OSSException, ClientException{
        ObjectListing ObjectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
        for(int i = 0; i < listDeletes.size(); i++){
            String objectName = listDeletes.get(i).getKey();
            System.out.println("objectName = " + objectName);
            //如果不为空，先删除bucket下的文件
            client.deleteObject(bucketName, objectName);
        }
        client.deleteBucket(bucketName);
    }

    /**
     * 把Bucket设置成所有人可读
     *
     * @param client  OSSClient对象
     * @param bucketName  Bucket名
     * @throws OSSException
     * @throws ClientException
     */
    private static void setBucketPublicReadable(OSSClient client, String bucketName)throws OSSException, ClientException{
        //创建bucket
        if (!client.doesBucketExist(bucketName)){
            client.createBucket(bucketName);
        }

        //设置bucket的访问权限， public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }

    /**
     * 上传文件
     *
     * @param client  OSSClient对象
     * @param bucketName  Bucket名
     * @param Objectkey  上传到OSS起的名
     * @param filename  本地文件名
     * @throws OSSException
     * @throws ClientException
     * @throws FileNotFoundException
     */
    private static void uploadFile(OSSClient client, String bucketName, String Objectkey, String filename)
            throws OSSException, ClientException, FileNotFoundException{
        File file = new File(filename);
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        System.out.print("\nObjectkey:-----------" + Objectkey);
        System.out.print("\nfilename:------------" + filename);
        //判断上传类型，多的可根据自己需求来判定
        if (filename.endsWith("xml")) {
            objectMeta.setContentType("text/xml");
        }
        else if (filename.endsWith("jpg")) {
            objectMeta.setContentType("image/jpeg");
        }
        else if (filename.endsWith("png")) {
            objectMeta.setContentType("image/png");
        }

        InputStream input = new FileInputStream(file);
        client.putObject(bucketName, Objectkey, input, objectMeta);
    }

    /**
     *  下载文件
     *
     * @param client  OSSClient对象
     * @param bucketName  Bucket名
     * @param Objectkey  上传到OSS起的名
     * @param filename 文件下载到本地保存的路径
     * @throws OSSException
     * @throws ClientException
     */
    private static void downloadFile(OSSClient client, String bucketName, String Objectkey, String filename)
            throws OSSException, ClientException {
        System.out.print("filename-----------------" + filename);
        CreateMultilayerFile(filename);
        client.getObject(new GetObjectRequest(bucketName, Objectkey),
                new File(filename));
    }
    /**
     *  遍历文件夹
     *
     */
    public static void showDir(OSSClient client, File path){
        boolean isFirstPic = true;
        boolean iseffective = false;
        File[] files =path.listFiles();
        for(File file:files){
            if(file.isDirectory())
                showDir(client,file);
            else {
                System.out.print("\n"+"--------------------------------"+ file.getName());
                String regex = "([0-9]|[a-z]|[A-Z]|_)*_[0-2][0-9].png";
                iseffective = file.getName().matches(regex);
                System.out.print("\n"+ iseffective);
                if(iseffective)
                    if(isFirstPic){
                        System.out.print("\n"+ file.getName());
                        isFirstPic = false;
                        String division[] = file.getName().split("_");
                        String FileName = division[0];
                        for(int j = 1;j < division.length;j++){
                            FileName += "_"+division[j];
                            // FileName += "_";
                        }
                        //FileName += ".png";
                        String outputFileName = file.getParentFile() + "\\" + FileName;
                        String OssOutPutFileName = outputFileName.substring(outputFileName.indexOf("D:") + 3);
                        OssOutPutFileName = "web3dpic"+"\\"+ OssOutPutFileName;

                        try {
                            ensureBucket(client, BUCKET_NAME);
                            setBucketPublicReadable(client, BUCKET_NAME);

                            System.out.println("正在上传...");
                            System.out.println("outputFileName" + outputFileName);

                            String downLoadPath = "D:\\downLoad\\" + OssOutPutFileName;
                            System.out.print("downLoadPath----------" + downLoadPath);


                            OssOutPutFileName = OssOutPutFileName.replaceAll("\\\\","/");
                            outputFileName = outputFileName.replace("\\","\\\\");
                            System.out.println("\nOssOutPutFileName" + OssOutPutFileName);
                            //uploadFile(client, BUCKET_NAME, OssOutPutFileName, outputFileName);


                            System.out.println("正在下载...");
                            downloadFile(client, BUCKET_NAME, OssOutPutFileName, downLoadPath);
                        }catch(Exception e){
                            e.printStackTrace();
                        } finally {
                            //deleteBucket(client, BUCKET_NAME);
                        }
                    }
            }
        }
    }
    /**
     *  创建多层文件
     *
     */
    private static boolean CreateMultilayerFile(String dir)
    {
        try {
            String[] lists=dir.split("\\.");
            int lastLength=lists[0].lastIndexOf("\\");
            //得到文件夹目录
            String Rootdir=lists[0].substring(0, lastLength);
            File dirPath = new File(Rootdir);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
        } catch (Exception e) {
            System.out.println("创建多层目录操作出错: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
