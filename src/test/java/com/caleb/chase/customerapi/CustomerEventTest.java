package com.caleb.chase.customerapi;

import java.time.Duration;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

import com.caleb.chase.customerapi.CustomerService;
import com.caleb.chase.customerapi.dto.CustomerDTO;


// The @EmbeddedKafka annotation on this class does two things. First, it starts
// an in-memory Kafka broker. Second, it registers that broker as a bean in the
// Spring context. The `@Autowired EmbeddedKafkaBroker embeddedKafkaBroker` you
// see below tells Spring to inject that bean into the test. We have to do that
// autowiring because `KafkaTestUtils.consumerProps()` needs to ask the broker
// "what address and port are you running on?" in order to consume from it.
// embeddedKafka is going to be on a random port each time (it randomly selects
// from available ports), so it's not something we can hardcode. Therefore the
// bean must be injected so it can be read and the port can be retrieved.
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"customer-events"})
class CustomerEventTest {
    @Autowired CustomerService customerService;
    // This
    @Autowired EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void creatingCustomer_publishesEvent() {
        // First, we set up a test consumer. That's because we need to test more
        // than just that `CustomerService.create()` doesn't throw. We need to
        // test that creating a consumer publishes an event to a topic. And
        // we're going to test that by creating a consumer that consumes and
        // event from that topic.


        // consumerProps generates a config map for a Kafka consumer. The first
        // argument is the name of the consumer group, "test-group". Kafka
        // requires a name, so we have to make sure that this has a name, even
        // though only one consumer group is existing while we run this test.
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);


        // cf stands for consumer factory. This line creates the consumer
        // instance. Once we define it, it becomes a life Kafka client that can
        // be used to create consumers. That consumer can then be passed as an
        // arg to the broker's consumeFromAnAEmbeddedTopic function to consume
        // from a topic.
        ConsumerFactory<String, String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

        // this the consumer instance itself. It's connected to the in-memory
        // Kafka broker we created, embeddedKafkaBroker, because
        // embeddedKafkaBroker was used to create the config map (consumerProps)
        // that was used to create the consumer factory that we're using to
        // create this consumer.
        Consumer<String, String> consumer = cf.createConsumer();

        customerService.create(new CustomerDTO("Alice", "alice@test.com"));

        // This subscribes our consumer, defined above, to the "customer-events"
        // topic that we're going to publish to. This all adds up to us being
        // able to create a customer and then verify that doing so published an
        // event to a topic.
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "customer-events");

        customerService.create(new CustomerDTO ("Alice", "alice@test.com"));

        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "customer-events", Duration.ofSeconds(5));
        assertThat(record.value()).contains("Alice");

        consumer.close();
    }
}
