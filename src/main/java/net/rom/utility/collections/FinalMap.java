package net.rom.utility.collections;

import com.google.common.collect.ImmutableMap;

@SuppressWarnings("rawtypes")
public class FinalMap {
	
	public ImmutableMap finalMap;
	
	public FinalMap(Object k, Object v) {
		finalMap = ImmutableMap.of(k, v);
	}
	
	public static FinalMap of(Object k, Object v) {
		return new FinalMap(k, v);
	}
	
	public ImmutableMap get() {
		return finalMap;
	}
}
