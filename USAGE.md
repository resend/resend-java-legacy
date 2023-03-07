<!-- Start SDK Example Usage -->
```java
package hello.world;

import com.resend.sdk.Resend;
import com.resend.sdk.models.shared.Security;

public class Application {
    public static void main(String[] args) {
        try {
            Resend.Builder builder = Resend.builder();

            builder.setSecurity(
                new Security() {{
                    bearerAuth = "Bearer YOUR_BEARER_TOKEN_HERE";
                }}
            );

            Resend sdk = builder.build();

            SendEmailRequest req = new SendEmailRequest() {{
                request = new Email() {{
                    bcc = "unde";
                    cc = "deserunt";
                    from = "porro";
                    html = "nulla";
                    replyTo = "id";
                    subject = "vero";
                    text = "perspiciatis";
                    to = "nulla";
                }};
            }};

            SendEmailResponse res = sdk.email.sendEmail(req);

            if (res.sendEmailResponse.isPresent()) {
                // handle response
            }
        } catch (Exception e) {
            // handle exception
        }
```
<!-- End SDK Example Usage -->