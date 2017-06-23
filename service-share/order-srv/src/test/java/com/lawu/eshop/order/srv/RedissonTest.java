package com.lawu.eshop.order.srv;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lawu.eshop.synchronization.lock.service.LockService;

/**
 * @author Leach
 * @date 2017/6/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderSrvApplication.class)
public class RedissonTest {

    @Autowired
    private LockService LockService;

    private ExecutorService executorService = new ThreadPoolExecutor(3000, 3500, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @SuppressWarnings("unused")
	@Ignore
    @Test
    public void lock() throws Exception {
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 20000; i++) {
        	String key = "order_srv_create_order" + i;
            Future<Integer> future = executorService.submit(new Callable<Integer>() {
                @Override
                public Integer call() {
                    try {
                    	if (!LockService.tryLock(key)) {
                    		System.out.println("锁还未释放");
                    		return 0;
                    	}
                    	LockService.unLock(key);
                    	System.out.println(key);
                        return 1;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            });

//            try {
//                System.out.println(future.get() + "-----" + key);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }

        }
        System.out.printf("Total time: %s", System.currentTimeMillis() - startTime);
    }

}
