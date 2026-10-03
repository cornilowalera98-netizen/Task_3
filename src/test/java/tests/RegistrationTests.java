package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;
import pages.RegisterPage;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Регистрация")
@Feature("Регистрация пользователя")
public class RegistrationTests extends BaseTest {

    @BeforeEach
    void setUp() {
        // Пустой метод, параметризация на уровне тестов
    }

    @ParameterizedTest(name = "Успешная регистрация в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")  // ✅ Исправлено!
    @Story("Успешная регистрация")
    void successfulRegistration(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём тестового пользователя через API
        createTestUser();

        // 3. Выполняем тест
        MainPage mainPage = new MainPage(driver).open();
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();
        registerPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // 4. Проверяем результат
        assertTrue(driver.getCurrentUrl().contains("/"),
                "После успешной регистрации должны попасть на главную");
    }

    @ParameterizedTest(name = "Ошибка при коротком пароле в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")  // ✅ Исправлено!
    @Story("Валидация пароля")
    void registrationWithShortPasswordShouldFail(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Создаём пользователя с коротким паролем
        testUser = new data.UserData("TestUser", "test_short@mail.ru", "12345");

        // 3. Переходим на страницу регистрации
        MainPage mainPage = new MainPage(driver).open();
        RegisterPage registerPage = mainPage.clickLoginButton().clickRegisterLink();

        // 4. Заполняем форму коротким паролем
        registerPage.register(testUser.getName(), testUser.getEmail(), testUser.getPassword());

        // 5. Проверяем, что отображается сообщение об ошибке
        assertTrue(registerPage.isErrorMessageDisplayed(),
                "При пароле короче 6 символов должно появиться сообщение об ошибке");

        // 6. Проверяем, что мы остались на странице регистрации
        assertTrue(driver.getCurrentUrl().contains("/register"),
                "При ошибке валидации мы должны остаться на странице регистрации");
    }
}