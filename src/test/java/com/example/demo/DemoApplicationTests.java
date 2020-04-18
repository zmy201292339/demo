package com.example.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() throws Exception {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 1000; i++) {
            sb.append(getRandomIp()).append(",");
        }
        String para = sb.substring(0, sb.length() - 2);
        long l1 = System.currentTimeMillis();
        System.out.println(sendPost("http://localhost:8095/v2/iocBatchPush", para));
        long l2 = System.currentTimeMillis();

        System.out.println(l2 - l1);
    }

    public static String sendPost(String url, String param) throws Exception {
        HttpPost httpPost = new HttpPost(url + "?keys=" + param);
        httpPost.setHeader("Content-Type", "*/*");
        httpPost.setHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlZHIiLCJjdXN0b21LZXkiOiJhcGktZ2F0ZXdheSBjbG91ZCBjb25zb2xlIiwiZGV2aWNlSWQiOiJERVZJQ0UtQVBJLUdBVEVXQVktQ0xPVUQiLCJjdXN0b21FeHRJbmZvIjoiZGVmYXVsdCBjdXN0b20gb2YgYXBpLndlYnNhYXMuY24vdGkiLCJyb2xlIjpbImFkbWluIl0sImV4cCI6MTU4NzMyNjAxNX0.gUkd8O2JiQz8AyWiTjPS_ri2vYJ5NYMsx8VyCpqAl-io7-vfgVlvR0J5DU3rKnv--uszcJUwARR6oGRlsRvTPw");
        httpPost.setHeader("deviceId", "DEVICE-API-GATEWAY-CLOUD");
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        return responseContent;
    }

    public static String getRandomIp() {

        // ip范围
        int[][] range = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
                {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
                {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
                {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
                {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
                {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
                {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
                {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
                {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
                {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成IP地址
     */
    public static String num2ip(int ip) {
        int[] b = new int[4];
        String x = "";
        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        x = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer.toString(b[3]);

        return x;
    }
}
