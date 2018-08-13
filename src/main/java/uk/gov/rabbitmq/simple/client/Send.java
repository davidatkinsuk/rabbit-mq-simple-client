package uk.gov.rabbitmq.simple.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Send {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // generally channels and connections should be retained and reused
        // although it's possible for a channel exception to make it unusable
        // for future invocations
        // "A classic anti-pattern to be avoided is opening a channel for each published message. Channels are supposed to be reasonably long-lived and opening a new one is a network round-trip which makes this pattern extremely inefficient."
        // also note that some channel operations are not thread safe. channel
        // pooling can be used to share channels if required
        // see https://www.rabbitmq.com/api-guide.html#channel-threads
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // will create the queue if it doesn't exist
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String message = String.format("Hello World @ %s",new SimpleDateFormat().format(new Date()));

            Map<String, Object> headers = new HashMap<>();
            headers.put("latitude",  51.5252949);
            headers.put("longitude", -0.0905493);

            channel.basicPublish("", QUEUE_NAME,
                    new AMQP.BasicProperties.Builder()
                        .contentType("text/plain")
                        .headers(headers)
                        .deliveryMode(2)
                        .priority(1)
                        .userId("guest")
                        .build(),
                    message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

        }

    }

}
