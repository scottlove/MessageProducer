package KafkaConsumerLib;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.List;


public class Consumer implements Runnable {
    private KafkaStream m_stream;
    private int m_threadNumber;
    private List<IOutputter> outputs;

    public Consumer(KafkaStream a_stream, int a_threadNumber, List<IOutputter> outputters)
    {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
        outputs = outputters;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while (it.hasNext())
        {
            String out = "Thread " + m_threadNumber + ": " + new String(it.next().message());
            //System.out.println(out);
            for(IOutputter output : outputs)
            {
                output.writeString(out);
            }
        }

        System.out.println("Shutting down Thread: " + m_threadNumber);
    }



}
