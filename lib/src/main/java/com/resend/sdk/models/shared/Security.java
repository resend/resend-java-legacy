package com.resend.sdk.models.shared;

import com.resend.sdk.utils.SpeakeasyMetadata;

public class Security {
    @SpeakeasyMetadata("security:scheme=true,type=http,subtype=bearer,name=Authorization")
    public String bearerAuth;
    public Security withBearerAuth(String bearerAuth) {
        this.bearerAuth = bearerAuth;
        return this;
    }
    
}
