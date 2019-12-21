package com.imooc.bigdata.hadoop.mr.wc;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * @Author haozhao
 * @Description //TODO 
 * @Date  12/20/19
 * @Param
 * KEYIN offset, 每行数据起始位置的偏移量
 * VALUEIN Map任务读数据的value类型，其实就是一行行的字符串，string
 * KEYOUT 自定义实现输出的key类型
 * VALUEOUT 自定义输出的Value的类型
 *
 * Hadoop里的类型是支持序列化和反序列化的
 * @return 
 **/
public class WordCountMapper extends Mapper<LongWritable, Text,Text, IntWritable> {
    /*
     * @Author haozhao
     * @Description //TODO
     * @Date  12/20/19
     * @Param [key, value, context]
     * key
     * value 是要被拆开的文本
     * context 只是一个单纯的统计结构，可以重复
     * @return
     **/
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //把value对应的行数据按照指定的分隔符拆开
        String[] words = value.toString().split("\n");

        for (String word : words){
            context.write(new Text(word),new IntWritable(1));
        }

    }
}
