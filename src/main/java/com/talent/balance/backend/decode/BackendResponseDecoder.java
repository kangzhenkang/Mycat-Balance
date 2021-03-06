/*
 * Copyright (c) 2013, OpenCloudDB/MyCAT and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software;Designed and Developed mainly by many Chinese 
 * opensource volunteers. you can redistribute it and/or modify it under the 
 * terms of the GNU General Public License version 2 only, as published by the
 * Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Any questions about this component can be directed to it's project Web address 
 * https://code.google.com/p/opencloudb/.
 *
 */
package com.talent.balance.backend.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talent.balance.backend.ext.BackendExt;
import com.talent.balance.common.BalancePacket;
import com.talent.mysql.ext.MysqlExt;
import com.talent.mysql.packet.response.HandshakePacket;
import com.talent.nio.api.Packet;
import com.talent.nio.communicate.ChannelContext;
import com.talent.nio.communicate.intf.DecoderIntf;

/**
 * 
 * 
 * @filename:	 com.talent.balance.backend.decode.BackendResponseDecoder
 * @copyright:   Copyright (c)2010
 * @company:     talent
 * @author:      谭耀武
 * @version:     1.0
 * @create time: 2013年12月25日 下午1:54:17
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2013年12月25日</td>	<td>谭耀武</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class BackendResponseDecoder implements DecoderIntf<Packet>
{
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(BackendResponseDecoder.class);

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{

	}

	/**
	 * 
	 */
	public BackendResponseDecoder()
	{

	}

	@Override
	public PacketWithMeta<Packet> decode(ByteBuf buffer, ChannelContext channelContext) throws DecodeException
	{
		BalancePacket backendResponsePacket = new BalancePacket();
		
		if (MysqlExt.getHandshakePacket(channelContext) == null && BackendExt.PROTOCOL_MYSQL.equals(channelContext.getProtocol())) {
			HandshakePacket handshakePacket = new HandshakePacket();
			PacketWithMeta<Packet> pwm = handshakePacket.decode(buffer);
			return pwm;
		}
		
		
		
		backendResponsePacket.setBuffer(Unpooled.copiedBuffer(buffer));

		List<Packet> packets = new ArrayList<Packet>();
		packets.add(backendResponsePacket);

		PacketWithMeta<Packet> packetWithMeta = new PacketWithMeta<Packet>();
		buffer.readerIndex(buffer.capacity());
		packetWithMeta.setPacketLenght(buffer.capacity());
		packetWithMeta.setPackets(packets);
		return packetWithMeta;
	}
}