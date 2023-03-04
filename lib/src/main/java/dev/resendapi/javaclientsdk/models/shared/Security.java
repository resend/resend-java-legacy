package dev.resendapi.javaclientsdk.models.shared;

import dev.resendapi.javaclientsdk.utils.SpeakeasyMetadata;

public class Security {
    @SpeakeasyMetadata("security:scheme=true,type=http,subtype=bearer,name=Authorization")
    public String bearerAuth;
    public Security withBearerAuth(String bearerAuth) {
        this.bearerAuth = bearerAuth;
        return this;
    }
    
}
