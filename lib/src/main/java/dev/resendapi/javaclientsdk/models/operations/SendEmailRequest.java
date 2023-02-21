package dev.resendapi.javaclientsdk.models.operations;

import dev.resendapi.javaclientsdk.utils.SpeakeasyMetadata;

public class SendEmailRequest {
    @SpeakeasyMetadata("request:mediaType=application/json")
    public dev.resendapi.javaclientsdk.models.shared.Email request;
    public SendEmailRequest withRequest(dev.resendapi.javaclientsdk.models.shared.Email request) {
        this.request = request;
        return this;
    }
    
}
