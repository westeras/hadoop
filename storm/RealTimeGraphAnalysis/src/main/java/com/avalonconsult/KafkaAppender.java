package com.avalonconsult;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;


import java.util.Properties;


/**
 * Created by adam on 2/17/15.
 */
public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String topic;
    private Producer<String, String> producer;
    private Formatter formatter;
    private String brokerList;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Producer<String, String> getProducer() {
        return producer;
    }

    public void setProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public String getBrokerList() { return this.brokerList; }

    public void setBrokerList(String brokerList) { this.brokerList = brokerList; }

    @Override
    protected void append(ILoggingEvent event) {
        String payload = this.formatter.format(event);
        KeyedMessage<String, String> data = new KeyedMessage<String, String>(this.topic, payload);
        this.producer.send(data);
    }

    @Override
    public void start() {
        super.start();
        Properties props = new Properties();
        props.put("metadata.broker.list", this.brokerList);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        ProducerConfig config = new ProducerConfig(props);
        this.producer = new Producer<String, String>(config);
    }

    @Override
    public void stop() {
        super.stop();
        this.producer.close();
    }
}
