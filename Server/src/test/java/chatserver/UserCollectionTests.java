package chatserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserCollectionTests {

    UserCollection collection;

    @BeforeEach
    void startUp(){
        collection = UserCollection.getInstance();
    }

    @Test
    void userShouldBeAdded(){
        User user = new User();
        collection.addUser(user);
        Assertions.assertFalse(collection.getUserList().isEmpty());
    }

    @Test
    void userShouldBeFound(){
        User user = new User("name", "surname", "login", "password", 1);
        collection.addUser(user);

        Assertions.assertEquals(user, collection.findUser(1));

    }

    @Test
    void userShouldBeDeleted(){
        User user = new User("name", "surname", "login", "password", 1);
        collection.addUser(user);
        collection.deleteUser(1);

        Assertions.assertNotEquals(user, collection.findUser(1));
    }




}
