package cn.gw.demo2.async;

import java.util.concurrent.atomic.AtomicInteger;

public class Teset {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        Thread[] threads = new Thread[50];
        for (int i = 0; i < 50; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        count.incrementAndGet();
                    }
                }
            });
            threads[i].start();
        }
        while (Thread.activeCount() > 1) {//等所有的线程跑完
            Thread.yield();
        }
        System.out.println(count);
    }
}
