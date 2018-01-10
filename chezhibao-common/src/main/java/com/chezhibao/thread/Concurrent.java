/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: Concurrent.java
 * Author:   zhangdanji
 * Date:     2017年11月23日
 * Description:   
 */
package com.chezhibao.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangdanji
 */
public class Concurrent {
    private CyclicBarrier cyclicBarrier;

    public Concurrent(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }

    public void start(){
        int nworkers = cyclicBarrier.getParties();
        ExecutorService executor = Executors.newFixedThreadPool(nworkers);
        for(int i = 0; i < nworkers; i ++){
            executor.execute(new Worker(cyclicBarrier,"worker-" + i));
        }
        executor.shutdown();
    }

    public static void main(String[] args) {
        Concurrent c = new Concurrent(new CyclicBarrier(10,new Action()));
        c.start();
    }
}

class Action implements Runnable{

    @Override
    public void run() {
        System.out.println("action!!");
    }
}

class Worker implements Runnable{

    private CyclicBarrier cyclicBarrier;
    private String name;

    Worker(CyclicBarrier cyclicBarrier,String name){
        this.cyclicBarrier = cyclicBarrier;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            while(true){
                System.out.println(name + " is ready.");
                cyclicBarrier.await();
                System.out.println(name + " Go!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}