package com.catware.eatapp.slack.handler;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.springframework.stereotype.Component;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.asElements;
import static com.slack.api.model.block.element.BlockElements.button;

@Component
public class HelloCommandHandler implements SlashCommandHandler {
    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) {
        return context.ack(asBlocks(
                section(section -> section.text(markdownText(":wave: pong"))),
                actions(actions -> actions
                        .elements(asElements(
                                button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                        ))
                )
        ));
    }
}
