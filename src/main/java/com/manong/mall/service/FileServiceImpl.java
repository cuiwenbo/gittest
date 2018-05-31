package com.manong.mall.service;

import com.manong.mall.api.FileService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by qjy on 2017/7/31.
 */
@Component("fileService")
public class FileServiceImpl implements FileService {
    @Value("file.path")
    private String filePath;
//    @Value("${connect_timeout}")
//    private int connect_timeout;
//    @Value("${network_timeout}")
//    private int network_timeout;
//    @Value("${charset}")
//    private String charset;
//    @Value("${http.tracker_http_port}")
//    private int http_tracker_http_port;
//    @Value("${http.anti_steal_token}")
//    private String http_anti_steal_token;
//    @Value("${http.secret_key}")
//    private String http_secret_key;
//    @Value("${tracker_server}")
//    private String[] tracker_server;

    @PostConstruct
    public void init() {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public byte[] read(String fileId) {
        try {
            return IOUtils.toByteArray(new FileInputStream(new File(filePath + "/" + fileId)));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public String save(int type, byte[] filedata, String fileext) {
        String id = System.currentTimeMillis() + fileext;
        try {
            IOUtils.write(filedata, new FileOutputStream(filePath + "/" + id));
            return id;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean delete(String fileid) {
        File file = new File(filePath + "/" + fileid);
        return file.delete();
    }

    @Override
    public String copy(String fileid) {
        byte[] buf = read(fileid);
        return save(1, buf, "data");
    }
}
