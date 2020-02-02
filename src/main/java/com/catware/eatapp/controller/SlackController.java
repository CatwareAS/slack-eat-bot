package com.catware.eatapp.controller;

import com.catware.eatapp.model.Attachment;
import com.catware.eatapp.model.TestResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/slack/slash")
public class SlackController {

    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public TestResponse onReceiveSlashCommand(@RequestParam("team_id") String teamId,
                                              @RequestParam("team_domain") String teamDomain,
                                              @RequestParam("channel_id") String channelId,
                                              @RequestParam("channel_name") String channelName,
                                              @RequestParam("user_id") String userId,
                                              @RequestParam("user_name") String userName,
                                              @RequestParam("command") String command,
                                              @RequestParam("text") String text,
                                              @RequestParam("response_url") String responseUrl) {
        TestResponse response = new TestResponse();
        response.setText("This is the response text: " + channelId + " " + channelName + " " + userName);
        response.setResponseType("in_channel");
        Attachment attachment = new Attachment();
        attachment.setText("This is the attachment text");
        attachment.setColor("#0000ff");
        response.getAttachments().add(attachment);
        return response;
    }


}