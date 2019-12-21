package com.imooc.bigdata.hadoop.mr.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/*
 * @Description 使用MR本地文件进行统计
 * @Param 
 * @return 
 **/
public class WordCountLocalApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.setProperty("HADOOP_USER_NAME","zhaohao");

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://hadoop000:8020");

        //创建一个JOb
        Job job = Job.getInstance(configuration);

        //设置Job对应的参数
        job.setJarByClass(WordCountLocalApp.class);

        job.setMapperClass(WordCountMapper.class);

        job.setReducerClass(WordCountReducer.class);
        //设置Job对应的参数 ： Mapper
        job.setMapOutputKeyClass(Text.class);

        job.setMapOutputValueClass(IntWritable.class);
        //设置Job对应的参数 ： Reduce
        job.setOutputKeyClass(Text.class);

        job.setOutputValueClass(IntWritable.class);

        //设定输入输出的路径
        FileInputFormat.setInputPaths(job,new Path("input"));
        FileOutputFormat.setOutputPath(job, new Path("output"));

        //提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result?0:-1);

    }
}
