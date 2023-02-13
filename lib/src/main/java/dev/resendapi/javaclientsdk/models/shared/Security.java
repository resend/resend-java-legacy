package dev.resendapi.javaclientsdk.models.shared;

import dev.resendapi.javaclientsdk.utils.SpeakeasyMetadata;

public class Security {
    @SpeakeasyMetadata("security:scheme=true,type=http,subtype=bearer")
    public SchemeBearerAuth bearerAuth;
    public Security withBearerAuth(SchemeBearerAuth bearerAuth) {
        this.bearerAuth = bearerAuth;
        return this;
    }
}
