package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: July
 * @Description:
 * @Date Created in 2022-02-20 15:43
 * @Modified By:
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
