package chatserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MessagesManagerTests {

    @Test
    void messageShouldBeAdded(){
        MessagesManager manager = new MessagesManager();
        manager.addMessage("test message");

        Assertions.assertFalse(manager.getMessageList().isEmpty());
    }
}
