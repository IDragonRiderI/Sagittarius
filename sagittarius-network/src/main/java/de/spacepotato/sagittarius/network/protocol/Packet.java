package de.spacepotato.sagittarius.network.protocol;

import java.io.DataOutputStream;
import java.io.UnsupportedEncodingException;

import de.spacepotato.sagittarius.nbt.NBT;
import de.spacepotato.sagittarius.nbt.NBTOutputStream;
import de.spacepotato.sagittarius.network.handler.ChildNetworkHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Packet {

	public void read(ByteBuf buf) throws Exception {
		
	}
	
	public void write(ByteBuf buf) throws Exception {
		
	}

	public void handle(ChildNetworkHandler childHandler) {
		
	}
	
	public abstract Packet createNewPacket();
	
	public abstract int getId();
	
	public ByteBuf encode() throws Exception {
		ByteBuf buf = Unpooled.buffer();
		write(buf);
		return buf;
	}
	
	public static long readVarLong(ByteBuf buf) {
		long l = 0L;
		int num = 0;

		while (true) {
			byte currentByte = buf.readByte();
			l |= (long) (currentByte & 127) << num++ * 7;
			if (num > 10) {
				throw new RuntimeException("VarLong too big!");
			}

			if ((currentByte & 128) != 128) {
				break;
			}
		}

		return l;
	}

	public static void writeVarLong(ByteBuf buf, long l) {
		while ((l & -128L) != 0L) {
			buf.writeByte((int) (l & 127L) | 128);
			l >>>= 7;
		}

		buf.writeByte((int) l);
	}

	public static int readVarInt(ByteBuf buf) {
		int i = 0;
		int j = 0;

		while (true) {
			int k = buf.readByte();
			i |= (k & 127) << j++ * 7;
			if (j > 5) {
				throw new RuntimeException("VarInt too big");
			}

			if ((k & 128) != 128) {
				break;
			}
		}

		return i;
	}

	public static void writeVarInt(ByteBuf buf, int i) {
		while ((i & -128) != 0) {
			buf.writeByte(i & 127 | 128);
			i >>>= 7;
		}

		buf.writeByte(i);
	}
	
	public static void writeVarInt(DataOutputStream buf, int i) throws Exception {
		while ((i & -128) != 0) {
			buf.writeByte(i & 127 | 128);
			i >>>= 7;
		}
		
		buf.writeByte(i);
	}

	public static String readString(ByteBuf buf) throws PacketException {
		return readString(buf, buf.readableBytes());
	}

	public static String readString(ByteBuf buf, int maxLength) throws PacketException {
		int size = readVarInt(buf);
		if (size > maxLength) {
			throw new PacketException("String longer than expected. [" + size + ">" + maxLength + "]");
		} else if (size > buf.readableBytes()) {
			throw new PacketException("String length exceeds packet length. [" + size + ">" + buf.readableBytes() + "]");
		} else {
			byte[] data = new byte[size];
			buf.readBytes(data);

			try {
				return new String(data, "UTF-8");
			} catch (UnsupportedEncodingException ex) {
				log.error("Encoding unknown: UTF-8 ", ex);
				return null;
			}
		}
	}

	public static void writeString(ByteBuf buf, String str) {
		try {
			byte[] b = str.getBytes("UTF-8");
			writeVarInt(buf, b.length);
			buf.writeBytes(b);
		} catch (UnsupportedEncodingException ex) {
			log.error("Encoding unknown: UTF-8 ", ex);
		}

	}

	public static void writeByteArray(ByteBuf buf, byte[] data) {
		writeVarInt(buf, data.length);
		buf.writeBytes(data);
	}

	public static byte[] readByteArray(ByteBuf buf) throws PacketException {
		return readByteArray(buf, buf.readableBytes());
	}
	
	public static byte[] readRest(ByteBuf buf) throws PacketException {
		byte[] b = new byte[buf.readableBytes()];
		buf.readBytes(b);
		return b;
	}

	public static byte[] readByteArray(ByteBuf buf, int maxLength) throws PacketException {
		int length = readVarInt(buf);
		if (length > maxLength) {
			throw new PacketException("Byte-Array longer than expected. [" + length + ">" + maxLength + "]");
		} else if (length > buf.readableBytes()) {
			throw new PacketException("Byte-Array length exceeds packet length. [" + length + ">" + buf.readableBytes() + "]");
		} else {
			byte[] data = new byte[length];
			buf.readBytes(data);
			return data;
		}
	}
	
	public static void writeAngle(ByteBuf buf, float angle) {
		buf.writeByte((byte) ((int) (angle * 256.0F / 360.0F)));
	}

	public static float readAngle(ByteBuf buf) {
		byte b = buf.readByte();
		float angle = (float) b * 360.0F / 256.0F;
		return angle;
	}

	public static void writeFixedPointNumber(ByteBuf buf, double d) {
		buf.writeInt((int) (d * 32.0D));
	}

	public static void writeFixedPointNumberByte(ByteBuf buf, double d) {
		buf.writeByte((byte) ((int) (d * 32.0D)));
	}

	public static double readFixedPointNumber(ByteBuf buf) {
		int i = buf.readInt();
		return (double) i / 32.0D;
	}

	public static double readFixedPointNumberByte(ByteBuf buf) {
		byte i = buf.readByte();
		return (double) i / 32.0D;
	}
	
	public static void writeNBT(ByteBuf buf, NBT nbt) throws Exception {
		try(NBTOutputStream out = new NBTOutputStream(new ByteBufOutputStream(buf))){
			if(nbt == null) out.write(0);
			else out.writeTag(nbt);			
		}
	}
}
