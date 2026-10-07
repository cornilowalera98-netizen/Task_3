package tests;

import config.Config;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Вход в аккаунт")
@Feature("Навигация к странице входа")
public class LoginTests extends BaseTest {

    @BeforeEach
    void setUp() {
        initDriver();
        new MainPage(driver).open();
    }

    @Test
    @Story("Переход из шапки сайта")
    @DisplayName("Переход на страницу входа по кнопке 'Войти в аккаунт' в шапке сайта")
    void loginViaHeaderLoginButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();
        assertOnLoginPage();
    }

    @Test
    @Story("Переход из шапки сайта без авторизации")
    @DisplayName("Редирект на страницу входа при клике на 'Личный кабинет' без авторизации")
    void loginViaHeaderProfileButton() {
        MainPage mainPage = new MainPage(driver);
        // При клике на ЛК без авторизации сайт редиректит на страницу входа
        mainPage.clickProfileButton();
        assertOnLoginPage();
    }

    @Test
    @Story("Переход из формы регистрации")
    @DisplayName("Переход на страницу входа по ссылке из формы регистрации")
    void loginViaRegistrationForm() {
        MainPage mainPage = new MainPage(driver);
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.clickLoginLink();
        assertOnLoginPage();
    }

    @Test
    @Story("Переход из формы восстановления пароля")
    @DisplayName("Переход на страницу входа по ссылке из формы восстановления пароля")
    void loginViaRestorePasswordForm() {
        MainPage mainPage = new MainPage(driver);
        var restorePage = mainPage.clickLoginButton().clickRestorePasswordLink();
        restorePage.clickLoginLink();
        assertOnLoginPage();
    }

    @Test
    @Story("Успешный вход существующего пользователя")
    @DisplayName("Успешный вход в аккаунт с валидными данными существующего пользователя")
    void loginExistingUser() {
        createTestUser();

        // Главная страница уже открыта в @BeforeEach, сразу переходим ко входу
        MainPage mainPage = new MainPage(driver);
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.equals(Config.BASE_URL) ||
                        currentUrl.startsWith(Config.BASE_URL + "?") ||
                        currentUrl.contains("/constructor"),
                "После успешного входа должны попасть на главную страницу. Текущий URL: " + currentUrl);

        MainPage mainPageAfterLogin = new MainPage(driver);
        assertTrue(mainPageAfterLogin.isProfileButtonDisplayed(),
                "После входа должна отображаться кнопка 'Личный кабинет'");
    }

    private void assertOnLoginPage() {
        assertTrue(driver.getCurrentUrl().contains("/login"),
                "Должны попасть на страницу /login");

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoginFormDisplayed(),
                "На странице входа должна отображаться форма входа");
    }
}