package com.saptarsi.assignement.notification;
/**
 * @author saptarsichaurashy
 *
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.saptarsi.assignement.AssignmentConstant;
import com.saptarsi.assignement.notification.queue.Queueable;

import lombok.Data;

// Added lombok annotation to remove redundant function defns. Scroll down and clear those if not required
@Data
public class Email implements Serializable, Queueable {

    private static final long serialVersionUID = 1552526832234055996L;
    private static final Gson gson = new Gson();

    @Expose
    private String uniqueId;
    Queueable.MessagePriority messagePriority; // These are for future use
                                               // cases. Right? Are we using
    @Expose                                           // them currently?
    private String messageGroup; // These are for future use cases. Right? Are we using
    
    @Expose// them currently?
    private String to;
    
    @Expose
    private String from;
    @Expose
    private String subject;
    @Expose
    private String templateId;

    /*
     * Need content param in order to send email content in the producer request
     * itself. This is consumer implementation agnostic. Either templateId or
     * content should be present.
     */
    @Expose
    String content;

    @Expose
    Map<String, String> params;

    public Email(String messageGroup, String to, String from, String subject, String templateId,
            Map<String, String> params) {

        super();
        this.uniqueId = null; // Deduplication id not required for message
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.templateId = templateId;
        this.params = params;
        this.messageGroup = messageGroup;
    }

    public boolean isValid() {
        // updated valid check
        if ((to == null) || (templateId == null && null == content)
                || (templateId != null && null != content) || (null != content && content == ""))
            return false;
        return true;
    }

    @Override
    public Serializable getBody() throws IOException {
        return gson.toJson(this);
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getMessageGroup() {
        return messageGroup;
    }

    @Override
    public Queueable.MessagePriority getMessagePriority() {
        return messagePriority;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setMessagePriority(Queueable.MessagePriority messagePriority) {
        this.messagePriority = messagePriority;
    }

    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }

    @Override
    public String getQueueType() {
        // TODO Auto-generated method stub
        return AssignmentConstant.STANDARD_QUEUE_TYPE;
    }
} // End of Class
