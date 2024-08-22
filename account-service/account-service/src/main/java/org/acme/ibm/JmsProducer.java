package org.acme.ibm;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.jms.JMSContext;
import javax.jms.Queue;

@ApplicationScoped
public class JmsProducer {

    @Inject
    QueueConfig queueConfig;

    public void sendMessage(String message) {
        try (JMSContext context = queueConfig.getConnectionFactory().createContext(queueConfig.user, queueConfig.password)) {
            Queue queue = context.createQueue("QUEUE1");
            System.out.println("Sending message to queue: " + queue);
            context.createProducer().send(queue, message);
            System.out.println("Message sent successfully: " + message);
        } catch (Exception e) {
            System.err.println("Error occurred during sending message.");
            e.printStackTrace();
        }
    }
}

