package com.rayzr522.bitzapi.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
	
	public abstract String name();
	
	public abstract String usage();
	
	public abstract String desc();
	
	public abstract String pattern();
	
	public abstract String perm();
	
	
	
}
