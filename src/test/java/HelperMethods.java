import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

public class HelperMethods {
    public static void findAndCheckTrue(String lastName, int id, JSONArray usersList) {
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

    public static void findAndCheckFalse(String lastName, int id, JSONArray usersList)  {
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
