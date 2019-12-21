package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/*
 * @Author haozhao
 * @Description word count with HDFS api
 * 读取文件
 * 统计，对每一行数据都要进行业务处理
 * 将结果缓存起来
 * 输出到HDFS
 * @Date  12/19/19
 * @Param
 * @return
 **/
public class HDFSWCApp02 {
    public static void main(String[] args) throws Exception {
        //读取文件

        Properties properties = ParamsUtils.getProperties();
        Path input = new Path(properties.getProperty(Constants.INPUT_PATH));
        //获取要操作的fs文件系统
        FileSystem fs = FileSystem.get(new URI(properties.getProperty(Constants.HDFS_URI)), new Configuration(), "zhaohao");

        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(input,false);
        //通过反射获取一个新的对象
        Class<?> clazz =Class.forName(properties.getProperty(Constants.MAPPER_CLASS));
        ImoocMapper mapper =(ImoocMapper) clazz.newInstance();

        ImoocContext context = new ImoocContext();
        //读取出来
        while (iterator.hasNext()){
            LocatedFileStatus file = iterator.next();
            FSDataInputStream in =fs.open(file.getPath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line ="";

            while ((line = reader.readLine())!=null){

                //开始业务逻辑处理
                mapper.map(line,context);
            }

            reader.close();
            in.close();

        }

        //将结果缓存



        Map<Object,Object> contextMap = context.getCacheMap();




        //将结果输出
        Path output = new Path(properties.getProperty(Constants.OUTPUT_PATH));

        FSDataOutputStream out = fs.create(new Path(output,new Path(properties.getProperty(Constants.OUTPUT_FILE))));

        //将缓存中的内容输出到out中
        Set<Map.Entry<Object,Object>> entries= contextMap.entrySet();
        for(Map.Entry<Object,Object> entry : entries){
            out.write((entry.getKey().toString() + "\t" + entry.getValue().toString() +"\n").getBytes());
        }

        out.close();
        fs.close();

        System.out.println("finished");





    }

}
