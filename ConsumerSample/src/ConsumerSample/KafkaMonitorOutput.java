package ConsumerSample;

import KafkaConsumerLib.IOutputter;
import Messages.IMessage;

import Messages.MessageFactory;
import Producer.IProducer;
import Producer.producerFactory;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class KafkaMonitorOutput  implements IOutputter {

    IProducer kafkaProducer;
    private Logger logger ;
    private String brokerList;
    private static MessageFactory mf = new MessageFactory();

    public KafkaMonitorOutput(String brokerList)
    {
        logger = LogManager.getLogger(KafkaMonitorOutput.class.getName());
        this.brokerList = brokerList;
        kafkaProducer = producerFactory.getProducer(brokerList, logger)   ;

    }

    @Override
    public void writeString(String data) {



        IMessage m = mf.createTraceMessage(mf.createNewMessage(data),"test" )  ;

        kafkaProducer.send(m);
    }
}
