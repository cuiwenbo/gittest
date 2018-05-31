package com.manong.mall.utils;

import org.apache.commons.codec.binary.Base64OutputStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * 加密工具类
 * @author qjy
 * @Need commons-codec-*.*.jar
 */
public class Encryption {
    public static void main(String[] args) {
        String strSource = "ABCDEFG1234567890";
        String str = getMd5(strSource);
        System.out.println(strSource+",length:"+str.length()+",md5:"+str);
        str = getSha1(strSource);
        System.out.println(strSource+",length:"+str.length()+",Sha1:"+str);
        str = getSha256(strSource).toUpperCase();
        System.out.println(strSource+",length:"+str.length()+",Sha256:"+str);
        str = getSha384(strSource);
        System.out.println(strSource+",length:"+str.length()+",Sha384:"+str);
        str = getSha512(strSource);
        System.out.println(strSource+",length:"+str.length()+",Sha512:"+str);

        digestFile("doc\\1.txt", MessageDigestAlgorithms.MD5);
        String target = getBase64(strSource);
        System.out.println(strSource);
        System.out.println(target);
        System.out.println(getFromBase64(target));
    }

    /**
     * MD5加密,返回32位十六进制字符串
     */
    public static String getMd5(String source){
        return DigestUtils.md5Hex(source);
    }

    /**
     * sha1加密,返回40位十六进制字符串
     */
    public static String getSha1(String source){
        return DigestUtils.sha1Hex(source);
    }

    /**
     * sha256加密,返回64位十六进制字符串
     */
    public static String getSha256(String source){
        return DigestUtils.sha256Hex(source);
    }

    /**
     * sha384加密,返回96位十六进制字符串
     */
    public static String getSha384(String source){
        return DigestUtils.sha384Hex(source);
    }

    /**
     * sha512加密,返回128位十六进制字符串
     */
    public static String getSha512(String source){
        return DigestUtils.sha512Hex(source);
    }

    /**
     * 文件加密
     */
    public static void digestFile(String filename, String algorithm) {
        byte[] b = new byte[1024 * 4];
        int len = 0;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(filename);
            while ((len = fis.read(b)) != -1) {
                md.update(b, 0, len);
            }
            byte[] digest = md.digest();
            //System.out.println(Arrays.toString(digest));
            StringBuffer fileNameBuffer = new StringBuffer(128).append(filename).append(".").append(algorithm);
            fos = new FileOutputStream(fileNameBuffer.toString());
            OutputStream encodedStream = new Base64OutputStream(fos);
            encodedStream.write(digest);
            encodedStream.flush();
            encodedStream.close();
        } catch (Exception e) {
            System.out.println("Error computing Digest: " + e);
        } finally {
            try {
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * Base64加密
     */
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * Base64解密
     */
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}