/* 
 * Code generated by Speakeasy (https://speakeasyapi.dev). DO NOT EDIT.
 */

package com.resend.sdk.models.shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.resend.sdk.utils.DateTimeDeserializer;
import com.resend.sdk.utils.DateTimeSerializer;
import java.time.OffsetDateTime;

/**
 * SendEmailResponse - OK
 */
public class SendEmailResponse {
    /**
     * The date and time the email was sent.
     */
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty("created_at")public OffsetDateTime createdAt;
    public SendEmailResponse withCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    /**
     * The sender email address.
     */
    @JsonProperty("from")public String from;
    public SendEmailResponse withFrom(String from) {
        this.from = from;
        return this;
    }
    
    /**
     * The unique identifier of the sent email.
     */
    @JsonProperty("id")public String id;
    public SendEmailResponse withId(String id) {
        this.id = id;
        return this;
    }
    
    /**
     * The recipient email address.
     */
    @JsonProperty("to")public String to;
    public SendEmailResponse withTo(String to) {
        this.to = to;
        return this;
    }
    
}
