package net.rom.utility.io;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipFile;

import com.google.common.base.Preconditions;

import net.rom.utility.collections.FinalMap;

@SuppressWarnings("unchecked")
public class Zip implements AutoCloseable {
	
	private static final FinalMap GEN = FinalMap.of("create", "true");
	private static final FinalMap DO_NOT_MAKE = FinalMap.of("create", "false");
	
	private final FileSystem fileSystem;
	private final Path path;
	

	public Zip(Path path) throws IOException {
		Preconditions.checkNotNull(path, "path should not be null");
		this.path = path;
		final URI uri = URI.create("jar:" + path.toUri());
		fileSystem = FileSystems.newFileSystem(uri, Files.exists(path) ? DO_NOT_MAKE.get() : GEN.get());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() throws IOException {
		fileSystem.close();
	}

	/**
	 * Returns the {@link Path} to this {@link ZipFile}.
	 *
	 * @return the {@link Path} to this {@link ZipFile}.
	 */
	public Path getPath() {
		return path;
	}
	
	/**
	 * Returns this {@link ZipFile}'s {@link FileSystem}.
	 *
	 * @return this {@link ZipFile}'s {@link FileSystem}.
	 */
	public FileSystem getFileSystem() {
		return fileSystem;
	}
	
	/**
	 * Returns a {@link Path} that represents an entry within this {@link ZipFile} that matches
	 * the specified entry path.
	 *
	 * @param entryPath an entry path.
	 * @return a {@link Path} that represents an entry within this {@link ZipFile} that matches
	 * the specified entry path.
	 * @throws java.nio.file.InvalidPathException if the entry path is invalid.
	 */
	public Path getEntry(String entryPath) {
		Preconditions.checkNotNull(entryPath, "entryPath should not be null");
		return fileSystem.getPath(entryPath).toAbsolutePath().normalize();
	}

	/**
	 * Returns a {@link Path} that represents this {@link ZipFile}'s root.
	 * This is equivalent to calling {@link #getEntry(String)} with {@code "/"} as the entry path.
	 *
	 * @return a {@link Path} that represents this {@link ZipFile}'s root.
	 */
	public Path getRoot() {
		return getEntry("/");
	}

	/**
	 * Creates a {@link ZipFile} instance that represents a zip file at the specified {@link Path}.
	 * If a file already exists at the specified {@link Path}, it is deleted first.
	 *
	 * @param path a {@link Path}.
	 * @return the created {@link ZipFile}.
	 * @throws IOException if an I/O error occurs.
	 * @see ZipFile#ZipFile(Path)
	 */
	public static Zip createNew(Path path) throws IOException {
		Preconditions.checkNotNull(path, "path should not be null");
		Files.deleteIfExists(path);
		return new Zip(path);
	}
}
