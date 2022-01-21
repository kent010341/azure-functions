package com.khctw;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class Function {

    @FunctionName("ProcessDTRoutedData")
    public void run(@EventGridTrigger(name = "event") String content,
                    final ExecutionContext context) {
        // 用 Azure Function 的 Log 記錄 content
        context.getLogger().info("Event content: " + content);
    }
}
