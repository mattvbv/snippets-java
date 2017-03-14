package com.mattvbv.snippets.java.multiline_string_extraction;

public class ValueAndUnit<V> {
	
	private V value;
	private String unit;

	public ValueAndUnit(V value, String unit) {
		super();
		this.value = value;
		this.unit = unit;
	}

	public V getValue() {
		return value;
	}

	public String getUnit() {
		return unit;
	}
	
}
