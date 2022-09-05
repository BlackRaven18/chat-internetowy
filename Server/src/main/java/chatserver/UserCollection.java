package chatserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserCollection implements Serializable {

    private static UserCollection instance;

    private List<User> userList;



    private UserCollection(){

        userList = new ArrayList<>();
        //userList.add(new User("Jan", "Kowalski", "1", "1", 1));
        //userList.add(new User("Arek", "Wolski", "2", "2", 2));
        //userList.add(new User("Marek", "Nowak", "3", "3", 3));
    }

    public static UserCollection getInstance(){
        if(instance == null){
            instance = new UserCollection();
        }
        return instance;
    }

    public User findUser(int id){
        for(User user : userList){
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public void saveAllConversationsToFiles(){
        for(User user : userList){
            user.saveConversationsToFile();
        }
    }

    public void deleteUser(int id){
        userList.remove(findUser(id));
    }

    public void addUser(User user){
        userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

}
