import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class UserTest {
    final static Logger LOGGER = Logger.getLogger(UserTest.class.getName());

    @Test
    public void getListOfUsers() throws IOException {
        LOGGER.info("getListOfUsers: Start");
     JSONArray listOfUser = Requests.sendGetRequestArray("users/");
     for(int i = 0; i<listOfUser.length(); i++){
         JSONObject user = listOfUser.getJSONObject(i);
         String response = "id: " + user.get("id") + "\n"+
                 "First name: " + user.get("firstName") + "\n"+
                 "Last name: " + user.get("lastName");
         System.out.println(response);
     }
    }
    
    @Test
    public void updateUser() throws IOException {
        User user = new User("Dodo","Exploro");
        Requests.sendPutRequest("users/3/",Converter.toJSON(user));
    }

    @Test
    public void deleteUser() throws IOException {
        Requests.sendDeleteRequest("users/3/");
    }

    @Test(description = "Проверка создания пользователя")
    public void createUserAndCheck() throws IOException {
        LOGGER.info("createUserAndCheck: Start");
        String firstName = "Domo";
        String lastName = "Oro";
        HelperMethods help = new HelperMethods();
        RequestMethods req = new RequestMethods();
        int id = req.createUser(firstName, lastName);
        JSONArray usersList = Requests.sendGetRequestArray("users/search?name=" + firstName);
        help.findAndCheckTrue(lastName,id,usersList);
        LOGGER.info("createUserAndCheck: Finnish");
    }

    @DataProvider(name = "update_check")
    public Object[][] dataProviderUpdateAndCheck() {
        return new Object[][] { { "TestFirstName", "TestLastName","NewFirstName", "NewLastName"} };
    }

    @Test(description = "Проверка на поиск пользователя с помощью старого имени", dataProvider = "update_check")
    public void updateUserAndCheck(String firstName,String lastName,String changeFirstName,String changeLastName) throws IOException {
        LOGGER.info("updateUserAndCheck: Start");
        HelperMethods help = new HelperMethods();
        RequestMethods req = new RequestMethods();
        int id = req.createUser(firstName,lastName);
        User user = new User(changeFirstName,changeLastName) ;
        Requests.sendPutRequest("users/"+id+"/",Converter.toJSON(user));
        JSONArray listOfUser = Requests.sendGetRequestArray("users/");
        help.findAndCheckFalse(lastName,id,listOfUser);
        LOGGER.info("updateUserAndCheck: Finnish");
    }
}
