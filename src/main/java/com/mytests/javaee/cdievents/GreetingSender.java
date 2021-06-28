package com.mytests.javaee.cdievents;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


public class GreetingSender implements EventSender {

    @Inject
    private Event<String> event;

    
    @Override
    public void sendSync(String message) {
        event.fire(message);
    }

    @Override
    public CompletionStage<String> sendAsync(String message) {
        System.out.println("Sending async");
        return event.fireAsync(message);
    }
}
