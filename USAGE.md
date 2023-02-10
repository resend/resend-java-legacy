<!-- Start SDK Example Usage -->
```java
package hello.world;

import dev.resendapi.javaclientsdk.SDK;
import dev.resendapi.javaclientsdk.models.shared.Security;

public class Application {
    public static void main(String[] args) {
        try {
            SDK.Builder builder = SDK.builder();

            builder.setSecurity(
                new Security() {{
                    bearerAuth = new SchemeBearerAuth() {{
                        authorization = "Bearer YOUR_BEARER_TOKEN_HERE";
                    }};
                }}
            );

            SDK sdk = builder.build();

            SendEmailRequest req = new SendEmailRequest() {{
                request = new Email() {{
                    bcc = "unde";
                    cc = "deserunt";
                    from = "porro";
                    html = "nulla";
                    react = "id";
                    replyTo = "vero";
                    subject = "perspiciatis";
                    text = "nulla";
                    to = "nihil";
                }};
            }};

            SendEmailResponse res = sdk.emails.sendEmail(req);

            if (res.sendEmailResponse.isPresent()) {
                // handle response
            }
        } catch (Exception e) {
            // handle exception
        }
```
<!-- End SDK Example Usage -->