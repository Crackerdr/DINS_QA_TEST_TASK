package check;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class HelperMethods {
    public static void findAndCheckNameTrue(String lastName, int id, JSONArray usersList) {
        String checkLastName="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkLastName = user.get("lastName").toString();
            }
        }
        Assert.assertEquals(checkLastName,lastName,"Параметр lastName не совпадает");
    }

    public static void findAndCheckNameFalse(String lastName, int id, JSONArray usersList)  {
        String checkLastName="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkLastName = user.get("lastName").toString();
            }
        }
        Assert.assertNotEquals(checkLastName,lastName,"Параметр lastName совпадает");
    }
    public static void findAndCheckPhoneTrue(String phone, int id, JSONArray usersList)  {
        String checkPhone="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkPhone = user.get("phone").toString();
            }
        }
        Assert.assertEquals(checkPhone,phone,"Параметр phone не совпадает");
    }
    public static void findAndCheckPhoneFalse(String phone, int id, JSONArray usersList)  {
        String checkPhone="";
        for(int i=0; i<usersList.length();i++){
            JSONObject user = usersList.getJSONObject(i);
            int checkId = Integer.parseInt(user.get("id").toString());
            if(checkId==id){
                checkPhone = user.get("phone").toString();
            }
        }
        Assert.assertNotEquals(checkPhone,phone,"Параметр phone совпадает");
    }
}
