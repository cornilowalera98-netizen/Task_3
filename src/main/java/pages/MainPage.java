package pages;

import config.Config;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Кнопка "Войти в аккаунт"
    @FindBy(xpath = "//*[contains(text(), 'Войти в аккаунт')]")
    private WebElement loginButton;

    // Кнопка "Личный кабинет" (появляется после входа)
    @FindBy(xpath = "//p[contains(text(), 'Личный Кабинет')]/..")
    private WebElement profileButton;

    // Логотип Stellar Burgers (универсальный локатор)
    @FindBy(xpath = "//div[contains(@class, 'logo')] | //img[contains(@alt, 'Stellar Burgers')] | //a[contains(text(), 'Stellar Burgers')]")
    private WebElement logo;

    // Вкладка "Булки"
    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Булки']]")
    private WebElement bunsTab;

    // Вкладка "Соусы"
    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Соусы']]")
    private WebElement saucesTab;

    // Вкладка "Начинки"
    @FindBy(xpath = "//div[contains(@class, 'tab_tab') and .//span[text()='Начинки']]")
    private WebElement fillingsTab;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
        PageFactory.initElements(driver, this);
    }

    @Step("Открыть главную страницу Stellar Burgers")
    public MainPage open() {
        driver.get(Config.BASE_URL);
        return this;
    }

    @Step("Нажать кнопку 'Войти в аккаунт' в шапке")
    public LoginPage clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return new LoginPage(driver);
    }

    @Step("Нажать кнопку 'Личный кабинет' в шапке")
    public ProfilePage clickProfileButton() {
        By modalOverlay = By.xpath("//div[contains(@class, 'modal__container')]");
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(modalOverlay));
        } catch (Exception ignored) {
        }

        wait.until(ExpectedConditions.visibilityOf(profileButton));
        wait.until(ExpectedConditions.elementToBeClickable(profileButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", profileButton);
        profileButton.click();
        return new ProfilePage(driver);
    }

    @Step("Нажать на логотип Stellar Burgers")
    public MainPage clickLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(logo)).click();
        return this;
    }

    @Step("Перейти в раздел 'Булки'")
    public MainPage clickBunsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsTab)).click();
        return this;
    }

    @Step("Перейти в раздел 'Соусы'")
    public MainPage clickSaucesTab() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesTab)).click();
        return this;
    }

    @Step("Перейти в раздел 'Начинки'")
    public MainPage clickFillingsTab() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsTab)).click();
        return this;
    }

    @Step("Проверить, что кнопка 'Личный кабинет' отображается")
    public boolean isProfileButtonDisplayed() {
        try {
            return profileButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что кнопка 'Войти в аккаунт' отображается")
    public boolean isLoginButtonDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что вкладка 'Булки' активна")
    public boolean isBunsTabActive() {
        try {
            String className = bunsTab.getAttribute("class");
            return className != null && className.contains("tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что вкладка 'Соусы' активна")
    public boolean isSaucesTabActive() {
        try {
            String className = saucesTab.getAttribute("class");
            return className != null && className.contains("tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверить, что вкладка 'Начинки' активна")
    public boolean isFillingsTabActive() {
        try {
            String className = fillingsTab.getAttribute("class");
            return className != null && className.contains("tab_type_current");
        } catch (Exception e) {
            return false;
        }
    }
}