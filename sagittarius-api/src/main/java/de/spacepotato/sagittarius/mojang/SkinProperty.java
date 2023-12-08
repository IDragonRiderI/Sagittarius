package de.spacepotato.sagittarius.mojang;

public class SkinProperty {

	private final String name;
	private final String value;
	private final String signature;
	
	public SkinProperty(String name, String value, String signature) {
		this.name = name;
		this.value = value;
		this.signature = signature;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public String getValue() {
		return value;
	}
	
}
