/*
 * The MIT License
 * Copyright © 2023 Plus Five Five, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.resend.sdk.models.shared;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Email {
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("bcc")
    public String bcc;
    public Email withBcc(String bcc) {
        this.bcc = bcc;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("cc")
    public String cc;
    public Email withCc(String cc) {
        this.cc = cc;
        return this;
    }
    
    @JsonProperty("from")
    public String from;
    public Email withFrom(String from) {
        this.from = from;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("html")
    public String html;
    public Email withHtml(String html) {
        this.html = html;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("reply_to")
    public String replyTo;
    public Email withReplyTo(String replyTo) {
        this.replyTo = replyTo;
        return this;
    }
    
    @JsonProperty("subject")
    public String subject;
    public Email withSubject(String subject) {
        this.subject = subject;
        return this;
    }
    
    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("text")
    public String text;
    public Email withText(String text) {
        this.text = text;
        return this;
    }
    
    @JsonProperty("to")
    public String to;
    public Email withTo(String to) {
        this.to = to;
        return this;
    }
    
}
