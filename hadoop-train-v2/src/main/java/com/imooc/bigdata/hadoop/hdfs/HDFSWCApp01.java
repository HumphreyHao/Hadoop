package com.imooc.bigdata.hadoop.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import sun.jvm.hotspot.memory.TenuredGeneration;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
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
public class HDFSWCApp01 {
    public static void main(String[] args) throws Exception {
        //读取文件
        Path input = new Path("");
        //获取要操作的fs文件系统
        FileSystem fs = FileSystem.get(new URI(""), new Configuration(), "zhaohao");

        RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(input,false);
        WordCountMapper mapper = new WordCountMapper();
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
        Path output = new Path("");

        FSDataOutputStream out = fs.create(new Path(output,new Path("")));

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
