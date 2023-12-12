package de.spacepotato.sagittarius.world.loader;

import java.io.File;

import de.spacepotato.sagittarius.world.WorldImpl;

public class BlankWorldLoader implements WorldLoader {

	@Override
	public WorldImpl loadWorld(File file) {
		return new WorldImpl();
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public WorldImpl loadDefaultWorld() {
		return new WorldImpl();
	}

}
