package com.mytests.javaee.cdievents;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Model;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

@Model @FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class GreetingSender implements EventSender {

    @Inject
    private Event<String> event;

    
    @Override
    public void sendSync(String message) {
        event.fire(message);
    }

    @Override
    public CompletionStage<String> sendAsync(String message) {
        System.out.println("==================Sending async========================");
        return event.fireAsync(message);
    }
}
