<!-- Start SDK Example Usage -->
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
                .setSecurity(new Security("corrupti") {{
                    bearerAuth = "YOUR_BEARER_TOKEN_HERE";
                }})
                .build();

            com.resend.sdk.models.shared.Email req = new Email("provident", "distinctio", "quibusdam") {{
                bcc = "unde";
                cc = "nulla";
                html = "corrupti";
                replyTo = "illum";
                text = "vel";
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
<!-- End SDK Example Usage -->