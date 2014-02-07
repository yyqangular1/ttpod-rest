package org.nerdronix.springnetty.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("serverHandler")
@Sharable
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {

	public void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
		System.out.print(msg);
		ctx.channel().writeAndFlush("Push :" + msg);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Channel is active\n");
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("\nChannel is disconnected");
		super.channelInactive(ctx);
	}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        messageReceived(ctx, msg);
    }
}
