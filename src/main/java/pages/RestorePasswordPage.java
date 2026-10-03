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

public class RestorePasswordPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Ссылка "Войти" на странице восстановления пароля
    @FindBy(xpath = "//a[contains(text(), 'Войти')]")
    private WebElement loginLink;

    public RestorePasswordPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
        PageFactory.initElements(driver, this);
    }

    @Step("Нажать кнопку 'Войти' на странице восстановления пароля")
    public LoginPage clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
        return new LoginPage(driver);
    }
}
