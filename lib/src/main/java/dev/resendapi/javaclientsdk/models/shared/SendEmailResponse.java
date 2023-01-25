package dev.resendapi.javaclientsdk.models.shared;

import java.time.OffsetDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.resendapi.javaclientsdk.utils.DateTimeSerializer;
import dev.resendapi.javaclientsdk.utils.DateTimeDeserializer;
public class SendEmailResponse {
    @JsonSerialize(using = DateTimeSerializer.class)
    @JsonDeserialize(using = DateTimeDeserializer.class)
    @JsonProperty("created_at")
    public OffsetDateTime createdAt;
    public SendEmailResponse withCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    @JsonProperty("from")
    public String from;
    public SendEmailResponse withFrom(String from) {
        this.from = from;
        return this;
    }
    @JsonProperty("id")
    public String id;
    public SendEmailResponse withId(String id) {
        this.id = id;
        return this;
    }
    @JsonProperty("to")
    public String to;
    public SendEmailResponse withTo(String to) {
        this.to = to;
        return this;
    }
}