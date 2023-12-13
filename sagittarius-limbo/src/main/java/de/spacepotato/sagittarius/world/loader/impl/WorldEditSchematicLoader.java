package de.spacepotato.sagittarius.world.loader.impl;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.GZIPInputStream;

import de.spacepotato.sagittarius.Sagittarius;
import de.spacepotato.sagittarius.nbt.NBT;
import de.spacepotato.sagittarius.nbt.NBTInputStream;
import de.spacepotato.sagittarius.nbt.NBTTagByteArray;
import de.spacepotato.sagittarius.nbt.NBTTagCompound;
import de.spacepotato.sagittarius.nbt.NBTTagInt;
import de.spacepotato.sagittarius.nbt.NBTTagList;
import de.spacepotato.sagittarius.nbt.NBTTagShort;
import de.spacepotato.sagittarius.nbt.NBTTagString;
import de.spacepotato.sagittarius.world.BlockPosition;
import de.spacepotato.sagittarius.world.Location;
import de.spacepotato.sagittarius.world.WorldImpl;
import de.spacepotato.sagittarius.world.loader.WorldLoader;
import de.spacepotato.sagittarius.world.metadata.SignMetadata;
import de.spacepotato.sagittarius.world.metadata.SkullMetadata;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WorldEditSchematicLoader implements WorldLoader {

	@Override
	public WorldImpl loadWorld(File file) {
		WorldImpl world = new WorldImpl();
		try (NBTInputStream in = new NBTInputStream(new GZIPInputStream(Files.newInputStream(file.toPath())))) {
			NBTTagCompound compound = (NBTTagCompound) in.readTag();

			int lenX = compound.getTag("Width", NBTTagShort.class).getValue();
			int lenY = compound.getTag("Height", NBTTagShort.class).getValue();
			int lenZ = compound.getTag("Length", NBTTagShort.class).getValue();

			int offsetX = compound.getTag("WEOffsetX", NBTTagInt.class).getValue();
			int offsetY = compound.getTag("WEOffsetY", NBTTagInt.class).getValue();
			int offsetZ = compound.getTag("WEOffsetZ", NBTTagInt.class).getValue();
			
			Location spawnPoint = Sagittarius.getInstance().getConfig().getSpawnPoint();
			int worldOffsetX = (int) Math.floor(spawnPoint.getX());
			int worldOffsetY = (int) Math.floor(spawnPoint.getY());
			int worldOffsetZ = (int) Math.floor(spawnPoint.getZ());

			byte[] blocks = compound.getTag("Blocks", NBTTagByteArray.class).getValue();
			byte[] blockData = compound.getTag("Data", NBTTagByteArray.class).getValue();
			byte[] addBlocks = compound.getValue().containsKey("AddBlocks") ? compound.getTag("AddBlocks", NBTTagByteArray.class).getValue() : new byte[0];

			for (int x = 0; x < lenX; x++) {
				for (int y = 0; y < lenY; y++) {
					for (int z = 0; z < lenZ; z++) {
						int index = (y * lenZ + z) * lenX + x;
						byte data = blockData[index];
						short blockId = getBlockId(index, addBlocks, blocks);

						if (blockId == 0) {
                            continue;
                        }
						world.setTypeIdAndData(x + offsetX + worldOffsetX, y + offsetY + worldOffsetY, z + offsetZ + worldOffsetZ, blockId, data);
					}
				}
			}

			if (compound.getValue().containsKey("TileEntities")) {
				List<NBT> tileEntities = compound.getTag("TileEntities", NBTTagList.class).getValue();
				for (NBT nbt : tileEntities) {
					NBTTagCompound tileEntity = (NBTTagCompound) nbt;
					String id = tileEntity.getTag("id", NBTTagString.class).getValue();
					
					if (id.equalsIgnoreCase("Sign")) {
						String line1 = tileEntity.getTag("Text1", NBTTagString.class).getValue();
						String line2 = tileEntity.getTag("Text2", NBTTagString.class).getValue();
						String line3 = tileEntity.getTag("Text3", NBTTagString.class).getValue();
						String line4 = tileEntity.getTag("Text4", NBTTagString.class).getValue();
						int x = tileEntity.getTag("x", NBTTagInt.class).getValue();
						int y = tileEntity.getTag("y", NBTTagInt.class).getValue();
						int z = tileEntity.getTag("z", NBTTagInt.class).getValue();
						SignMetadata metaData = new SignMetadata(new BlockPosition(x + offsetX + worldOffsetX, y + offsetY + worldOffsetY, z + offsetZ + worldOffsetZ), line1, line2, line3, line4);
						world.getMetadata().add(metaData);
					} else if(id.equalsIgnoreCase("Skull")) {
						int x = tileEntity.getTag("x", NBTTagInt.class).getValue();
						int y = tileEntity.getTag("y", NBTTagInt.class).getValue();
						int z = tileEntity.getTag("z", NBTTagInt.class).getValue();
						tileEntity.getTag("x", NBTTagInt.class).setValue(x + offsetX + worldOffsetX);
						tileEntity.getTag("y", NBTTagInt.class).setValue(y + offsetY + worldOffsetY);
						tileEntity.getTag("z", NBTTagInt.class).setValue(z + offsetZ + worldOffsetZ);
						SkullMetadata metadata = new SkullMetadata(new BlockPosition(x + offsetX + worldOffsetX, y + offsetY + worldOffsetY, z + offsetZ + worldOffsetZ), tileEntity);
						world.getMetadata().add(metadata);
					}
				}
			}
		} catch (Exception ex) {
			log.error("Unable to load schematic! ", ex);
		}

		return world;
	}

	private short getBlockId(int index, byte[] addBlocks, byte[] blocks) {
		short blockId;

		// Check if there is a nibble for that block
		if (index / 2 >= addBlocks.length) {
			// Does not seem like that - no further block data added
			blockId = (short) (blocks[index] & 0xFF);
		} else {
			int additionalData;
			if (index % 2 == 0) {
				additionalData = ((addBlocks[index / 2] & 0x0F) << 8);
			} else {
				additionalData = ((addBlocks[index / 2] & 0xF0) << 4);
			}

			blockId = (short) (additionalData + (blocks[index] & 0xFF));
		}
		return blockId;
	}

	@Override
	public boolean isSupported() {
		File file = new File("world.schematic");
		return file.exists();
	}

	@Override
	public WorldImpl loadDefaultWorld() {
		File file = new File("world.schematic");
		return loadWorld(file);
	}

}
