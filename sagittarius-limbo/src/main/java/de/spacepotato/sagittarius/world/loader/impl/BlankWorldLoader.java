package de.spacepotato.sagittarius.world.loader.impl;

import java.io.File;

import de.spacepotato.sagittarius.world.WorldImpl;
import de.spacepotato.sagittarius.world.loader.WorldLoader;

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
