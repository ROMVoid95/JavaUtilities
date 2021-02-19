package net.rom.utility.io.utils;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.Predicate;

public class FileVistor extends SimpleFileVisitor<Path> {
	protected Path baseDirectory;

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
		return null;
	}

	public static class Copy extends FileVistor {
		private final Path targetDirectory;
		private final CopyOption[] options;

		Copy(Path sourceDirectory, Path targetDirectory, CopyOption[] options) {
			this.baseDirectory = sourceDirectory;
			this.targetDirectory = targetDirectory;
			this.options = options;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
			Files.createDirectories(targetDirectory.resolve(baseDirectory.relativize(directory)));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
			Files.copy(file, targetDirectory.resolve(baseDirectory.relativize(file)), options);
			return FileVisitResult.CONTINUE;
		}

	}

	public static class Delete extends FileVistor {
		private final Predicate<Path> filter;

		Delete(Path baseDirectory, Predicate<Path> filter) {
			this.baseDirectory = baseDirectory;
			this.filter = filter;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
			if (filter.test(file)) {
				Files.delete(file);
			}

			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path directory, IOException ex) throws IOException {
			if (ex != null) {
				return FileVisitResult.TERMINATE;
			}

			if (!directory.equals(baseDirectory) && filter.test(directory)) {
				Files.walkFileTree(directory, new Delete(directory, path -> true));
				Files.delete(directory);
			}

			return FileVisitResult.CONTINUE;
		}
	}
}
