package com.saptarsi.assignement.notification;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.saptarsi.assignement.notification.queue.SQSProducer;

@Service
public class SQSEmailSender implements EmailSender {

	@Autowired
	SQSProducer sqsProducer;
	
	@Override
	public CompletableFuture<Boolean> send(Email email) {
		// valid check before sending
		if(!email.isValid()){
			return null;
		}

		SendMessageRequest sendMessageRequest = null;
		try {
			sendMessageRequest = sqsProducer.createMessage(email);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return CompletableFuture.supplyAsync(sqsProducer.pushMessage(sendMessageRequest));
	}

}
