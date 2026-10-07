package tests;

import config.Config;
import data.UserData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Регистрация")
@Feature("Регистрация пользователя")
public class RegistrationTests extends BaseTest {

    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        initDriver();
        MainPage mainPage = new MainPage(driver).open();
        registerPage = mainPage.clickLoginButton().clickRegisterLink();
    }

    @Test
    @Story("Успешная регистрация")
    @DisplayName("Успешная регистрация нового пользователя с переходом на главную страницу")
    void successfulRegistration() {
        testUser = new UserData(
                "TestUser_" + System.currentTimeMillis(),
                "test_" + System.currentTimeMillis() + "@mail.ru",
                "password123"
        );

        registerPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        assertTrue(driver.getCurrentUrl().startsWith(Config.BASE_URL),
                "После успешной регистрации должны попасть на главную страницу");

        MainPage mainPageAfterRegister = new MainPage(driver);
        assertTrue(mainPageAfterRegister.isProfileButtonDisplayed(),
                "После регистрации пользователь должен быть авторизован (видна кнопка ЛК)");
    }

    @Test
    @Story("Валидация пароля")
    @DisplayName("Отображение ошибки при регистрации с паролем короче 6 символов")
    void registrationWithShortPasswordShouldFail() {
        String shortPassword = "12345";
        String email = "test_short_" + System.currentTimeMillis() + "@mail.ru";
        String name = "TestUser";

        registerPage.register(name, email, shortPassword);

        assertTrue(registerPage.isErrorMessageDisplayed(),
                "При пароле короче 6 символов должно появиться сообщение об ошибке");

        assertTrue(driver.getCurrentUrl().contains("/register"),
                "При ошибке валидации мы должны остаться на странице регистрации");
    }
}