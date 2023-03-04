```java
package hello.world;

import dev.resendapi.javaclientsdk.Resend;
import dev.resendapi.javaclientsdk.models.shared.Security;

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
                    from = "hello@resend.com";
                    to = "thefuture@yourcompany.com";
                    subject = "Welcome to Resend!";
                    text = "Hello, World!";
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