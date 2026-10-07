package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.ConstructorPage;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Конструктор бургеров")
@Feature("Навигация по разделам конструктора")
public class ConstructorSectionTests extends BaseTest {

    @BeforeEach
    void setUp() {
        initDriver();
        new MainPage(driver).open();
    }

    @Test
    @Story("Переключение вкладок: Булки")
    @DisplayName("Переход к разделу 'Булки' делает вкладку активной и загружает ингредиенты")
    void navigateToBunsSection() {
        ConstructorPage constructorPage = new ConstructorPage(driver);

        constructorPage.clickBunsTab();

        assertTrue(constructorPage.isBunsTabActive(),
                "После клика на 'Булки' эта вкладка должна быть активной");
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Булки' должен отображаться");
    }

    @Test
    @Story("Переключение вкладок: Соусы")
    @DisplayName("Переход к разделу 'Соусы' делает вкладку активной и загружает ингредиенты")
    void navigateToSaucesSection() {
        ConstructorPage constructorPage = new ConstructorPage(driver);

        constructorPage.clickSaucesTab();

        assertTrue(constructorPage.isSaucesTabActive(),
                "После клика на 'Соусы' эта вкладка должна быть активной");
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Соусы' должен отображаться");
    }

    @Test
    @Story("Переключение вкладок: Начинки")
    @DisplayName("Переход к разделу 'Начинки' делает вкладку активной и загружает ингредиенты")
    void navigateToFillingsSection() {
        ConstructorPage constructorPage = new ConstructorPage(driver);

        constructorPage.clickFillingsTab();

        assertTrue(constructorPage.isFillingsTabActive(),
                "После клика на 'Начинки' эта вкладка должна быть активной");
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Начинки' должен отображаться");
    }
}