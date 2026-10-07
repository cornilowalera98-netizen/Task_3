package pages;

import config.Config;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ConstructorPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By BUNS_TAB = By.xpath("//div[contains(@class, 'tab_tab') and .//span[text()='Булки']]");
    private static final By SAUCES_TAB = By.xpath("//div[contains(@class, 'tab_tab') and .//span[text()='Соусы']]");
    private static final By FILLINGS_TAB = By.xpath("//div[contains(@class, 'tab_tab') and .//span[text()='Начинки']]");

    // Локатор для списка ингредиентов
    private static final By INGREDIENTS_LIST = By.xpath("//div[contains(@class, 'ingredient')]");

    public ConstructorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(Config.EXPLICIT_WAIT));
    }

    @Step("Нажать на вкладку «Булки»")
    public ConstructorPage clickBunsTab() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(BUNS_TAB));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    @Step("Нажать на вкладку «Соусы»")
    public ConstructorPage clickSaucesTab() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(SAUCES_TAB));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    @Step("Нажать на вкладку «Начинки»")
    public ConstructorPage clickFillingsTab() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(FILLINGS_TAB));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        return this;
    }

    @Step("Проверить, что вкладка «Булки» активна")
    public boolean isBunsTabActive() {
        return isTabActive(BUNS_TAB);
    }

    @Step("Проверить, что вкладка «Соусы» активна")
    public boolean isSaucesTabActive() {
        return isTabActive(SAUCES_TAB);
    }

    @Step("Проверить, что вкладка «Начинки» активна")
    public boolean isFillingsTabActive() {
        return isTabActive(FILLINGS_TAB);
    }

    @Step("Проверить, что список ингредиентов отображается")
    public boolean isIngredientsListDisplayed() {
        try {
            List<WebElement> ingredients = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(INGREDIENTS_LIST));
            return !ingredients.isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    private boolean isTabActive(By tabLocator) {
        try {
            wait.until(ExpectedConditions.attributeContains(tabLocator, "class", "tab_type_current"));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Проверить, что мы на странице конструктора")
    public boolean isOnConstructorPage() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.startsWith(Config.BASE_URL);
    }
}