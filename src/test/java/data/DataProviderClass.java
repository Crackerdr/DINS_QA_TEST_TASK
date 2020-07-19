package data;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
    @DataProvider(name = "create_check")
    public Object[][] dataProviderCreateAndCheck() {
        return new Object[][] { { "TestCFirstName", "TestCLastName"} };
    }

    @DataProvider(name = "update_check")
    public Object[][] dataProviderUpdateAndCheck() {
        return new Object[][] { { "TestUFirstName", "TestULastName","NewUFirstName", "NewULastName"} };
    }
    @DataProvider(name = "delete_check")
    public Object[][] dataProviderDeleteAndCheck() {
        return new Object[][] { {"ContFirstName","ContLastName","1232399734", "bo@bo.com",1} };
    }
    @DataProvider(name = "change_blank_check")
    public Object[][] dataProviderBlankAndCheck() {
        return new Object[][] { { "TestFirstName", "TestLastName","3434343434","Dora@explora.com",3,
                "Vana","","gala@gmail.com"} };
    }
}
