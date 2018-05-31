package com.manong.mall.api;

/**
 * Created by qjy on 2017/7/31.
 */
public interface FileService {
    int PRIVATE = 1;
    int PUTBLIC = 2;

    byte[] read(String fileId);
    /**
     *
     * @param type 1:read, 2:http
     * @param filedata
     * @param fileext
     * @return 1:filepath, 2:url
     */
    String save(int type, byte[] filedata, String fileext);

    boolean delete(String fileid);

    String copy(String fileid);
}
