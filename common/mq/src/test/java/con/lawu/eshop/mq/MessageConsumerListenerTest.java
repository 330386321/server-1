package con.lawu.eshop.mq;

import com.lawu.eshop.mq.message.impl.AbstractMessageConsumerListener;

/**
 *
 */
public class MessageConsumerListenerTest extends AbstractMessageConsumerListener {
    @Override
    public void consumeMessage(String topic, String tags, Object message) {
        System.out.println(message.toString());
    }
}
