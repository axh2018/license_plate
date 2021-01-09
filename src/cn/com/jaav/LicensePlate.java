package cn.com.jaav;

import cn.com.jaav.util.Base64Util;
import cn.com.jaav.util.FileUtil;
import cn.com.jaav.util.HttpUtil;
import com.alibaba.fastjson.JSONPath;

import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * 车牌识别
 */
public class LicensePlate
{
    public static void licensePlate() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        try {
            // 本地文件路径
            String filePath = "car_0.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "24.0c6dcf0a0b08723598858d7609042200.2592000.1612671280.282335-23507526";
            String result = HttpUtil.post(url, accessToken, param);
            Object code = JSONPath.read(result.toString(), "$.words_result.number");
            System.out.println("车牌号：" + code);
            Object color = JSONPath.read(result.toString(), "$.words_result.color");
            System.out.println("颜色：" + color);
            //用第一个字符的可信度来代表可信度
            Object probability = JSONPath.read(result.toString(), "$.words_result.probability[0]");
            System.out.println("可信度：" + probability);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LicensePlate.licensePlate();
    }
}