package com.pegasus.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


// 多线程任务工具类
public class AsyncTaskUtil {
    // 创建一个固定大小的线程池
    private static final ExecutorService executor = Executors.newFixedThreadPool(5);

    // 模拟发送短信通知 (为了使用多线程，异步执行)
    public static void sendSmsNotification(String phone, String message) {
        executor.submit(() -> {
            try {
                // 模拟耗时操作，发送短信
                System.out.println("正在发送短信给 " + phone + "...");
                Thread.sleep(2000); // 睡2秒
                System.out.println("内容: " + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    // 应用关闭时清理线程池
    public static void shutdown() {
        executor.shutdown();
    }
}