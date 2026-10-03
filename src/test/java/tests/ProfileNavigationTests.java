package tests;


import config.Config;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.LoginPage;
import pages.ProfilePage;


import java.time.Duration;


import static org.junit.jupiter.api.Assertions.assertTrue;


@Epic("Навигация в личном кабинете")
@Feature("Переходы между страницами")
public class ProfileNavigationTests extends BaseTest {


    @BeforeEach
    void setUp() {
        // Пользователь создаётся в BaseTest.createTestUser()
    }


    @ParameterizedTest(name = "Переход в личный кабинет по клику на 'Личный кабинет' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход в профиль из шапки")
    @Story("Переход в профиль из шапки")
    void navigateToProfileViaHeaderButton(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём пользователя через API
        createTestUser();

        // 3. Заходим на сайт и логинимся через UI
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        // Переходим на страницу входа и входим
        var loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        // 4. Кликаем на "Личный кабинет" в шапке
        mainPage.clickProfileButton();

        // 5. Проверяем, что попали в профиль (URL содержит /profile или /account)
        assertTrue(driver.getCurrentUrl().contains("/profile") ||
                        driver.getCurrentUrl().contains("/account"),
                "После клика на 'Личный кабинет' должны попасть в профиль");
    }


    @ParameterizedTest(name = "Переход в конструктор по клику на 'Конструктор' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из профиля в конструктор по ссылке")
    void navigateToConstructorViaConstructorLink(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём пользователя и логинимся
        createTestUser();
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        var loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        // 3. Переходим в профиль
        mainPage.clickProfileButton();

        // 4. Кликаем на "Конструктор" в шапке
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickConstructorLink();

        // 5. Проверяем, что попали на главную (конструктор)
        assertTrue(driver.getCurrentUrl().equals(Config.BASE_URL) ||
                        driver.getCurrentUrl().equals(Config.BASE_URL + "/"),
                "После клика на 'Конструктор' должны попасть на главную страницу");
    }

    @ParameterizedTest(name = "Переход в конструктор по клику на логотип в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из профиля в конструктор по логотипу")
    void navigateToConstructorViaLogo(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём пользователя и логинимся
        createTestUser();
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        var loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        // 3. Переходим в профиль
        mainPage.clickProfileButton();

        // 4. Кликаем на логотип
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogo();

        // 5. Проверяем, что попали на главную (конструктор)
        assertTrue(driver.getCurrentUrl().equals(Config.BASE_URL) ||
                        driver.getCurrentUrl().equals(Config.BASE_URL + "/"),
                "После клика на логотип должны попасть на главную страницу");
    }

    @ParameterizedTest(name = "Выход из аккаунта по кнопке 'Выход' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Выход из профиля")
    void logoutFromProfile(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём пользователя и логинимся
        createTestUser();
        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        // 3. Переходим в профиль
        mainPage.clickProfileButton();

        // 4. Кликаем на "Выход"
        ProfilePage profilePage = new ProfilePage(driver);
        profilePage.clickLogoutButton();

        // 5. Ожидание
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> {
            String url = driver.getCurrentUrl();
            // Ждём, пока URL перестанет содержать /account
            return !url.contains("/account");
        });

        // 6. Дополнительная проверка: убеждаемся, что мы на главной
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains(Config.BASE_URL.replace("https://", "")),
                "После выхода из аккаунта должны быть на главной странице. Текущий URL: " + currentUrl);
    }
}
