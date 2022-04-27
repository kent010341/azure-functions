package com.hwacom.aoscada;

import com.microsoft.azure.eventgrid.models.EventGridEvent;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.azure.functions.signalr.SignalRConnectionInfo;
import com.microsoft.azure.functions.signalr.SignalRMessage;
import com.microsoft.azure.functions.signalr.annotation.SignalRConnectionInfoInput;
import com.microsoft.azure.functions.signalr.annotation.SignalROutput;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.apache.commons.io.IOUtils;


public class Function {

    @FunctionName("index")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS)HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("index.html");
        String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return request.createResponseBuilder(HttpStatus.OK).header("Content-Type", "text/html").body(text).build();
    }

    @FunctionName("negotiate")
    public SignalRConnectionInfo negotiate(
            @HttpTrigger(
                    name = "req",
                    methods = { HttpMethod.POST },
                    authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> req,
            @SignalRConnectionInfoInput(
                    name = "connectionInfo",
                    hubName = "serverless") SignalRConnectionInfo connectionInfo) {

        return connectionInfo;
    }

    @FunctionName("broadcast")
    @SignalROutput(name = "$return", hubName = "serverless")
    public SignalRMessage broadcast(@EventGridTrigger(name = "event") String content) {
        return new SignalRMessage("newMessage", content);
    }
}
