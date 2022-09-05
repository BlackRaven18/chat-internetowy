package chatserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessagesManager implements Serializable {


    public MessagesManager(List<String> messageList) {
        this.messageList = messageList;
    }

    private List<String> messageList;

    public MessagesManager(){
        messageList = new ArrayList<>();
    }

    public void addMessage(String message){
        messageList.add(message);
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }
}
