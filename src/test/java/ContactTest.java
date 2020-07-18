import models.Contact;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

public class ContactTest {
    final static Logger LOGGER = Logger.getLogger(Test.class.getName());

    @Test
    public void addContact() throws IOException {
//        Contact contact = new Contact("firstNamej","lastNamek","8888887788","bo@bko.com");
//        Requests.sendPostRequest(Requests.PHONE_BOOK_BASE,"users/4/contacts",Converter.toJSON(contact));
        JSONArray listOfUser = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/4/contacts");
        for(int i = 0; i<listOfUser.length(); i++){
            JSONObject user = listOfUser.getJSONObject(i);
            String response = "id: " + user.get("id") + "\n"+
                    "First name: " + user.get("firstName") + "\n"+
                    "Last name: " + user.get("lastName")+ "\n"+
                    "Phone: "+ user.get("phone") + "\n"+
                    "Email: " + user.get("email");
            System.out.println(response);
        }
        Requests.sendDeleteRequest(Requests.PHONE_BOOK_BASE,"users/4/");
        JSONArray listOfUserAfter = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/4/contacts");
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

    @Test(description = "Проверка удаления контакта")
    public void deleteContactAndCheck() throws IOException {
        String firstName = "ContactFirstNameTest";
        String lastName = "ContactLastNameTest";
        String phone = "1232399734";
        String email = "bo@bo.com";
        int idOfUser = 1;
        ContactMethod contactMethod = new ContactMethod();
        int id = contactMethod.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/"+idOfUser+"/contacts");
        ContactMethod.findContactAndCheckTrue(lastName,id,listOfUser);
        Requests.sendDeleteRequest(Requests.PHONE_BOOK_BASE,"users/"+idOfUser+"/contacts/"+id+"/");
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/"+idOfUser+"/contacts");
        ContactMethod.findContactAndCheckFalse(lastName,idOfUser,listOfUserAfter);
    }

    @Test
    public void changeToBlankAndCheck() throws IOException {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String phone = "3434343434";
        String email = "Dora@explora";
        String newLastName = "Vana";
        String newNumber = "";
        String newEmail = "";
        int idOfUser = 2;
        ContactMethod contactMethod = new ContactMethod();
        int id = contactMethod.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/"+idOfUser+"/contacts");
        ContactMethod.findContactAndCheckTrue(lastName,id,listOfUser);
        Contact contact = new Contact("Dodo",newLastName,newNumber,newEmail);
        try {
            Requests.sendPutRequest(Requests.PHONE_BOOK_BASE, "users/" + idOfUser + "/contacts/" + id + "/",
                    Converter.toJSON(contact));
        } catch (IOException e){
           LOGGER.info("Error");
        }
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray(Requests.PHONE_BOOK_BASE,"users/"+idOfUser + "/contacts");
        ContactMethod.findContactAndCheckFalse(newLastName,idOfUser,listOfUserAfter);

    }
}
