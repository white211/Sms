package cn.white.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Create by @author white
 *
 * @date 2017-11-01 15:16
 */
public class GetMessageCode1 {
    private static final String QUERY_PATH = "https://api.miaodiyun.com/20150822/industrySMS/sendSMS";
    private static final String ACCOUNT_SID = "xx";
    private static final String AUTH_TOKEN = "xx";

    public static String getCode(String telephone) {
        String random = getSms();
        String timeStamp = getTimestamp();
        String sig = getMD5(ACCOUNT_SID, AUTH_TOKEN, timeStamp);
//        String tamp = "【不黑科技】注册验证码："+random+"，如非本人操作，请忽略此短信。";
        String tamp = "【white科技】尊敬的用户，您的验证码为" + random;
        OutputStreamWriter out = null;
        BufferedReader br = null;
        StringBuilder sb = null;

        try {
            URL url = new URL(QUERY_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(10000);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            String args = getQueryArgs(ACCOUNT_SID, tamp, telephone, timeStamp, sig, "JSON");
            out.write(args);
            out.flush();
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                System.out.println(temp);
//                sb.append(temp);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(sb);
        String respCode = jsonObject.getString("respCode");
        String defaultrespCode = "00000";
        if (respCode.equals(defaultrespCode)) {
            return random;
        } else {
            return defaultrespCode;
        }
    }
    /*
    请求参数拼接
     */
    public static String getQueryArgs(String accountSid, String smsContent, String to, String timestamp, String sig, String respDataType) {
        return "accountSid=" + accountSid + "&smsContent=" + smsContent +
                " &to=" + to + "&timestamp=" + timestamp + "&sig=" + sig + "&respDataType=" + respDataType;
    }

    /*
    获取时间戳
     */
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /*
    获取MD5
     */
    public static String getMD5(String sid, String token, String timestamp) {
        StringBuilder result = new StringBuilder();
        String source = sid + token + timestamp;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(source.getBytes());
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    result.append("0" + hex);
                } else {
                    result.append(hex);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /*
    获取六位随机数 使用递归
     */
    public static String getSms() {
        String random = new Random().nextInt(1000000) + "";
        if (random.length() != 6) {
            return getSms();
        } else {
            return random;
        }
    }

    public static void main(String[] args) {
//        String timestamp = getTimestamp();
//        System.out.println(timestamp);
//        for(int i = 0;i<100;i++){
//            System.out.println(getSms());
//        }
        String code = getCode("18814142741");
        System.out.println(code);
    }
}
