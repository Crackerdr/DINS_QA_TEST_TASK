import models.Contact;
import models.User;

import java.io.IOException;

public class RequestMethods {
    public int createUser(String firstName, String lastName) throws IOException {
        User user = new User(firstName,lastName);
        return Requests.sendPostRequestAndGetId("users/",Converter.toJSON(user));
    }
    public int createContact(String firstName, String lastName, String phone, String email, int id) throws IOException {
        Contact contact = new Contact(firstName,lastName,phone,email);
        return Requests.sendPostRequestAndGetId("users/"+id+"/contacts",Converter.toJSON(contact));
    }
}
