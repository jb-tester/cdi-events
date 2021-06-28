package com.mytests.javaee.cdievents;

import java.util.concurrent.CompletionStage;

/**
 * @author Radim Hanus
 * @author Arun Gupta
 * @author Arjan Tijms
 */
public interface EventSender {

    void sendSync(String message);
    CompletionStage<String> sendAsync(String message);
}
