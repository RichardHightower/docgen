package com.cloudurable.docgen.generators;

import com.cloudurable.jai.model.text.completion.chat.Message;

public class GenUtils {

    public static String convertMessageToMarkdown(Message message) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("\n# ").append(message.getRole()).append("\n").append(message.getContent()).append("\n---\n");
        return messageBuilder.toString();
    }
}
