package me.j360.nettyserver.echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created with j360-lts -> me.j360.nettyclient.
 * User: min_xu
 * Date: 2015/10/27
 * Time: 11:21
 * ËµÃ÷£º
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter{


    private final ByteBuf firstMessage;

    public EchoClientHandler() {
        //super();
        firstMessage = Unpooled.buffer(NettyClient.SIZE);
        for(int i=0;i<firstMessage.capacity();i++){
            firstMessage.writeByte((byte) i);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
