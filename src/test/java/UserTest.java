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
     JSONArray listOfUser = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/");
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
        Requests.sendPutRequest(Requests.PHONE_BOOK_BASE,"users/3/",Converter.toJSON(user));
    }

    @Test
    public void deleteUser() throws IOException {
        Requests.sendDeleteRequest(Requests.PHONE_BOOK_BASE,"users/3/");
    }

    @Test(description = "Проверка создания пользователя")
    public void createUserAndCheck() throws IOException {
        LOGGER.info("createUserAndCheck: Start");
        String firstName = "Domo";
        String lastName = "Oro";
        UserMethods userMethod = new UserMethods();
        int id = userMethod.createUser(firstName, lastName);
        JSONArray usersList = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE, "users/search?name=" + firstName);
        UserMethods.findUserAndCheckTrue(firstName,lastName,id,usersList);
        LOGGER.info("createUserAndCheck: Finnish");
    }

    @DataProvider(name = "old_new_date")
    public Object[][] dataProviderMethod() {
        return new Object[][] { { "TestFirstName", "TestLastName"}, { "NewFirstName", "NewLastName"} };
    }

    @Test(description = "Проверка на поиск пользователя с помощью старого имени")
    public void updateUserAndCheck() throws IOException {
        LOGGER.info("updateUserAndCheck: Start");
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String changeFirstName = "NewFirstName";
        String changeLastName = "NewLastName";
        UserMethods userMethods = new UserMethods();
        int id = userMethods.createUser(firstName,lastName);
        User user = new User(changeFirstName,changeLastName) ;
        Requests.sendPutRequest(Requests.PHONE_BOOK_BASE,"users/"+id+"/",Converter.toJSON(user));
        JSONArray listOfUser = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/");
        UserMethods.findUserAndCheckFalse(firstName,lastName,id,listOfUser);
        LOGGER.info("updateUserAndCheck: Finnish");
    }
}
