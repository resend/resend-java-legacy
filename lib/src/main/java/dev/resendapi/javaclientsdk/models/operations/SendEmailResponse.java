package dev.resendapi.javaclientsdk.models.operations;



public class SendEmailResponse {
    public String contentType;
    public SendEmailResponse withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }
    public dev.resendapi.javaclientsdk.models.shared.SendEmailResponse sendEmailResponse;
    public SendEmailResponse withSendEmailResponse(dev.resendapi.javaclientsdk.models.shared.SendEmailResponse sendEmailResponse) {
        this.sendEmailResponse = sendEmailResponse;
        return this;
    }
    public Long statusCode;
    public SendEmailResponse withStatusCode(Long statusCode) {
        this.statusCode = statusCode;
        return this;
    }
}
