package org.acme.ibm;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.ConnectionFactory;


@ApplicationScoped
public class QueueConfig {

    @ConfigProperty(name = "ibm.mq.user")
    public String user;

    @ConfigProperty(name = "ibm.mq.password")
    public String password;

    @ConfigProperty(name = "ibm.mq.host")
    public String host;

    @ConfigProperty(name = "ibm.mq.port")
    public int port;

    @ConfigProperty(name = "ibm.mq.channel")
    public String channel;

    @ConfigProperty(name = "ibm.mq.queue-manager")
    public String queueManager;

    @ConfigProperty(name = "ibm.mq.queues.inputQueue")
    public String inputQueue;



    private MQConnectionFactory mqConnectionFactory;

    @PostConstruct
    public void init() {
        try {
            mqConnectionFactory = new MQConnectionFactory();
            mqConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mqConnectionFactory.setHostName(host);
            mqConnectionFactory.setPort(port);
            mqConnectionFactory.setQueueManager(queueManager);
            mqConnectionFactory.setChannel(channel);
            mqConnectionFactory.setStringProperty(WMQConstants.USERID, user);
            mqConnectionFactory.setStringProperty(WMQConstants.PASSWORD, password);

            System.out.println("Connecting to MQ: " + host + ":" + port + " with channel " + channel + " and queue manager " + queueManager);
            System.out.println("Using user: " + user);

        } catch (Exception e) {
            System.err.println("Failed to initialize MQConnectionFactory");
            e.printStackTrace();
        }
    }


    public ConnectionFactory getConnectionFactory() {
        return  mqConnectionFactory;
    }
}
