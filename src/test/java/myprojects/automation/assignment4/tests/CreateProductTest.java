package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.*;

public class CreateProductTest extends BaseTest {
    private ProductData newProduct;

    @BeforeTest
    public void generateProduct(){
        newProduct = ProductData.generate();
    }

    @Parameters({"login", "password"})
    @Test
    public void createNewProduct(String login, String password) {
        // TODO implement test for product creation

        actions.login(login, password);
        actions.createProduct(newProduct);
    }

    // TODO implement logic to check product visibility on website
    @AfterTest
    public void checkProductCreation(){
        actions.checkProductCreation(newProduct);
    }

}
