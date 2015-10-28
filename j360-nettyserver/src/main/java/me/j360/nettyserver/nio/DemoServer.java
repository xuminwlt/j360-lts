package me.j360.nettyserver.nio;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created with j360-lts -> me.j360.nettyserver.nio.
 * User: min_xu
 * Date: 2015/10/27
 * Time: 15:05
 * 说明：
 */
public class DemoServer {

    public static void main(String[] args){
        Selector selector = null;
        ServerSocketChannel server = null;

        try {
            selector = Selector.open();
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(8080));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            while(true){
                selector.select();
                for(SelectionKey key:selector.selectedKeys()){
                    if(key.isConnectable()){
                        ((SocketChannel)key.channel()).finishConnect();
                    }
                    if(key.isAcceptable()){
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.socket().setTcpNoDelay(true);
                        client.register(selector,SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){

                    }
                }
            }
        }catch (Exception e){

        }finally {
            try {
                selector.close();
                server.socket().close();
                server.close();

            }catch (Exception e){

            }
        }
    }
}
