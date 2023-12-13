package de.spacepotato.sagittarius.world.loader;

import de.spacepotato.sagittarius.world.WorldImpl;

import java.io.File;

public interface WorldLoader {

	/**
	 * Returns whether this loader has found a map that can be loaded.
	 * @return true if there is a world file present that may be loaded by this loader.
	 */
	boolean isSupported();

	/**
	 * Loads the default world.
	 * @return An instance of the default world.
	 */
	WorldImpl loadDefaultWorld();
	
	/**
	 * Loads a specific world from a file.
	 * @param file The file in which the world is present.
	 * @return The loaded world.
	 */
	WorldImpl loadWorld(File file);

	
}
