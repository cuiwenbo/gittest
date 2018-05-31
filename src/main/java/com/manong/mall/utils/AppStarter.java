package com.manong.mall.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by boleli on 2017/3/14.
 */
public class AppStarter {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("starting...");
        CountDownLatch closeLatch = new CountDownLatch(1);
        new ClassPathXmlApplicationContext("classpath:/spring/*.xml");
        System.out.println("started...");
        try {
            closeLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
