package de.spacepotato.sagittarius.network.protocol.play;

import java.util.List;
import java.util.UUID;

import de.spacepotato.sagittarius.mojang.SkinProperty;
import de.spacepotato.sagittarius.network.protocol.Packet;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ServerPlayerListItemPacket extends Packet {

	@Data
	public static class PlayerListEntry {
		
		private final UUID uuid;
		private final String name;
		private final SkinProperty[] properties;
		private final int gamemode;
		private final int ping;
		private final String displayName;
		
	}
	
	// 0 = add player
	// 1 = update gamemode
	// 2 = update latency
	// 3 = update display name
	// 4 = remove player
	private byte action;
	private List<PlayerListEntry> entries;
	
	@Override
	public void write(ByteBuf buf) throws Exception {
		writeVarInt(buf, action);
		writeVarInt(buf, entries.size());
		for (PlayerListEntry entry : entries) {
			buf.writeLong(entry.getUuid().getMostSignificantBits());
			buf.writeLong(entry.getUuid().getLeastSignificantBits());
			
			if (action == 0) {
				writeString(buf, entry.getName());
				writeVarInt(buf, entry.properties.length);
				for (SkinProperty property : entry.properties) {
					writeString(buf, property.getName());
					writeString(buf, property.getValue());
					buf.writeBoolean(property.getSignature() != null);
					if (property.getSignature() != null) {
						writeString(buf, property.getSignature());
					}
				}
			}
			
			if (action == 0 || action == 1) {
				writeVarInt(buf, entry.gamemode);
			}
			
			if (action == 0 || action == 2) {
				writeVarInt(buf, entry.ping);
			}
			
			if (action == 0 || action == 3) {
				buf.writeBoolean(entry.displayName != null);
				if (entry.displayName != null) {
					writeString(buf, entry.displayName);
				}
			}
		}
	}
	
	@Override
	public Packet createNewPacket() {
		return new ServerPlayerListItemPacket();
	}

	@Override
	public int getId() {
		return 0x38;
	}

}
