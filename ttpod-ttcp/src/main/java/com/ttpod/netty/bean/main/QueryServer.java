package com.ttpod.netty.bean.main;

import com.ttpod.netty.Server;
import com.ttpod.netty.bean.codec.QueryReqDecoder;
import com.ttpod.netty.bean.codec.QueryResEncoder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

import java.util.concurrent.Executors;

/**
 * date: 14-1-28 下午1:11
 *
 * @author: yangyang.cong@ttpod.com
 */
public class QueryServer {
    public static void main(String[] args) {

        final  ChannelHandler queryServerHandler = new QueryServerHandler();

        final ChannelHandler frameEncoder = new ProtobufVarint32LengthFieldPrepender();
        final ChannelHandler queryResEncoder = new QueryResEncoder();
        final EventLoopGroup searchGroup = new NioEventLoopGroup(
                0, Executors.newCachedThreadPool()
        );
        new Server(new ChannelInitializer<SocketChannel>() {// (4)

            //            final QueryReqDecoder decoder =  ;
//            final QueryReqEncoder encoder =  ;
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline p = ch.pipeline();
                p.addLast("decoder", new QueryReqDecoder());
//                pipeline.addLast("encoder", encoder);
                p.addLast("frameEncoder",frameEncoder );
                p.addLast("queryResEncoder", queryResEncoder);

                p.addLast(searchGroup,"queryServerHandler", queryServerHandler);
            }
        });

        searchGroup.shutdownGracefully();
    }
}
