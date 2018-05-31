package com.manong.mall.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Created by boleli on 2016/11/29.
 */
public class HttpUtils {
    public static HttpResult get(String url) {
        return get(url, null, null);
    }

    public static HttpResult get(String url, JSONObject config) {
        return get(url, null, config);
    }

    public static HttpResult get(String url, Map<String, String> params, JSONObject config) {
        url = addUrlQuery(url, params);
        HttpGet httpget = new HttpGet(url);

        return execute(url, httpget, config);
    }

    public static HttpResult post(String url, String postbody, JSONObject config) {
        HttpPost httppost = new HttpPost(url);

        try {
            httppost.setEntity(new StringEntity(postbody, "UTF-8"));
        } catch (Exception e) {
        }

        return execute(url, httppost, config);
    }

    public static HttpResult post(String url, Map params, JSONObject config) {
        HttpPost httppost = new HttpPost(url);

        List<NameValuePair> formparams = new ArrayList<>();
        if (params != null && params.size() > 0) {
            Iterator<String> keys = params.keySet().iterator();
            while (keys.hasNext()) {
                String key = keys.next();
                formparams.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
        }

        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "utf-8");
            httppost.setEntity(uefEntity);
        } catch (Exception e) {

        }

        HttpResult ret = execute(url, httppost, config);
        return ret;
    }

    private static CloseableHttpClient getHttpClient(boolean isHttps) {
        CookieStore cs = new BasicCookieStore();
        RequestConfig.Builder requestBuilder = RequestConfig.custom().setSocketTimeout(600 * 1000).setConnectTimeout(600 * 1000)
                .setConnectionRequestTimeout(600 * 1000).setCookieSpec(CookieSpecs.STANDARD_STRICT);
        RequestConfig requestConfig;
        requestConfig = requestBuilder.build();
        HttpClientBuilder builder;
        if (isHttps) {
            builder = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory())
                    .setDefaultCookieStore(cs).setDefaultRequestConfig(requestConfig);
        } else {
            builder = HttpClients.custom().setDefaultCookieStore(cs).setDefaultRequestConfig(requestConfig);
        }
        CloseableHttpClient httpclient = builder.build();
        return httpclient;
    }

    public static HttpResult execute(String url, HttpRequestBase request, JSONObject config) {

        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:39.0) Gecko/20100101 Firefox/39.0");
        request.setHeader("Accept", "*/*");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        request.setHeader("Accept-Encoding", "gzip, deflate");
        String rstType = "page";
        if (config != null) {
            JSONObject header = config.getJSONObject("header");
            if (MapUtils.isNotEmpty(header)) {
                header.forEach((k, v) -> request.setHeader(k, String.valueOf(v)));
            }
            rstType = config.getString("rstType");
        }

        HttpResult ret = new HttpResult();
        try (CloseableHttpClient httpClient = getHttpClient(url.startsWith("https://"));
             CloseableHttpResponse r = httpClient.execute(request)) {
            ret.setCode(r.getStatusLine().getStatusCode());
            if ("file".equals(rstType)) { // 返回类型是文件，表示下载请求
                HttpEntity entity = r.getEntity();
                byte[] file = IOUtils.toByteArray(entity.getContent());
                ret.setFile(file);
            } else {
                String resultencode = "utf-8";
                String retString = EntityUtils.toString(r.getEntity(), resultencode);
                ret.setData(retString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String arg0, SSLSocket arg1) throws IOException {
                }

                @Override
                public void verify(String arg0, X509Certificate arg1) throws SSLException {
                }

                @Override
                public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {
                }

            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

    private static String addUrlQuery(String url, Map<String, String> params) {
        if (MapUtils.isEmpty(params)) {
            return url;
        }

//        String querys = params.entrySet().stream()
//                .filter(p -> StringUtils.isNotBlank(p.getKey()) && StringUtils.isNotBlank(p.getValue()))
//                .map(p -> p.getKey() + "=" + p.getValue())
//                .reduce((a, b) -> a + "&" + b)
//                .orElse("");
        String querys ="";
        if (StringUtils.isNotBlank(querys)) {
            if (url.contains("?")) {
                url = url + "&" + querys;
            } else {
                url = url + "?" + querys;
            }
        }
        return url;
    }

    public static void main(String argb[]) {
        Map<String, String> params = new HashMap<>();
        params.put("wd", "test");
        String url = "http://www.baidu.com/s";

        HttpResult rst = post(url, params, null);
        System.out.println(rst.getCode());
        System.out.println(rst.getData());
    }

}
