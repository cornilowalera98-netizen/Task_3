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

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    // Ссылка "Зарегистрироваться"
    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    // Ссылка "Восстановить пароль"
    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement restorePasswordLink;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести email: {email}")
    public LoginPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).sendKeys(email);
        return this;
    }

    @Step("Ввести пароль: {password}")
    public LoginPage enterPassword(String password) {
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Войти'")
    public MainPage clickLoginButton() {
        loginButton.click();
        return new MainPage(driver);
    }

    @Step("Перейти на страницу регистрации")
    public RegisterPage clickRegisterLink() {
        registerLink.click();
        return new RegisterPage(driver);
    }

    @Step("Перейти на страницу восстановления пароля")
    public RestorePasswordPage clickRestorePasswordLink() {
        restorePasswordLink.click();
        return new RestorePasswordPage(driver);
    }

    @Step("Войти в аккаунт с email: {email} и паролем: {password}")
    public MainPage login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        return clickLoginButton();
    }
}
