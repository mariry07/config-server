package com.optical.common;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


/**
 * @author mary
 * @data 2021/5/3
 */

public class HttpUtil {
//    public static String sendPost(String urlParam) throws HttpException, IOException {
//        // 创建httpClient实例对象
//        HttpClient httpClient = new HttpClient();
//        // 设置httpClient连接主机服务器超时时间：15000毫秒
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
//        // 创建post请求方法实例对象
//        PostMethod postMethod = new PostMethod(urlParam);
//        // 设置post请求超时时间
//        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
//        postMethod.addRequestHeader("Content-Type", "application/json");
//
//        httpClient.executeMethod(postMethod);
//
//        String result = postMethod.getResponseBodyAsString();
//        postMethod.releaseConnection();
//        return result;
//    }
//    public static String sendGet(String urlParam) throws HttpException, IOException {
//        // 创建httpClient实例对象
//        HttpClient httpClient = new HttpClient();
//        // 设置httpClient连接主机服务器超时时间：15000毫秒
//        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
//        // 创建GET请求方法实例对象
//        GetMethod getMethod = new GetMethod(urlParam);
//        // 设置post请求超时时间
//        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
//        getMethod.addRequestHeader("Content-Type", "application/json");
//
//        httpClient.executeMethod(getMethod);
//
//        String result = getMethod.getResponseBodyAsString();
//        getMethod.releaseConnection();
//        return result;
//    }
//
//    public static void test2() {
//        String uri = "http://47.105.53.60:11007/xitang/open/device/log/XTDYT3";
//
//
//        try {
//            URL url = new URL(uri + "/test2");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//            connection.setDoInput(true); // 设置可输入
//            connection.setDoOutput(true); // 设置该连接是可以输出的
//            connection.setRequestMethod("POST"); // 设置请求方式
//            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("code", "001");
//            data.put("name", "测试");
//            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
//            pw.write(objectMapper.writeValueAsString(data));
//            pw.flush();
//            pw.close();
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            String line = null;
//            StringBuilder result = new StringBuilder();
//            while ((line = br.readLine()) != null) { // 读取数据
//                result.append(line + "\n");
//            }
//            connection.disconnect();
//
//            System.out.println(result.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//
//    public static void main(String[] args) throws HttpException, IOException {
////        String url ="http://int.dpool.sina.com.cn/iplookup/iplookup.php?ip=120.79.75.96";
//        String url ="http://47.105.53.60:11007/xitang/open/device/log/XTDYT3";
//        test2();
//        System.out.println(sendPost(url));
//        System.out.println(sendGet(url));
//    }
//}


//public class HttpUtil {
    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数
     * @param authorization
     *            请求参数(允许为空)
     * @param cookie
     *            请求参数(允许为空)
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, Object> param, String authorization, String cookie) {
        String result = "";
        BufferedReader in = null;
        try {
            StringBuffer query = new StringBuffer();

            for (Map.Entry<String, Object> kv : param.entrySet()) {
                query.append(URLEncoder.encode(kv.getKey(), "UTF-8") + "=");
                query.append(URLEncoder.encode(kv.getValue().toString(), "UTF-8") + "&");
            }
            if (query.lastIndexOf("&") > 0) {
                query.deleteCharAt(query.length() - 1);
            }

            String urlNameString = url + "?" + query.toString();
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (authorization != null){
                connection.setRequestProperty("Authorization", authorization);
            }
            if (cookie != null){
                connection.setRequestProperty("Cookie", cookie);
            }
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数
     * @param authorization
     *            请求参数(允许为空)
     * @param cookie
     *            请求参数(允许为空)
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> param, String authorization, String cookie) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            String para = "";
            for (String key : param.keySet()) {
                para += (key + "=" + param.get(key) + "&");
            }
            if (para.lastIndexOf("&") > 0) {
                para = new String(para.substring(0, para.length() - 1));
            }
            String urlNameString = url + "?" + para;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            HttpURLConnection  conn = (HttpURLConnection)realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (authorization != null){
                conn.setRequestProperty("Authorization", authorization);
            }
            if (cookie != null){
                conn.setRequestProperty("Cookie", cookie);
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送post请求
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数
     * @param authorization
     *            请求参数(允许为空)
     * @param cookie
     *            请求参数(允许为空)
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param, String authorization, String cookie) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url + "?" + param);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            if (authorization != null){
                conn.setRequestProperty("Authorization", authorization);
            }
            if (cookie != null){
                conn.setRequestProperty("Cookie", cookie);
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);

            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }



    public static String post(String actionUrl, String params)
            throws IOException {
        String serverURL = actionUrl;
        StringBuffer sbf = new StringBuffer();
        String strRead = null;
        URL url = new URL(serverURL);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");//请求post方式
        connection.setDoInput(true);
        connection.setDoOutput(true);
        //header内的的参数在这里set
        //connection.setRequestProperty("key", "value");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.connect();
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
        //body参数放这里
        writer.write(params);
        writer.flush();
        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        while ((strRead = reader.readLine()) != null) {
            sbf.append(strRead);
            sbf.append("\r\n");
        }
        reader.close();
        connection.disconnect();
        String results = sbf.toString();
        return results;
    }



//    public void uploadFileOrg(Map map){
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        long stateTime = System.currentTimeMillis();
//        log.info("开始调用article定时任务生成文章,当前系统时间==={}",stateTime);
//        try {
//            HttpPost httppost = new HttpPost(clockAuditArticle);
//
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000)
//                    .setSocketTimeout(200000).build();
//            httppost.setConfig(requestConfig);
//            Map<String, Object> paramMap = new HashMap<String, Object>();
//            paramMap.put("unique_id",System.currentTimeMillis());
//            httppost.setEntity(new StringEntity(JSONObject.toJSONString(paramMap), ContentType.create("application/json", "utf-8")));
//
//            httppost.setHeader("Authorization","aggregete");
//            httppost.setHeader("Content-Type", "application/json");
//
//            CloseableHttpResponse response = httpclient.execute(httppost);
//            try {
//                HttpEntity resEntity = response.getEntity();
//                if (resEntity != null) {
//                    log.info("调用article定时任务生成文章成功,用时====={}",System.currentTimeMillis()-stateTime);
//                    String responseEntityStr = EntityUtils.toString(response.getEntity());
//                    System.out.println("--上传返回信息--"+responseEntityStr);
//                }else{
//                    throw new AppBusinessException(UserErrorCode.UPLOAD_ERROR);
//                }
//                EntityUtils.consume(resEntity);
//            } finally {
//                response.close();
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpclient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }




    public static void main(String[] args) {
//        String jsionResult=HttpUtil.sendPost("http://47.105.53.60:11007/xitang/open/device/log/XTDYT3",body,authorization,cookie);

        Map map = new HashMap();
        map.put("type", 7);
        map.put("device_code", "xtd20211700012");
        String param = JSON.toJSONString(map);
        try{

            String jsionResult=HttpUtil.post("http://47.105.53.60:11007/xitang/open/device/log/XTDYT3",param);
            System.out.println(jsionResult);

        }catch (Exception e) {

        }


    }
}