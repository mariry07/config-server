package com.optical.component;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

/**
 * @author Gjing
 *
 * netty服务初始化器
 **/
@Mapper
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final BlockingQueue<String> queue;

    //队列大小，先这么丑陋的写一下
    private final int maxSize;
    public ServerChannelInitializer(BlockingQueue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ByteBuf[] delimiter = new ByteBuf[] {
                Unpooled.copiedBuffer("}".getBytes()),
                Unpooled.wrappedBuffer("ok".getBytes()),
//                Unpooled.wrappedBuffer(new byte[] { '\n' }),
        };

        //添加编解码
        socketChannel.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(10240, delimiter));
//        socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(10240, Unpooled.copiedBuffer("}".getBytes())));
//        socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
        socketChannel.pipeline().addLast(new NettyServerHandler(queue, maxSize));
        socketChannel.pipeline().addLast(new NettyOutBoundHandler());
    }
}
