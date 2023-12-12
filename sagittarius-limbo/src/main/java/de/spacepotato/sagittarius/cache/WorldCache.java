package de.spacepotato.sagittarius.cache;

import java.util.ArrayList;
import java.util.List;

import com.carrotsearch.hppc.cursors.ObjectCursor;

import de.spacepotato.sagittarius.entity.PlayerImpl;
import de.spacepotato.sagittarius.network.protocol.PacketContainer;
import de.spacepotato.sagittarius.network.protocol.play.ServerMapChunkBulkPacket;
import de.spacepotato.sagittarius.world.Chunk;
import de.spacepotato.sagittarius.world.ChunkImpl;
import de.spacepotato.sagittarius.world.WorldImpl;
import de.spacepotato.sagittarius.world.loader.BlankWorldLoader;
import de.spacepotato.sagittarius.world.loader.WorldEditSchematicLoader;
import de.spacepotato.sagittarius.world.loader.WorldLoader;
import de.spacepotato.sagittarius.world.metadata.BlockMetadata;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorldCache {

	private List<PacketContainer> worldPackets;
	private List<WorldLoader> loaders;
	
	public WorldCache() {
		loaders = new ArrayList<>();
		loaders.add(new WorldEditSchematicLoader());
		loaders.add(new BlankWorldLoader());
	}
	
	public void load() {
		WorldImpl world = null;
		for (WorldLoader loader : loaders) {
			if (loader.isSupported()) {
				log.info("Loading world...");
				world = loader.loadDefaultWorld();
				break;
			}
		}
		
		List<PacketContainer> worldPackets = new ArrayList<>();
		
		List<Chunk> chunks = new ArrayList<>();
		for (ObjectCursor<ChunkImpl> c : world.getChunks().values()) {
			chunks.add(c.value);
		}
		
		// We try to send as few packets as possible.
		// if we do end up with a packet that is too big, then we're sending the next chunks in a separate packet.
		List<Integer> breaks = new ArrayList<>();
		int totalSize = 0;
		for (int i = 0; i < chunks.size(); i++) {
			Chunk chunk = chunks.get(i);
			totalSize += chunk.getSizeEstimate((short) chunk.calculatePrimaryBitMask(), true, true);
			if (totalSize > 1_900_000) {
				breaks.add(i);
				totalSize = 0;
			}
		}
		breaks.add(chunks.size());
		
		List<List<Chunk>> splitted = new ArrayList<>();
		int currentBatch = 0;
		for (int i = 0; i < chunks.size(); i++) {
			if (breaks.contains(i)) currentBatch++;
			if (splitted.size() <= currentBatch) {
				splitted.add(new ArrayList<>());
			}
			List<Chunk> chunkList = splitted.get(currentBatch);
			chunkList.add(chunks.get(i));
		}
		
		splitted.forEach(batch -> {
			ServerMapChunkBulkPacket packet = new ServerMapChunkBulkPacket(true, batch);
			worldPackets.add(new PacketContainer(packet));			
		});
		
		for (BlockMetadata metadata : world.getMetadata()) {
			worldPackets.add(new PacketContainer(metadata.toPacket()));
		}
		
		
		this.worldPackets = worldPackets;
	}
	
	public void send(PlayerImpl player) {
		for (PacketContainer container : worldPackets) {
			player.sendPacket(container);
		}
	}
	
}