package net.rom.utility.data;

public class NewFileShutdown extends RuntimeException {

	private static final long serialVersionUID = -6049529263587200927L;
	
	public NewFileShutdown(String msg) {
		super(msg);
	}
}
