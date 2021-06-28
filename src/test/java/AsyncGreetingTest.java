import com.mytests.javaee.cdievents.EventReceiver;
import com.mytests.javaee.cdievents.EventSender;
import com.mytests.javaee.cdievents.Synchronizer;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.TimeUnit.SECONDS;


public class AsyncGreetingTest {


    @Inject
    private EventSender sender;

    @Inject
    private EventReceiver receiver;
    
    @Inject
    private Synchronizer synchronizer;

    @Test
    public void test() throws Exception {
        Assert.assertNotNull(sender);
        Assert.assertNotNull(receiver);


        // Default greet
        Assert.assertEquals("Willkommen", receiver.getGreet());
        
        // Send a new greet synchronously
        sender.sendSync("Welcome");
        
        // Receiver must not belong to the dependent pseudo-scope since we are checking the result
        Assert.assertEquals("Welcome-sync", receiver.getGreet());
        
        // Send a new greet asynchronously
        CompletionStage<String> completionStage = sender.sendAsync("Hi");
        
        synchronizer.waitTillReceiverStarted();
        
        // The receiver has started, signal that it may now start processing
        // This is done to test that the receiver is really on a different thread
        synchronizer.receiverMayProcess();
        
        completionStage.toCompletableFuture().get(15, SECONDS);

        Assert.assertEquals("Welcome-syncHi-async", receiver.getGreet());
        
    }
}
