package com.catware.eatapp.controller;

import com.catware.eatapp.model.Attachment;
import com.catware.eatapp.model.TestResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SlackController {

    private final static String mark = "{\n" +
            "\t\"blocks\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"type\": \"section\",\n" +
            "\t\t\t\"block_id\": \"section567\",\n" +
            "\t\t\t\"text\": {\n" +
            "\t\t\t\t\"type\": \"mrkdwn\",\n" +
            "\t\t\t\t\"text\": \"<https://example.com|Radisson Blu Plaza Hotel, Oslo> \\n :star: :soccer: :soccer::soccer: :star: \\n Doors had too many axe holes, guest in room 237 was far too rowdy, whole place felt stuck in the 2920s.\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"accessory\": {\n" +
            "\t\t\t\t\"type\": \"image\",\n" +
            "\t\t\t\t\"image_url\": \"https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500\",\n" +
            "\t\t\t\t\"alt_text\": \"Haunted hotel image\"\n" +
            "\t\t\t}\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"type\": \"section\",\n" +
            "\t\t\t\"block_id\": \"section789\",\n" +
            "\t\t\t\"fields\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"type\": \"mrkdwn\",\n" +
            "\t\t\t\t\t\"text\": \"*Average Rating*\\n7.7\"\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"type\": \"actions\",\n" +
            "\t\t\t\"elements\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"type\": \"button\",\n" +
            "\t\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\t\"text\": \"Dont reply to review\",\n" +
            "\t\t\t\t\t\t\"emoji\": false\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"type\": \"section\",\n" +
            "\t\t\t\"text\": {\n" +
            "\t\t\t\t\"type\": \"mrkdwn\",\n" +
            "\t\t\t\t\"text\": \"Pick one or more items from the list\"\n" +
            "\t\t\t},\n" +
            "\t\t\t\"accessory\": {\n" +
            "\t\t\t\t\"type\": \"multi_static_select\",\n" +
            "\t\t\t\t\"placeholder\": {\n" +
            "\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\"text\": \"Select items\",\n" +
            "\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t},\n" +
            "\t\t\t\t\"options\": [\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"Chinese\",\n" +
            "\t\t\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"value\": \"value-0\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"Coreean\",\n" +
            "\t\t\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"value\": \"value-1\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\"text\": {\n" +
            "\t\t\t\t\t\t\t\"type\": \"plain_text\",\n" +
            "\t\t\t\t\t\t\t\"text\": \"Vietnamese\",\n" +
            "\t\t\t\t\t\t\t\"emoji\": true\n" +
            "\t\t\t\t\t\t},\n" +
            "\t\t\t\t\t\t\"value\": \"value-2\"\n" +
            "\t\t\t\t\t}\n" +
            "\t\t\t\t]\n" +
            "\t\t\t}\n" +
            "\t\t}\n" +
            "\t]\n" +
            "}";


    @PostMapping(value = "/slack/slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String onReceiveSlashCommand(@RequestParam("team_id") String teamId,
                                        @RequestParam("team_domain") String teamDomain,
                                        @RequestParam("channel_id") String channelId,
                                        @RequestParam("channel_name") String channelName,
                                        @RequestParam("user_id") String userId,
                                        @RequestParam("user_name") String userName,
                                        @RequestParam("command") String command,
                                        @RequestParam("text") String text,
                                        @RequestParam("response_url") String responseUrl) {
        return mark;
    }

    @PostMapping(value = "/slack2/slash", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public TestResponse onReceiveSlashCommand2(@RequestParam("team_id") String teamId,
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