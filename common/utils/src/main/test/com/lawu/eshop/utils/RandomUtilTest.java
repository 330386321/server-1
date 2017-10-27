package com.lawu.eshop.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 随机编码生成测试类
 * 
 * @author jiangxinjun
 * @date 2017年10月24日
 */
public class RandomUtilTest {
    
    private final static int max = 10000;
    private final static String FILEPATH1 = "C:\\Users\\Administrator\\Desktop\\num1.csv";
    private final static String FILEPATH2 = "C:\\Users\\Administrator\\Desktop\\num2.csv";
    
    @Ignore
	@Test
    public void getTableNumRandomString() {
	    Runnable runnable1 = new Runnable() {
            public void run() {
                List<String> orderNumList = new ArrayList<>();
                for (int i = 1;i <= max; i++) {
                    orderNumList.add(RandomUtil.getTableNumRandomString(""));
                }
                File file = new File(FILEPATH1);
                try {
                    FileUtils.writeLines(file, orderNumList, true);
                    Thread.sleep(10000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable1 is end");
            }
        };
        Runnable runnable2 = new Runnable() {
            public void run() {
                List<String> orderNumList = new ArrayList<>();
                for (int i = 1;i <= max; i++) {
                    orderNumList.add(RandomUtil.getTableNumRandomString(""));
                }
                File file = new File(FILEPATH2);
                try {
                    FileUtils.writeLines(file, orderNumList, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("runnable2 is end");
            }
        };
        Thread thread1 = new Thread(runnable1);
        thread1.start();
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        
        for (int i = 0; i < 10000; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(thread1.isAlive());
                System.out.println(thread2.isAlive());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
    @Ignore
	@Test
    public void getTableNumRandomStringCompare() {
	    File file1 = new File(FILEPATH1);
        File file2 = new File(FILEPATH2);
        StringBuilder sb = new StringBuilder();
        try {
            List<String> contents1 = FileUtils.readLines(file1, "UTF-8");
            List<String> contents2 = FileUtils.readLines(file2, "UTF-8");
            for (String item : contents1) {
                if (contents2.contains(item)) {
                    sb.append(item).append("\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
    }
}
