package test;

import check.HelperMethods;
import converter.Converter;
import data.DataProviderClass;
import httpRequest.RequestMethods;
import httpRequest.Requests;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.Contact;
import org.json.JSONArray;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

@Feature("Функциональные тесты")
@Story("Контакты")
public class ContactTest {
    final static Logger LOGGER = Logger.getLogger(Test.class.getName());

 @Test(description = "Проверка удаления контакта", dataProvider = "delete_check", dataProviderClass = DataProviderClass.class)
    public void deleteContactAndCheck(String firstName, String lastName, String phone, String email,int idOfUser) throws IOException {
        LOGGER.info("deleteContactAndCheck: Start");
        int id = RequestMethods.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray("users/"+idOfUser+"/contacts/search?number="+phone);
        HelperMethods.findAndCheckPhoneTrue(phone,id,listOfUser);
        Requests.sendDeleteRequest("users/"+idOfUser+"/contacts/"+id+"/");
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray("users/"+idOfUser+"/contacts/search?number="+phone);
        HelperMethods.findAndCheckPhoneFalse(phone,idOfUser,listOfUserAfter);
        LOGGER.info("deleteContactAndCheck: Finnish");
    }



    @Test(description = "Проверка изменения номера на пустое значение",dataProvider = "change_blank_check",dataProviderClass = DataProviderClass.class)
    public void changeToBlankAndCheck(String firstName,String lastName,String phone,String email,int idOfUser,
                                      String newLastName, String newNumber,String newEmail) throws IOException {
        LOGGER.info("changeToBlankAndCheck: Start");
        int id = RequestMethods.createContact(firstName,lastName,phone,email,idOfUser);
        JSONArray listOfUser = Requests.sendGetRequestArray("users/"+idOfUser+"/contacts");
        HelperMethods.findAndCheckNameTrue(lastName,id,listOfUser);
        Contact contact = new Contact("Dodo",newLastName,newNumber,newEmail);
        try {
        Requests.sendPutRequest("users/" + idOfUser + "/contacts/" + id + "/",
                    Converter.toJSON(contact));
        } catch (IOException e){
           LOGGER.info("Error");
        }
        JSONArray listOfUserAfter =  Requests.sendGetRequestArray("users/" + idOfUser + "/contacts");
        HelperMethods.findAndCheckNameFalse(newLastName,idOfUser,listOfUserAfter);
        LOGGER.info("changeToBlankAndCheck: Finnish");

    }
}
