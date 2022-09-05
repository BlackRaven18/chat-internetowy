package chatserver;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {

    private String name;
    private String surname;
    private String login;
    private String password;
    private int id;
    private HashMap<Integer, MessagesManager> conversationList;

    public User(){
        conversationList = new HashMap<>();
    };


    public User(String name, String surname, String login, String password, int id) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.id = id;

        conversationList = new HashMap<>();
    }

    public User(String name, String surname, String login, String password, int id, HashMap<Integer, MessagesManager> conversationList) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.id = id;
        this.conversationList = conversationList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<Integer, MessagesManager> getConversationList() {
        return conversationList;
    }

    public void setConversationList(HashMap<Integer, MessagesManager> conversationList) {
        this.conversationList = conversationList;
    }

    public boolean addNewConversation(int id, MessagesManager messagesManager){
        if(this.id != id) {
            conversationList.put(id, messagesManager);
            return true;
        }
        return false;
    }

    public void addNewMessageToConversation(int id, String message){
        MessagesManager messagesManager = conversationList.get(id);
        messagesManager.addMessage(message);
    }

    public boolean checkIfConversationExist(int id){
        if(conversationList.containsKey(id)){
            return true;
        }
        return false;
    }

    public void saveConversationsToFile(){

        File directory = new File(this.name + "-" + this.surname + "- Conversations");
        String conversation;


        if(!directory.exists()){
            if(!directory.mkdir()){
                System.err.println("Saving messages to text file error!");
                return;
            }
        }

        for(Map.Entry<Integer, MessagesManager> entry : conversationList.entrySet()){
            Integer key = entry.getKey();
            MessagesManager manager = entry.getValue();

            UserCollection collection = UserCollection.getInstance();
            User user = collection.findUser(key);

            String fileName = user.getName() + "-" + user.getSurname() + ".txt";
            String path = directory + System.getProperty("file.separator") + fileName;

            try (PrintWriter writer = new PrintWriter(path)){

                for(String message : manager.getMessageList()){
                     writer.println(message);
                }


            } catch (FileNotFoundException e){
                System.out.println("Not able to write message to file");
            }
        }

    }



}
