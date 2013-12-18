package ConsumerSample;

import KafkaConsumerLib.IOutputter;
import Producer.IProducer;
import Producer.producerFactory;
import Producer.producerUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class KafkaMonitorOutput  implements IOutputter {

    IProducer kafkaProducer;
    private Logger logger ;
    private String brokerList;

    public KafkaMonitorOutput(String brokerList)
    {
        logger = LogManager.getLogger(KafkaMonitorOutput.class.getName());
        this.brokerList = brokerList;
        kafkaProducer = producerFactory.getProducer(brokerList, logger)   ;
    }

    @Override
    public void writeString(String data) {


        System.out.println("the trace producer")    ;
        //kafkaProducer.send(data);
    }
}
