import models.Contact;
import models.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.io.IOException;

public class ContactMethod {

    public int createContact(String firstName, String lastName, String phone, String email, int id) throws IOException {
        Contact contact = new Contact(firstName,lastName,phone,email);
        return Requests.sendPostRequestAndGetId(Requests.PHONE_BOOK_BASE,"users/"+id+"/contacts",Converter.toJSON(contact));
    }

    public static void findContactAndCheckTrue(String lastName, int id, JSONArray usersList) {
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

    public static void findContactAndCheckFalse(String lastName, int id, JSONArray usersList)  {
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
