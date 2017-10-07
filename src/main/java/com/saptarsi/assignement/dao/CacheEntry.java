package com.saptarsi.assignement.dao;

public class CacheEntry {
    
	private String name;
    private Class  type;

    public CacheEntry(String name, Class type) {
        this.name = name;
        this.type = type;
    }

    public String name() {
        return name;
    }

    public Class type() {
        return type;
    }

    public String all(){
        return this.name().concat("_ALL");
    }
    
    
}
