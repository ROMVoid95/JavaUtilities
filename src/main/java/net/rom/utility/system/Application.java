package net.rom.utility.system;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.rom.utility.annotations.Incubating;

@Incubating
public class Application {

	/***
	 * <p>
	 * Restart the java application by registering a shutdown hook at the very end
	 * of the life cycle. Tested under JRE 8 Windows 10.
	 * 
	 * <p style="color:red;">
	 * <b>This method should be used with caution and testing is needed to be done
	 * before relying on it's functionality!. JVM specific security managers, and
	 * overall behaviour might impact this method</b>
	 * </p>
	 * 
	 * 
	 * <p>
	 * This method "injects" it's own runnable at the very end of the lifetime of
	 * the program after all shutdown hooks have been executed by accessing non
	 * private methods via reflection. This might break if the implementation of
	 * java will change therefore again be cautious!
	 * 
	 * Guard yourself against constant restarting due to uninitialized conditions.
	 * 
	 * @since 1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void restartApp() {

		Runnable restartApp = () -> {
			// java binary
			String java = System.getProperty("java.home") + "/bin/java";
			// vm arguments
			List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
			StringBuffer vmArgsOneLine = new StringBuffer();
			for (String arg : vmArguments) {
				// if it's the agent argument : we ignore it otherwise the
				// address of the old application and the new one will be in conflict
				if (!arg.contains("-agentlib")) {
					vmArgsOneLine.append(arg);
					vmArgsOneLine.append(" ");
				}
			}
			// init the command to execute, add the vm args
			final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);
			// program main and program arguments (be careful a sun property. might not be
			// supported by all JVM)
			String[] mainCommand = System.getProperty("sun.java.command").split(" ");
			// program main is a jar
			if (mainCommand[0].endsWith(".jar")) {
				// if it's a jar, add -jar mainJar
				cmd.append("-jar " + new File(mainCommand[0]).getPath());
			} else {
				// else it's a .class, add the classpath and mainClass
				cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
			}
			// finally add program arguments
			for (int i = 1; i < mainCommand.length; i++) {
				cmd.append(" ");
				cmd.append(mainCommand[i]);
			}
			try {
				Runtime.getRuntime().exec(cmd.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		// Modified heavily from here

		/*
		 * We want all shutdown hooks to finish before re starting our application.
		 * Registering a normal shutdown hook is out of question. Give it the lowest
		 * priority
		 * 
		 * (0) Console restore hook (1) Application hooks (2) DeleteOnExit hook
		 * 
		 * so far I only came across the 3 slots being used. If we register a slot which
		 * is supposed to be used by another java feature we are in trouble so be
		 * careful!.
		 */

		// Register low priority shutdown hook.
		try {
			Class shutdownClass = Class.forName("java.lang.Shutdown");

			// Do other classes
			Method registerShutdownSlot = shutdownClass.getDeclaredMethod("add", Integer.TYPE, Boolean.TYPE,
					Runnable.class);

			// The JVM has 10 shutdown hook priorities Maybe start from 10 and work our way
			// up?
			for (int i = 3; i < 10; i++) {
				try {
					registerShutdownSlot.setAccessible(true);
					registerShutdownSlot.invoke(null, i, false, restartApp);
					break;
				} catch (IllegalStateException e) {
				} finally {
					registerShutdownSlot.setAccessible(false);
				}
			}
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			System.out.println("Error registering shutdown hook " + e.getMessage() + e);
		}
		// Invoke shutdown hooks
		System.exit(0);
	}

	
}
