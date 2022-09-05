package chatserver;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final ObjectMapper mapper = new ObjectMapper();
    private UserCollection userCollection = UserCollection.getInstance();


    @GetMapping(value = "/users/{id}", produces = "application/json")
    @ResponseBody
    @SneakyThrows
    public ResponseEntity<String> getUser(@PathVariable int id){
        User user = userCollection.findUser(id);
        if(user != null){
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
            return new ResponseEntity<>(json, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/users", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getUsers(){
        JSONArray array = new JSONArray();

        try{
            for(User user : userCollection.getUserList()){
                String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
                JSONObject object = new JSONObject();

                object.put("name", user.getName());
                object.put("surname", user.getSurname());
                object.put("login", user.getLogin());
                object.put("password", user.getPassword());
                object.put("id", user.getId());
                array.put(object);
            }


            //String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(userCollection);

            return new ResponseEntity<>(array.toString(), HttpStatus.OK);
        }catch(JsonProcessingException exception){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id){

        if(userCollection.findUser(id) != null){
            userCollection.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/users", consumes ="application/json")
    public ResponseEntity<String> postNewUser(@RequestBody final User newUser){
        if(newUser != null){
            if(userCollection.findUser(newUser.getId()) == null) {
                userCollection.addUser(newUser);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("POST error", HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users/{id}", consumes ="application/json")
    public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody final User userDetails){
        User user = userCollection.findUser(id);

        if(user != null){
            user.setName(userDetails.getName());
            user.setSurname(userDetails.getSurname());
            user.setLogin(userDetails.getLogin());
            user.setPassword(userDetails.getPassword());
            user.setId(userDetails.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }




}
