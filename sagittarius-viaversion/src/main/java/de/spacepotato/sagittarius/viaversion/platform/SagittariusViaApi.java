package de.spacepotato.sagittarius.viaversion.platform;

import com.viaversion.viaversion.ViaAPIBase;
import de.spacepotato.sagittarius.entity.Player;
import de.spacepotato.sagittarius.network.protocol.PacketReceiver;
import io.netty.buffer.ByteBuf;

public class SagittariusViaApi extends ViaAPIBase<Player> {

	@Override
	public int getPlayerVersion(Player player) {
		return getPlayerVersion(player.getUUID());
	}

	@Override
	public void sendRawPacket(Player player, ByteBuf packet) {
		((PacketReceiver) player).sendPacket(packet);
	}

}
