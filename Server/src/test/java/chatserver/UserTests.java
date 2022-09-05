package chatserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class UserTests {

    private User testUser;

    @BeforeEach
    void setUp(){
        testUser = new User("name", "surname", "login", "password", 1);
    }



    @Test
    void newConversationShouldBeAdded() {
        MessagesManager manager = new MessagesManager();

        Assertions.assertTrue(testUser.addNewConversation(2, manager));
    }
    @Test
    void newConversationShouldNotBeAdded() {
        int id = 1;
        MessagesManager manager = new MessagesManager();

        Assertions.assertFalse(testUser.addNewConversation(1, manager));
    }

    @Test
    void newMessageShouldBeAddedToConversation(){
        MessagesManager manager = new MessagesManager();
        testUser.addNewConversation(2, manager);

        manager.addMessage("message");
        Assertions.assertFalse(manager.getMessageList().isEmpty());
    }

    @Test
    void conversationShouldExist(){
        MessagesManager manager = new MessagesManager();
        testUser.addNewConversation(2, manager);

        Assertions.assertTrue(testUser.checkIfConversationExist(2));
    }


}
