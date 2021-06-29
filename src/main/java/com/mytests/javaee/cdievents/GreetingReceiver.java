package com.mytests.javaee.cdievents;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;


@ApplicationScoped @Named @FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class GreetingReceiver implements EventReceiver, Serializable {

    private static final long serialVersionUID = 1L;
    
    private String greet = "nothing here";
    
    @Inject
    private Synchronizer synchronizer;
    
    /**
     * Synchronous observer
     * 
     * @param greet 
     */
    void receiveSync(@Observes String greet) {
        System.out.println("==================receive sync=====================");
        this.greet = greet + " received";
    }


    void receiveAsync(@ObservesAsync String greet) {
        // Signal that we've started
        synchronizer.receiverStarted();
        
        // Wait till we're allowed to process this event
        synchronizer.waitTillReceiverMayProcess();
        
        try {
            // Simulate some amount of work
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("==================receive async=====================");
        this.greet += greet + " async received";
    }

    @Override
    public String getGreet() {
        return greet;
    }
}
