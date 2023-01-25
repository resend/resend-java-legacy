package dev.resendapi.javaclientsdk;

import dev.resendapi.javaclientsdk.utils.HTTPClient;
import dev.resendapi.javaclientsdk.utils.HTTPRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import dev.resendapi.javaclientsdk.utils.SerializedBody;

public class Emails {
	private HTTPClient _defaultClient;
	private HTTPClient _securityClient;
	private String _serverUrl;
	private String _language;
	private String _sdkVersion;
	private String _genVersion;

	public Emails(HTTPClient defaultClient, HTTPClient securityClient, String serverUrl, String language, String sdkVersion, String genVersion) {
		this._defaultClient = defaultClient;
		this._securityClient = securityClient;
		this._serverUrl = serverUrl;
		this._language = language;
		this._sdkVersion = sdkVersion;
		this._genVersion = genVersion;
	}
	
	
    /**
     * sendEmail - Send an email
    **/
    public dev.resendapi.javaclientsdk.models.operations.SendEmailResponse sendEmail(dev.resendapi.javaclientsdk.models.operations.SendEmailRequest request) throws Exception {
        String baseUrl = this._serverUrl;
        String url = dev.resendapi.javaclientsdk.utils.Utils.generateURL(baseUrl, "/emails");
        
        HTTPRequest req = new HTTPRequest();
        req.setMethod("POST");
        req.setURL(url);
        SerializedBody serializedRequestBody = dev.resendapi.javaclientsdk.utils.Utils.serializeRequestBody(request);
        if (serializedRequestBody == null) {
            throw new Exception("Request body is required");
        }
        req.setBody(serializedRequestBody);
        
        req.addHeader("user-agent", String.format("speakeasy-sdk/%s %s %s", this._language, this._sdkVersion, this._genVersion));
        
        HTTPClient client = this._securityClient;
        
        HttpResponse<byte[]> httpRes = client.send(req);

        String contentType = httpRes.headers().allValues("Content-Type").get(0);

        dev.resendapi.javaclientsdk.models.operations.SendEmailResponse res = new dev.resendapi.javaclientsdk.models.operations.SendEmailResponse() {{
            sendEmailResponse = null;
        }};
        res.statusCode = Long.valueOf(httpRes.statusCode());
        res.contentType = contentType;
        
        if (httpRes.statusCode() == 200) {
            if (dev.resendapi.javaclientsdk.utils.Utils.matchContentType(contentType, "application/json")) {
                ObjectMapper mapper = new ObjectMapper();
                mapper.findAndRegisterModules();
                dev.resendapi.javaclientsdk.models.shared.SendEmailResponse out = mapper.readValue(new String(httpRes.body(), StandardCharsets.UTF_8), dev.resendapi.javaclientsdk.models.shared.SendEmailResponse.class);
                res.sendEmailResponse = out;
            }
        }

        return res;
    }
	
}