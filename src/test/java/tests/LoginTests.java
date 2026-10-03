package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Вход в аккаунт")
@Feature("Навигация к странице входа")
public class LoginTests extends BaseTest {

    @BeforeEach
    void setUp() {
        // Пользователь создаётся в BaseTest.createTestUser()
    }

    @ParameterizedTest(name = "Переход на страницу входа по кнопке 'Войти в аккаунт' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из шапки сайта")
    void loginViaHeaderLoginButton(String browserType, String browserName) {
        initDriver(browserType);

        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После клика на 'Войти в аккаунт' должны попасть на страницу /login");
    }

    @ParameterizedTest(name = "Переход на страницу входа по кнопке 'Личный кабинет' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из шапки сайта без авторизации")
    void loginViaHeaderProfileButton(String browserType, String browserName) {
        initDriver(browserType);

        MainPage mainPage = new MainPage(driver).open();
        // При клике на ЛК без авторизации сайт редиректит на страницу входа
        mainPage.clickProfileButton();

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "При клике на 'Личный кабинет' без авторизации должны попасть на страницу /login");
    }

    @ParameterizedTest(name = "Переход на страницу входа из формы регистрации в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из формы регистрации")
    void loginViaRegistrationForm(String browserType, String browserName) {
        initDriver(browserType);

        MainPage mainPage = new MainPage(driver).open();
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();

        // Кликаем по ссылке "Войти" внутри формы регистрации
        registerPage.clickLoginLink();

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После клика на 'Войти' в форме регистрации должны вернуться на страницу /login");
    }

    @ParameterizedTest(name = "Переход на страницу входа из формы восстановления пароля в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переход из формы восстановления пароля")
    void loginViaRestorePasswordForm(String browserType, String browserName) {
        initDriver(browserType);

        MainPage mainPage = new MainPage(driver).open();
        // Переходим: Главная -> Вход -> Восстановить пароль
        var restorePage = mainPage.clickLoginButton().clickRestorePasswordLink();

        // Кликаем по ссылке "Войти" на странице восстановления
        restorePage.clickLoginLink();

        assertTrue(driver.getCurrentUrl().contains("/login"),
                "После клика на 'Войти' в форме восстановления пароля должны вернуться на страницу /login");
    }
}