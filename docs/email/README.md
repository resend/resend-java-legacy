# email

## Overview

Email operations

### Available Operations

* [sendEmail](#sendemail) - Send an email

## sendEmail

Send an email

### Example Usage

```java
package hello.world;

import com.resend.sdk.Resend;
import com.resend.sdk.models.operations.SendEmailResponse;
import com.resend.sdk.models.shared.Email;
import com.resend.sdk.models.shared.Security;

public class Application {
    public static void main(String[] args) {
        try {
            Resend sdk = Resend.builder()
                .setSecurity(new Security("error") {{
                    bearerAuth = "Bearer YOUR_BEARER_TOKEN_HERE";
                }})
                .build();

            com.resend.sdk.models.shared.Email req = new Email("deserunt", "suscipit", "iure") {{
                bcc = "magnam";
                cc = "debitis";
                html = "ipsa";
                replyTo = "delectus";
                text = "tempora";
            }};            

            SendEmailResponse res = sdk.email.sendEmail(req);

            if (res.sendEmailResponse != null) {
                // handle response
            }
        } catch (Exception e) {
            // handle exception
        }
    }
}
```
