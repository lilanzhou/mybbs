package com.ryan.service.kafka.consumer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryan.po.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
/**
 * Created by Administrator on 2019:04:20
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Component
public class MsgConsumer {
    //以下读取消费者属性值
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String servers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;


    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private String enableAutoCommit;


    @Value("${spring.kafka.consumer.auto-commit-interval-ms}")
    private String autoCommitInterval;


    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;


    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    @Value("${spring.kafka.consumer.session-timeout-ms}")
    private String sessionTimeoutMs;

    private static KafkaConsumer<String,String>consumer;

    public KafkaConsumer<String,String> createConsumer(){
        if (consumer==null){
           Properties props=new Properties();//读取配置文件

            ////Kafka集群连接串，可以由多个host:port组成
            props.put("bootstrap.servers", this.servers);
            props.put("group.id", this.groupId);
            props.put("enable.auto.commit", this.enableAutoCommit);
            props.put("auto.offset.reset", this.autoOffsetReset);
            props.put("auto.commit.interval.ms", this.autoCommitInterval);
            props.put("session.timeout.ms", this.sessionTimeoutMs);
            props.put("key.deserializer", this.keyDeserializer);
            props.put("value.deserializer", this.valueDeserializer);
           consumer=new KafkaConsumer<String, String>(props);
        }
        return consumer;
    }

    public synchronized List<Message> consumerMsg(Integer id){
      consumer=this.createConsumer();
      consumer.subscribe(Arrays.asList("reply"+id));
      List<Message> result=new ArrayList<>();
      synchronized (this){
          ConsumerRecords<String, String> records = consumer.poll(100);
          for (ConsumerRecord<String,String> re:records
               ) {
              System.out.println("收到了数据-》》》》：fetched from partition "
                      + re.partition() + ", offset: " + re.offset() + "," +
                      "key:" + re.key() + ", message: " + re.value());

              Message m = JSON.parseObject(re.value(), Message.class);
              result.add(m);
          }
          //提交已经拉取出来的offset,如果是手动模式下面,必须拉取之后提交,否则以后会拉取重复消息
           consumer.commitAsync();
//          try {
//              Thread.sleep(100);
//          } catch (InterruptedException e) {
//              e.printStackTrace();
//          }
      }
    return result;
    }

}
