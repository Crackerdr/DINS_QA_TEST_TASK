package test;

import check.HelperMethods;
import converter.Converter;
import data.DataProviderClass;
import httpRequest.RequestMethods;
import httpRequest.Requests;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import models.User;
import org.json.JSONArray;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.logging.Logger;

@Feature("Функциональные тесты")
@Story("Пользователи")
public class UserTest {
    final static Logger LOGGER = Logger.getLogger(UserTest.class.getName());

    @Test(description = "Проверка создания пользователя",dataProvider = "create_check", dataProviderClass = DataProviderClass.class)
    public void createUserAndCheck(String firstName,String lastName) throws IOException {
        LOGGER.info("createUserAndCheck: Start");
        int id = RequestMethods.createUser(firstName, lastName);
        JSONArray usersList = Requests.sendGetRequestArray("users/search?name=" + firstName);
        HelperMethods.findAndCheckNameTrue(lastName,id,usersList);
        LOGGER.info("createUserAndCheck: Finnish");
    }



    @Test(description = "Проверка на поиск пользователя с помощью старого имени", dataProvider = "update_check", dataProviderClass = DataProviderClass.class)
    public void updateUserAndCheck(String firstName,String lastName,String changeFirstName,String changeLastName) throws IOException {
        int id = RequestMethods.createUser(firstName,lastName);
        User user = new User(changeFirstName,changeLastName) ;
        Requests.sendPutRequest("users/"+id+"/", Converter.toJSON(user));
        JSONArray listOfUser = Requests.sendGetRequestArray("users/");
        HelperMethods.findAndCheckNameFalse(lastName,id,listOfUser);
        LOGGER.info("updateUserAndCheck: Finnish");
    }
}
