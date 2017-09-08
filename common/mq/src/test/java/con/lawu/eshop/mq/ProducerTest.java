package con.lawu.eshop.mq;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lawu.eshop.mq.message.MessageProducerService;

/**
 * @author Leach
 * @date 2017/9/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test.xml"})
public class ProducerTest {
    @Autowired
    private MessageProducerService messageProducerService;

    @Test
    @Ignore
    public void tt() {
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message1");
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message2");
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message3");
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message4");
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message5");
        messageProducerService.sendMessage("test_topic1", "test_tag", "this is a message6");

        int max = 1000;
        while (--max > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
