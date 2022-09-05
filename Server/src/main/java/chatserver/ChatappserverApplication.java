package chatserver;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class ChatappserverApplication implements ApplicationRunner{

	private final String usersFileName = "Users.xml";


	public static void main(String[] args) {
		SpringApplication.run(ChatappserverApplication.class, args);
	}


	@Override
	public void run(ApplicationArguments args) {
		deserializeUsers();
		MessageServer messageServer = new MessageServer();

		messageServer.start(5555);
		messageServer.stop();
	}

	@PreDestroy
	private void stop(){
		UserCollection collection = UserCollection.getInstance();

		serializeUsers();
		collection.saveAllConversationsToFiles();
	}

	private void serializeUsers(){
		try{
			XMLSerialization.serializeToXML(usersFileName, UserCollection.getInstance().getUserList());
		} catch(IOException e){
			e.getMessage();
		}
	}


	public void deserializeUsers(){
		try{
			UserCollection.getInstance().setUserList((List<User>)XMLSerialization.deserializeFromXML(usersFileName));
		} catch(IOException exception){
			System.out.println("Nieudana deserializacja");
		}

	}





}
