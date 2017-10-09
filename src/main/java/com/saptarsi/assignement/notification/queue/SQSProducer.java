package com.saptarsi.assignement.notification.queue;

import java.io.IOException;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.saptarsi.assignement.AssignmentConstant;

@Service
public class SQSProducer {

    @Autowired
    private AmazonSQS amazonSqsClient;

    @Autowired
    private SQSConfiguration sqsConfiguration;

    private final static Logger log = LoggerFactory.getLogger(SQSProducer.class);

    public SendMessageRequest createMessage(Queueable queueable) throws IOException {

        SendMessageRequest message = new SendMessageRequest();
        if (!queueable.isValid()) {
            return null;
        }
        message.setMessageBody(queueable.getBody().toString());
        message.setQueueUrl(sqsConfiguration.getQueueName(queueable));
        if (queueable.getQueueType().equals(AssignmentConstant.FIFO_QUEUE_TYPE)) {
            message.setMessageDeduplicationId(queueable.getUniqueId());
            message.setMessageGroupId(queueable.getMessageGroup());
        }
        log.debug("Message created : " + message.toString());
        return message;
    }

    public Supplier<Boolean> pushMessage(SendMessageRequest sendMessageRequest) {

        log.debug("------Started sending data to SQS---------------");
        try {
            SendMessageResult result;
            if (sendMessageRequest == null) {
                throw new QueueingException("Sending data to queue failed : No Message Body Available ");
            } else {
                result = amazonSqsClient.sendMessage(sendMessageRequest);
            }
            log.debug(result.toString());
            return new Supplier<Boolean>() {

                @Override
                public Boolean get() {
                    // TODO Auto-generated method stub
                    log.debug(result.toString());
                    return true;
                }
            };
        } catch (UnsupportedOperationException | AmazonSQSException ex) {
            throw new QueueingException("Sending data to queue failed : " + ex.getMessage());
        } catch (Exception e) {
            // TODO please remove this afterward
            throw new QueueingException("Sending data to queue failed : " + e.getMessage());
        }
    }
}
