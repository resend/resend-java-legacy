package com.resend.sdk.models.operations;

import com.resend.sdk.utils.SpeakeasyMetadata;

public class SendEmailRequest {
    @SpeakeasyMetadata("request:mediaType=application/json")
    public com.resend.sdk.models.shared.Email request;
    public SendEmailRequest withRequest(com.resend.sdk.models.shared.Email request) {
        this.request = request;
        return this;
    }
    
}
