package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        driver.get(Properties.getBaseAdminUrl());

        WebElement loginEl = wait.until(ExpectedConditions.elementToBeClickable((By.id("email"))));
        loginEl.sendKeys(login);
        WebElement passEl = driver.findElement(By.id("passwd"));
        passEl.sendKeys(password);
        WebElement submit = driver.findElement(By.name("submitLogin"));
        submit.click();
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        waitForContentLoad();
        driver.findElement(By.id("subtab-AdminCatalog")).click();
        waitForContentLoad();
        driver.findElement(By.id("page-header-desc-configuration-add"));
        waitForContentLoad();


        //Fill info fields
        driver.findElement(By.id("form_step1_name_1")).sendKeys(newProduct.getName());
        driver.findElement(By.id("form_step3_qty_0")).sendKeys(newProduct.getQty().toString());
        driver.findElement(By.id("form_step2_price_ttc")).sendKeys(newProduct.getPrice());

        driver.findElement(By.id("form_step1_active")).click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.name(""))).click();
        driver.findElement(By.xpath(
                "/html/body[@class='adminproducts']/div[@id='main-div']/div[@class='content-div -notoolbar']/div[@class='row ']/div[@class='col-xs-12']/form[@id='form']/div[@class='product-footer']/div[@class='col-lg-6 text-lg-right']/input[@id='submit']")).
                click();
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.name(""))).click();
    }

    public void checkProductCreation(ProductData newProduct){
        driver.get(Properties.getBaseUrl());
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("all-product-link pull-xs-left pull-md-right h4"))).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("js-product-list")));
        List<WebElement> titles = driver.findElements(By.cssSelector("h3 product-title"));
        Map<String, WebElement> ElementsMap = new HashMap<>();
        for (WebElement title:
             titles) {
            ElementsMap.put(title.getText(), title);
        }
        Assert.assertTrue(ElementsMap.containsKey((newProduct.getName())));

        ElementsMap.get(newProduct.getName()).click();
        //name assert
        Assert.assertEquals(
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(
                "/html/body[@id='product']/main/section[@id='wrapper']/div[@class='container']/div[@id='content-wrapper']/section[@id='main']/div[@class='row']/div[@class='col-md-6'][2]/h1[@class='h1']"))).
                getText(),
                newProduct.getName());
        //price assert
        Assert.assertEquals(driver.findElement(By.className("current-price")).findElement(By.tagName("span")).getAttribute("content"),
                newProduct.getPrice());
        //checking qty of product on the product page has no sense...

    }
    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".icon-refresh icon-spin icon-fw")));
    }
}
