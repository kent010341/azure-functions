package com.khctw;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class Function {

    @FunctionName("ProcessDTRoutedData")
    public void run(@EventGridTrigger(name = "event") String content,
            final ExecutionContext context) {
        context.getLogger().info("Event content: " + content);
    }
}
