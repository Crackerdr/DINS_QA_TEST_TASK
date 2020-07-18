import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserMethods {

    public int createUser(String firstName, String lastName) throws IOException {
        User user = new User(firstName,lastName);
       return Requests.sendPostRequestAndGetId(Requests.PHONE_BOOK_BASE,"users/",Converter.toJSON(user));
    }


    public static void findUserAndCheckTrue(String firstName, String lastName, int id, JSONArray usersList) throws IOException {
        String checkLastName="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkLastName = user.get("lastName").toString();
            }
        }
        Assert.assertEquals(checkLastName,lastName);
    }

    public static void findUserAndCheckFalse(String firstName, String lastName, int id, JSONArray usersList) throws IOException {
        String checkLastName="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkLastName = user.get("lastName").toString();
            }
        }
        Assert.assertFalse(checkLastName.equals(lastName));
    }
}
