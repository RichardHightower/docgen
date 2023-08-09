package com.cloudurable.docgen.util;

import java.io.File;

public class Env {

    public static String getOpenaiApiKey() {
        var openaiApiKey = System.getenv("OPENAI_API_KEY");
        if (openaiApiKey == null || openaiApiKey.isBlank()) {
            openaiApiKey = FileUtils.readFile(new File("./config/open_ai_api_key.txt"));
        }
        return openaiApiKey.trim();
    }

}
