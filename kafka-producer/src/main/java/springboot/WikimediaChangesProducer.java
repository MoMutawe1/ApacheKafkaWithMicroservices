package springboot;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WikimediaChangesProducer {

    // we need to use kafkaTemplate to send a messages to a kafka broker
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // read a real-time wikimedia stream data
    public void sendMessage() throws InterruptedException {
        String topic = "wikimedia_recentchange";  // this is the topic name we created in KafkaTopicConfig class.

        // to read real-time stream data from wikimedia, we use event source
        // (this handler will be triggered whenever there's an event in a wikimedia).
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        // EventSource will connect to the source of data which is wikimedia in this case
        // the internal implementation of EventSource uses the ExecutorService to create the threads.
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        // filling all the real time data from the data source wikimedia
        EventSource eventSource = builder.build();
        //then we need to start this eventsource in a separate thread.
        eventSource.start();
        //after 10 min this should stop
        TimeUnit.MINUTES.sleep(10);
    }
}
