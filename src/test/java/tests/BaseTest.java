package tests;

import config.Config;
import data.UserData;
import helpers.ApiHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.provider.Arguments;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class BaseTest {
    protected WebDriver driver;
    protected UserData testUser;

    // Флаг, чтобы знать, был ли пользователь реально создан через API
    private boolean isUserCreatedViaApi = false;

    public static Stream<Arguments> browserProvider() {
        return Stream.of(
                Arguments.of("chrome", "Google Chrome"),
                Arguments.of("yandex", "Yandex Browser")
        );
    }

    @Step("Инициализация драйвера для браузера: {browserType}")
    protected void initDriver(String browserType) {
        WebDriverManager.chromedriver().clearResolutionCache();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--disable-search-engine-choice-screen");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        if ("yandex".equalsIgnoreCase(browserType)) {
            options.setBinary(Config.YANDEX_BINARY_PATH);
            WebDriverManager.chromedriver().browserVersion("150").setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Config.IMPLICIT_WAIT));
    }

    @Step("Создание тестового пользователя через API")
    protected void createTestUser() {
        String uniqueId = UUID.randomUUID().toString().substring(0, 8);
        testUser = new UserData(
                "TestUser_" + uniqueId,
                "test_" + uniqueId + "@mail.ru",
                "password123"
        );
        ApiHelper.createUser(testUser);
        isUserCreatedViaApi = true;
    }

    @Step("Удаление тестового пользователя через API")
    protected void deleteTestUser() {
        if (testUser != null && isUserCreatedViaApi) {
            ApiHelper.deleteUser(testUser);
            isUserCreatedViaApi = false;
        }
    }

    @AfterEach
    @Step("Очистка: удаление тестового пользователя и закрытие браузера")
    public void tearDown() {
        try {
            // Пытаемся удалить пользователя
            deleteTestUser();
        } catch (Exception e) {
            System.err.println("Предупреждение: не удалось удалить пользователя через API. " + e.getMessage());
        } finally {
            // закрываем браузер и ОБНУЛЯЕМ ссылку
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }
}