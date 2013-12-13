package ConsumerSample;

import KafkaConsumerLib.*     ;



public class ConsumerSample {

    public static void main(String[] args)
    {
        String zooKeeper = "localhost:2181" ;
        String groupId = "1";
        String topic = "ptTest";
        int threads = 4;
        ConsumerGroup example = new ConsumerGroup(zooKeeper, groupId, topic);
        example.run(threads);
        try {
            Thread.sleep(100000);
        }
        catch (InterruptedException ie)
        {
        }
        example.shutdown();
    }
}
