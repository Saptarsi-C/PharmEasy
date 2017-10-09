package com.saptarsi.assignement.notification.queue;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saptarsi.assignement.AssignmentConstant;
import com.saptarsi.assignement.notification.queue.Queueable.MessagePriority;

@Component
public class SQSConfiguration {
    private final static Logger log = LoggerFactory.getLogger(SQSConfiguration.class);

    @Value("#{${communication.sqs.standard}}")
    Map<String, String> standardQueues;

    @Value("#{${communication.sqs.fifo}}")
    Map<String, String> fifoQueues;

    public String getQueueName(Queueable queueable) {
        MessagePriority msgPriority = queueable.getMessagePriority();
        String priority = AssignmentConstant.DEFAULT_QUEUE_PRIORITY;
        if (null != msgPriority) {
            priority = msgPriority.toString();
        }
        log.info("Priority : " + priority);
        String messageGroup = queueable.getMessageGroup();
        log.info("MessageGroup : "  + messageGroup);
        String key;
        if (null != messageGroup)
            key = messageGroup + "-" + priority;
        else
            key = AssignmentConstant.DEFAULT_MESSAGE_GROUP + "-" + AssignmentConstant.DEFAULT_QUEUE_PRIORITY;
        String queueName;
        if (queueable.getQueueType().equals(AssignmentConstant.FIFO_QUEUE_TYPE)) {
            queueName = fifoQueues.get(key);
            if (null == queueName) {
                queueName = fifoQueues
                        .get(AssignmentConstant.DEFAULT_MESSAGE_GROUP + "_" + AssignmentConstant.DEFAULT_QUEUE_PRIORITY);
            }
        } else {
            queueName = standardQueues.get(key);
            if (null == queueName) {
                queueName = standardQueues
                        .get(AssignmentConstant.DEFAULT_MESSAGE_GROUP + "_" + AssignmentConstant.DEFAULT_QUEUE_PRIORITY);
            }
        }
        log.info("QueueName : " + queueName);
        return queueName;
    }
}
