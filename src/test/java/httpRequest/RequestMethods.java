package httpRequest;

import converter.Converter;
import io.qameta.allure.Step;
import models.Contact;
import models.User;

import java.io.IOException;

public class RequestMethods {

    @Step("Создать пользователя")
    public static int createUser(String firstName, String lastName) throws IOException {
        User user = new User(firstName,lastName);
        return Requests.sendPostRequestAndGetId("users/", Converter.toJSON(user));
    }

    @Step("Создать контакт")
    public static int createContact(String firstName, String lastName, String phone, String email, int id) throws IOException {
        Contact contact = new Contact(firstName,lastName,phone,email);
        return Requests.sendPostRequestAndGetId("users/"+id+"/contacts", Converter.toJSON(contact));
    }
}
