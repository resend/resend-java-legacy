<div align="center">
    <picture>
        <source srcset="https://user-images.githubusercontent.com/68016351/221072893-61d9e99a-ed2a-4f58-b167-0ff2cbea0614.svg" media="(prefers-color-scheme: dark)" width="500">
        <img src="https://user-images.githubusercontent.com/68016351/221070388-c5faf78a-d3b7-440b-a300-c2e7b635279b.svg" width="500">
    </picture>
   <p>Resend is the email platform for developers.</p>
   <a href="https://resend.com/docs/api-reference/concepts"><img src="https://img.shields.io/static/v1?label=Docs&message=API Ref&color=000000&style=for-the-badge" /></a>
   <a href="https://github.com/resendlabs/resend-java/actions"><img src="https://img.shields.io/github/actions/workflow/status/resendlabs/resend-java/speakeasy_sdk_generation.yaml?style=for-the-badge" /></a>
  <a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" /></a>
  <a href="https://github.com/resendlabs/resend-java/releases"><img src="https://img.shields.io/github/v/release/resendlabs/resend-java?sort=semver&style=for-the-badge" /></a>
</div>

<!-- Start SDK Installation -->
## SDK Installation

### Gradle

```groovy
implementation 'com.resend.api:sdk:1.6.0'
```

### Maven

```xml
<dependency>
    <groupId>com.resend.api</groupId>
    <artifactId>sdk</artifactId>
    <version>1.6.0</version>
</dependency>
```

<!-- End SDK Installation -->

## Authentication

To authenticate you need to add an Authorization header with the contents of the header being Bearer re_123456789 where re_123456789 is your API Key. First, you need to get an API key, which is available in the [Resend Dashboard](https://resend.com/login).

```bash
Authorization: Bearer re_123
```

## SDK Example Usage
```java
package hello.world;

import com.resend.sdk.Resend;
import com.resend.sdk.models.operations.SendEmailRequest;
import com.resend.sdk.models.operations.SendEmailResponse;
import com.resend.sdk.models.shared.Email;
import com.resend.sdk.models.shared.Security;

public class Application {
    public static void main(String[] args) {
        try {
            Resend.Builder builder = Resend.builder();

			builder.setSecurity(
                new Security().withBearerAuth("Bearer YOUR_BEARER_TOKEN_HERE"));

            Resend sdk = builder.build();

			Email email = new Email().
				withFrom("onboarding@resend.dev").
				withHtml("Congrats on sending your <strong>first email</strong> from Java SDK!").
				withSubject("Hello World").
				withTo("amy@acme.com");

			SendEmailRequest req = new SendEmailRequest();

			req.withRequest(email);

            SendEmailResponse res = sdk.email.sendEmail(req);

            if (res.statusCode == 200) {
                // handle response
            }
        } catch (Exception e) {
            // handle exception
        }
```

<!-- Start SDK Available Operations -->
## SDK Available Operations


### email

* `sendEmail` - Send an email
<!-- End SDK Available Operations -->

## Contributions

While we value open-source contributions to this SDK, this library is generated programmatically. Feel free to open a PR or a Github issue as a proof of concept and we'll do our best to include it in a future release !
