package pages;

import config.Config;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // "Конструктор" в шапке (ведёт на главную)
    @FindBy(xpath = "//p[contains(text(), 'Конструктор')]/..")
    private WebElement constructorLink;

    // Логотип Stellar Burgers
    @FindBy(xpath = "//div[contains(@class, 'header__logo')]")
    private WebElement logo;

    // Кнопка "Выход" в боковом меню профиля
    @FindBy(xpath = "//button[contains(text(), 'Выход')]")
    private WebElement logoutButton;

    // Поле "Имя"
    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameField;

    // Поле "Email"
    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    // Поле "Пароль"
    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;

    // Кнопка "Сохранить"
    @FindBy(xpath = "//button[contains(text(), 'Сохранить')]")
    private WebElement saveButton;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
        PageFactory.initElements(driver, this);
    }

    @Step("Нажать на ссылку 'Конструктор' в шапке")
    public MainPage clickConstructorLink() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorLink)).click();
        return new MainPage(driver);
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
        return new MainPage(driver);
    }

    @Step("Нажать кнопку 'Выход'")
    public LoginPage clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
        return new LoginPage(driver);
    }

    @Step("Проверить, что кнопка 'Выход' отображается")
    public boolean isLogoutButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(logoutButton));
            return logoutButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что страница профиля загружена")
    public boolean isProfilePageLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("/account"));
            wait.until(ExpectedConditions.visibilityOf(emailField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получить значение поля 'Email'")
    public String getEmailFieldValue() {
        return emailField.getAttribute("value");
    }

       @Step("Ввести новое имя: {name}")
    public ProfilePage enterName(String name) {
        nameField.clear();
        nameField.sendKeys(name);
        return this;
    }

    @Step("Ввести новый пароль: {password}")
    public ProfilePage enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Сохранить'")
    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

}