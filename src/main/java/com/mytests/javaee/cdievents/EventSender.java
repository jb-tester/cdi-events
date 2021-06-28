package com.mytests.javaee.cdievents;

import java.util.concurrent.CompletionStage;


public interface EventSender {

    void sendSync(String message);
    CompletionStage<String> sendAsync(String message);
}
