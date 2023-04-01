<!-- Start SDK Example Usage -->
```java
package hello.world;

import com.resend.sdk.Resend;
import com.resend.sdk.models.shared.Security;
import com.resend.sdk.models.operations.SendEmailResponse;
import com.resend.sdk.models.shared.Email;

public class Application {
    public static void main(String[] args) {
        try {
            Resend sdk = Resend.builder()
                .setSecurity(new Security() {{
                    bearerAuth = "Bearer YOUR_BEARER_TOKEN_HERE";
                }})
                .build();

            com.resend.sdk.models.shared.Email req = new Email() {{
                bcc = "corrupti";
                cc = "provident";
                from = "distinctio";
                html = "quibusdam";
                replyTo = "unde";
                subject = "nulla";
                text = "corrupti";
                to = "illum";
            }}            

            SendEmailResponse res = sdk.email.sendEmail(req);

            if (res.sendEmailResponse.isPresent()) {
                // handle response
            }
        } catch (Exception e) {
            // handle exception
        }
```
<!-- End SDK Example Usage -->