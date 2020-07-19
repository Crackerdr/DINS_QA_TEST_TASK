import models.Contact;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class ContactTest {
    final static Logger LOGGER = Logger.getLogger(Test.class.getName());

    @Test
    public void addContact() throws IOException {
        JSONArray listOfUser = Requests.sendGetRequestArray("users/4/contacts");
        for(int i = 0; i<listOfUser.length(); i++){
            JSONObject user = listOfUser.getJSONObject(i);
            String response = "id: " + user.get("id") + "\n"+
                    "First name: " + user.get("firstName") + "\n"+
                    "Last name: " + user.get("lastName")+ "\n"+
                    "Phone: "+ user.get("phone") + "\n"+
                    "Email: " + user.get("email");
            System.out.println(response);
        }
        Requests.sendDeleteRequest("users/4/");
        JSONArray listOfUserAfter = Requests.sendGetRequestArray("users/4/contacts");
        for(int i = 0; i<listOfUserAfter.length(); i++){
            JSONObject user = listOfUser.getJSONObject(i);
            String response = "id: " + user.get("id") + "\n"+
                    "First name: " + user.get("firstName") + "\n"+
                    "Last name: " + user.get("lastName")+ "\n"+
                    "Phone: "+ user.get("phone") + "\n"+
                    "Email: " + user.get("email");
            System.out.println(response);
        }
    }

    @DataProvider(name = "delete_check")
    public Object[][] dataProviderDeleteAndCheck() {
        return new Object[][] { { "ContactFirstNameTest","ContactLastNameTest","1232399734", "bo@bo.com"} };
    }

    @Test(description = "Проверка удаления контакта", dataProvider = "delete_check")
    public void deleteContactAndCheck(String firstName, String lastName, String phone, String email) throws IOException {
        LOGGER.info("deleteContactAndCheck: Start");
        int idOfUser = 1;
        HelperMethods help = new HelperMethods();
        RequestMethods req = new RequestMethods();
        int id = req.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray("users/"+idOfUser+"/contacts");
        help.findAndCheckTrue(lastName,id,listOfUser);
        Requests.sendDeleteRequest("users/"+idOfUser+"/contacts/"+id+"/");
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray("users/"+idOfUser+"/contacts");
        help.findAndCheckFalse(lastName,idOfUser,listOfUserAfter);
        LOGGER.info("deleteContactAndCheck: Finnish");
    }

    @DataProvider(name = "change_blank_check")
    public Object[][] dataProviderBlankAndCheck() {
        return new Object[][] { { "TestFirstName", "TestLastName","3434343434","Dora@explora.com",
                "Vana","","gala@gmail.com"} };
    }

    @Test(description = "Проверка изменения номера на пустое значение",dataProvider = "change_blank_check")
    public void changeToBlankAndCheck(String firstName,String lastName,String phone,String email,
                                      String newLastName, String newNumber,String newEmail) throws IOException {
        LOGGER.info("changeToBlankAndCheck: Start");
        int idOfUser = 2;
        HelperMethods help = new HelperMethods();
        RequestMethods req = new RequestMethods();
        int id = req.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray("users/"+idOfUser+"/contacts");
        help.findAndCheckTrue(lastName,id,listOfUser);
        Contact contact = new Contact("Dodo",newLastName,newNumber,newEmail);
        try {
            Requests.sendPutRequest("users/" + idOfUser + "/contacts/" + id + "/",
                    Converter.toJSON(contact));
        } catch (IOException e){
           LOGGER.info("Error");
        }
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray("users/"+idOfUser + "/contacts");
        help.findAndCheckFalse(newLastName,idOfUser,listOfUserAfter);
        LOGGER.info("changeToBlankAndCheck: Finnish");

    }
}
