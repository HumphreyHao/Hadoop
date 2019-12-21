package com.imooc.bigdata.hadoop.hdfs;

import java.io.IOException;
import java.util.Properties;

/*
 * @Author haozhao
 * @Description 读取配置文件
 * @Date  12/20/19
 * @Param
 * @return
 **/
public class ParamsUtils {
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(ParamsUtils.class.getClassLoader().getResourceAsStream("wc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Properties getProperties() throws Exception{
        return  properties;
    }
}
