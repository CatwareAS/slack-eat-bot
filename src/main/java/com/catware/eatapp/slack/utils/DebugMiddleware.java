package com.catware.eatapp.slack.utils;

import com.catware.eatapp.slack.utils.model.DebugResponseBody;
import com.slack.api.bolt.middleware.Middleware;
import com.slack.api.bolt.middleware.MiddlewareChain;
import com.slack.api.bolt.request.Request;
import com.slack.api.bolt.response.Response;
import com.slack.api.bolt.util.JsonOps;
import org.springframework.stereotype.Component;

import java.util.Collections;

import static java.util.stream.Collectors.joining;

@Component
public class DebugMiddleware implements Middleware {
    @Override
    public Response apply(Request req, Response res, MiddlewareChain chain) throws Exception {
        Response resp = chain.next(req);
        if (resp.getStatusCode() != 200) {
            resp.getHeaders().put("content-type", Collections.singletonList(resp.getContentType()));
            // dump all the headers as a single string
            String headers = resp.getHeaders().entrySet().stream()
                    .map(e -> e.getKey() + ": " + e.getValue() + "\n").collect(joining());

            // set an ephemeral message with useful information
            DebugResponseBody body = new DebugResponseBody();
            body.setResponseType("ephemeral");
            body.setText(
                    ":warning: *[DEBUG MODE] Something is technically wrong* :warning:\n" +
                            "Below is a response the Slack app was going to send...\n" +
                            "*Status Code*: " + resp.getStatusCode() + "\n" +
                            "*Headers*: ```" + headers + "```" + "\n" +
                            "*Body*: ```" + resp.getBody() + "```"
            );
            resp.setBody(JsonOps.toJsonString(body));

            resp.setStatusCode(200);
        }
        return resp;
    }

}
