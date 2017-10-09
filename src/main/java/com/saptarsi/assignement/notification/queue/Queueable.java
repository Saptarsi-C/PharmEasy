package com.saptarsi.assignement.notification.queue;

import java.io.IOException;
import java.io.Serializable;

public interface Queueable {

    public enum MessagePriority {
        NONURGENT_NONIMPORTANT, NONURGENT_IMPORTANT, URGENT_NONIMPORTANT, URGENT_IMPORTANT
    }

    Serializable getBody() throws IOException;

    String getUniqueId();// Deduplication id for sqs

    String getMessageGroup();

    MessagePriority getMessagePriority();

    boolean isValid();
    
    String getQueueType();

}
