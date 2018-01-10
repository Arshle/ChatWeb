/*
 * Copyright (C), 2014-2017, 江苏乐博国际投资发展有限公司
 * FileName: NioServer.java
 * Author:   zhangdanji
 * Date:     2017年12月01日
 * Description:
 */
package com.chezhibao.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author zhangdanji
 */
public class NioServer implements Runnable{

    /**
     * 多路复用器
     */
    private Selector selector;
    /**
     * 建立读缓冲区
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    /**
     * 建立写缓冲区
     */
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public NioServer(int port){
        try {
            this.selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress(port));
            ssc.register(this.selector, SelectionKey.OP_ACCEPT);
            System.out.println("serverSocketChannel has registed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
       while(true){
           try {
               this.selector.select();
               Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
               while (iterator.hasNext()){
                   SelectionKey selectionKey = iterator.next();
                   iterator.remove();
                   if(selectionKey.isValid()){
                       if(selectionKey.isAcceptable()){
                           accept(selectionKey);
                       }
                       if(selectionKey.isReadable()){
                            read(selectionKey);
                       }
                       if(selectionKey.isWritable()){
                           write(selectionKey);
                       }
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }

       }
    }

    private void read(SelectionKey key){
        try {
            readBuffer.clear();
            SocketChannel socketChannel = (SocketChannel) key.channel();
            int count = socketChannel.read(readBuffer);
            if(count == -1){
                socketChannel.close();
                key.cancel();
                return;
            }
            readBuffer.flip();
            byte[] bytes = new byte[this.readBuffer.remaining()];
            this.readBuffer.get(bytes);
            String body = new String(bytes).trim();
            System.out.println("Server : " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accept(SelectionKey key){
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(SelectionKey key){

    }

    public static void main(String[] args) {
        new Thread(new NioServer(8765)).start();
    }
}
