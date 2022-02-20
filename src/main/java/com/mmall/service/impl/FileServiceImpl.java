package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;

import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-20 15:43
 * @Modified By:
 */
@Service("iFileService")
public class FileServiceImpl implements IFileService {
    //打印日志的声明
    private Logger logger = (Logger) LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();//获取原始文件名
        //扩展名
        String fileExtentionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtentionName;
        logger.info("开始上传文件,上传的文件名:{},上传的路径是:{},新的文件名是:{}", fileName, path, uploadFileName);


        File fileDir = new File(path);
        if (!fileDir.exists()) {
            //权限，判断是否存在之后
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);//完整的

        try {
            file.transferTo(targetFile);
            //文件已经上传成功
            // todo target文件上传到ftp服务器，
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // todo 上传完成之后，删除upload文件下的文件
            targetFile.delete();
        } catch (IOException e) {
            System.out.println("上传文件异常，本来应该是logger");
            e.printStackTrace();
            return null;
        }
        return targetFile.getName();
    }
}
