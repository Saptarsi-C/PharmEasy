package com.saptarsi.assignement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class GsonConfig {
	
	@Bean
	public Gson umGson(){
		GsonBuilder gb = new GsonBuilder();
		GsonExclusionStrategy exclusionStrategy = new GsonExclusionStrategy();
		gb.addDeserializationExclusionStrategy(exclusionStrategy);
		gb.disableInnerClassSerialization();
		gb.excludeFieldsWithoutExposeAnnotation();
		return gb.create();
	}
}
