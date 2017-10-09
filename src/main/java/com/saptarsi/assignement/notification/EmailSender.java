package com.saptarsi.assignement.notification;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface EmailSender {
	CompletableFuture<Boolean> send(Email email);	
	default CompletableFuture<Boolean> send(String msgGroup, String to, String from, String subject, String template, Map<String, String> params){
		return send(new Email(msgGroup, to, from, subject, template, params));
	}	
}
