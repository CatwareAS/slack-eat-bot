package com.catware.eatapp.slack.handler;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.block.composition.OptionObject;
import org.springframework.stereotype.Component;

import com.slack.api.model.view.View;
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;
import com.slack.api.bolt.util.JsonOps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PreferencesCommandHandler implements SlashCommandHandler {

    class PrivateMetadata {
        String responseUrl;
        String commandArgument;
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) throws IOException, SlackApiException {

// when a user enters some word in "Topics"
 /*       app.blockSuggestion("topics-action", (req, ctx) -> {
            String keyword = req.getPayload().getValue();
            List<Option> options = allOptions.stream()
                    .filter(o -> ((PlainTextObject) o.getText()).getText().contains(keyword))
                    .collect(toList());
            return ctx.ack(r -> r.options(options.isEmpty() ? allOptions : options));
        });
*/
// when a user chooses an item from the "Topics"
  /*      app.blockAction("topics-action", (req, ctx) -> {
            return ctx.ack();
        });

*/
        String username = slashCommandRequest.getPayload().getUserName();
        String userId = slashCommandRequest.getPayload().getUserId();

        //List<Option> allOptions = getCategories(userId);


        PrivateMetadata data = new PrivateMetadata();
        data.responseUrl = context.getResponseUrl();
        data.commandArgument = slashCommandRequest.getPayload().getText();

        ViewsOpenResponse viewsOpenRes = context.client().viewsOpen(r -> r
                .triggerId(context.getTriggerId())
                .view(buildView()));
        if (viewsOpenRes.isOk()) return context.ack();
        else return Response.builder().statusCode(500).body(viewsOpenRes.getError()).build();





  /*      return context.ack(
                asBlocks(
                        section(section -> {
                            section.text(markdownText(":wave: pong"));
                            section.accessory(checkboxes(checkboxes -> {
                                checkboxes.options(listOpt);
                                checkboxes.actionId("test");
                                return checkboxes;
                            }));
                            return section;
                        }),
        actions(actions -> actions
                .elements(asElements(
                        button(b -> b.actionId("ping-again").text(plainText(pt -> pt.text("Ping"))).value("ping"))
                ))
        )
        ));*/
    }

    View buildView() {

        List<OptionObject> listOpt = new ArrayList<>();
        listOpt.add(new OptionObject(plainText("Schedule", true), "ttt", plainText("Schedule", true), "http://test.com"));
        return view(view -> view.callbackId("meeting-arrangement")
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("Meeting Arrangement").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .privateMetadata("")
                .blocks(asBlocks(
                        section(section -> section
                                .blockId("category-block")
                                .text(markdownText("Select a category of the meeting!"))
                                .accessory(staticSelect(staticSelect -> staticSelect
                                        .actionId("category-selection-action")
                                        .placeholder(plainText("Select a category"))
                                        .options(asOptions(
                                                option(plainText("Customer"), "customer"),
                                                option(plainText("Partner"), "partner"),
                                                option(plainText("Internal"), "internal")
                                        ))
                                )
                        )),
                        input(input -> input
                                .blockId("agenda-block")
                                .element(plainTextInput(pti -> pti.actionId("agenda-action").multiline(true)))
                                .label(plainText(pt -> pt.text("Detailed Agenda").emoji(true)))
                        )
                ))
        );
    }

}