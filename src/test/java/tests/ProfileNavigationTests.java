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
import pages.ProfilePage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Навигация в личном кабинете")
@Feature("Переходы между страницами")
public class ProfileNavigationTests extends BaseTest {

    @BeforeEach
    void setUp() {
        initDriver();
        createTestUser();

        MainPage mainPage = new MainPage(driver).open();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterEmail(testUser.getEmail());
        loginPage.enterPassword(testUser.getPassword());
        loginPage.clickLoginButton();

        // Ждем завершения редиректа после логина, чтобы тесты начинались со стабильного состояния
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/login")));
    }

    @Test
    @Story("Переход в профиль из шапки")
    @DisplayName("Переход в профиль через кнопку 'Личный кабинет' в шапке сайта")
    void navigateToProfileViaHeaderButton() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileButton();

        assertTrue(driver.getCurrentUrl().contains("/profile") || driver.getCurrentUrl().contains("/account"), "После клика на 'Личный кабинет' должны попасть в профиль");

        assertTrue(new ProfilePage(driver).isLogoutButtonDisplayed(), "На странице профиля должна отображаться кнопка 'Выход'");
    }

    @Test
    @Story("Переход из профиля в конструктор по ссылке")
    @DisplayName("Переход на главную страницу по ссылке 'Конструктор' из профиля")
    void navigateToConstructorViaConstructorLink() {
        new MainPage(driver).clickProfileButton();
        new ProfilePage(driver).clickConstructorLink();

        assertTrue(driver.getCurrentUrl().startsWith(Config.BASE_URL), "После клика на 'Конструктор' должны попасть на главную страницу");

        assertTrue(new MainPage(driver).isProfileButtonDisplayed(), "На главной странице должна отображаться кнопка 'Личный кабинет'");
    }

    @Test
    @Story("Переход из профиля в конструктор по логотипу")
    @DisplayName("Переход на главную страницу по клику на логотип из профиля")
    void navigateToConstructorViaLogo() {
        new MainPage(driver).clickProfileButton();
        new ProfilePage(driver).clickLogo();

        assertTrue(driver.getCurrentUrl().startsWith(Config.BASE_URL), "После клика на логотип должны попасть на главную страницу");

        assertTrue(new MainPage(driver).isProfileButtonDisplayed(), "На главной странице должна отображаться кнопка 'Личный кабинет'");
    }

    @Test
    @Story("Выход из профиля")
    @DisplayName("Выход из аккаунта через кнопку 'Выход' в профиле")
    void logoutFromProfile() {
        new MainPage(driver).clickProfileButton();
        new ProfilePage(driver).clickLogoutButton();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> !d.getCurrentUrl().contains("/account") && !d.getCurrentUrl().contains("/profile"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.startsWith(Config.BASE_URL), "После выхода из аккаунта должны быть на главной странице. Текущий URL: " + currentUrl);

        assertTrue(new LoginPage(driver).isLoginFormDisplayed(), "После выхода должна отображаться форма входа");
    }
}