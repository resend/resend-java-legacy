package dev.resendapi.javaclientsdk.models.shared;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Email {
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("bcc")
    public String bcc;
    public Email withBcc(String bcc) {
        this.bcc = bcc;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("cc")
    public String cc;
    public Email withCc(String cc) {
        this.cc = cc;
        return this;
    }
    
    @JsonProperty("from")
    public String from;
    public Email withFrom(String from) {
        this.from = from;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("html")
    public String html;
    public Email withHtml(String html) {
        this.html = html;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("reply_to")
    public String replyTo;
    public Email withReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }
    
    @JsonProperty("subject")
    public String subject;
    public Email withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("text")
    public String text;
    public Email withText(String text) {
        this.text = text;
        return this;
    }
    
    @JsonProperty("to")
    public String to;
    public Email withTo(String to) {
        this.to = to;
        return this;
    }
    
}
