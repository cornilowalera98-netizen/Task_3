package pages;

import config.Config;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле Имя
    @FindBy(xpath = "//label[text()='Имя']/following-sibling::input")
    private WebElement nameField;

    // Поле Email
    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailField;

    // Поле Пароль
    @FindBy(xpath = "//label[text()='Пароль']/following-sibling::input")
    private WebElement passwordField;

    // Кнопка "Зарегистрироваться"
    @FindBy(xpath = "//button[text()='Зарегистрироваться']")
    private WebElement registerButton;

    // Ссылка "Войти" (для уже зарегистрированных)
    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginLink;

    // Сообщение об ошибке "Некорректный пароль"
    @FindBy(xpath = "//*[contains(@class, 'input__input-error') or contains(@class, 'input_error') or contains(text(), 'Некорректный') or contains(text(), '6 символов')]")
    private WebElement errorMessage;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
        PageFactory.initElements(driver, this);
    }

    @Step("Ввести имя: {name}")
    public RegisterPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameField)).clear();
        nameField.sendKeys(name);
        return this;
    }

    @Step("Ввести email: {email}")
    public RegisterPage enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailField)).clear();
        emailField.sendKeys(email);
        return this;
    }

    @Step("Ввести пароль: {password}")
    public RegisterPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).clear();
        passwordField.sendKeys(password);
        return this;
    }

    @Step("Нажать кнопку 'Зарегистрироваться'")
    public MainPage clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton)).click();
        return new MainPage(driver);
    }

    @Step("Перейти на страницу входа по ссылке в форме регистрации")
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }

    @Step("Зарегистрировать пользователя: {name}, {email}, {password}")
    public MainPage register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        return clickRegisterButton();
    }

    @Step("Получить текст сообщения об ошибке")
    public String getErrorMessage() {
        wait.until(ExpectedConditions.visibilityOf(errorMessage));
        return errorMessage.getText();
    }

    @Step("Проверить, что отображается сообщение об ошибке")
    public boolean isErrorMessageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
