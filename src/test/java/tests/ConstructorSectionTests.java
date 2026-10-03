package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.ConstructorPage;
import pages.MainPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Конструктор бургеров")
@Feature("Навигация по разделам конструктора")
public class ConstructorSectionTests extends BaseTest {

    @ParameterizedTest(name = "Переход к разделу 'Булки' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переключение вкладок: Булки")
    @DisplayName("Переход к разделу 'Булки' делает вкладку активной и загружает ингредиенты")
    void navigateToBunsSection(String browserType, String browserName) {
        // 1. Инициализируем драйвер
        initDriver(browserType);

        // 2. Открываем главную страницу (где находится конструктор)
        new MainPage(driver).open();

        // 3. Создаем экземпляр страницы конструктора
        ConstructorPage constructorPage = new ConstructorPage(driver);

        // 4. Кликаем на вкладку "Булки"
        constructorPage.clickBunsTab();

        // 5. Проверяем, что вкладка стала активной
        assertTrue(constructorPage.isBunsTabActive(),
                "После клика на 'Булки' эта вкладка должна быть активной");

        // 6. Проверяем, что список ингредиентов загрузился
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Булки' должен отображаться");
    }

    @ParameterizedTest(name = "Переход к разделу 'Соусы' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переключение вкладок: Соусы")
    @DisplayName("Переход к разделу 'Соусы' делает вкладку активной и загружает ингредиенты")
    void navigateToSaucesSection(String browserType, String browserName) {
        initDriver(browserType);
        new MainPage(driver).open();

        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickSaucesTab();

        assertTrue(constructorPage.isSaucesTabActive(),
                "После клика на 'Соусы' эта вкладка должна быть активной");
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Соусы' должен отображаться");
    }

    @ParameterizedTest(name = "Переход к разделу 'Начинки' в браузере: {1}")
    @MethodSource("tests.BaseTest#browserProvider")
    @Story("Переключение вкладок: Начинки")
    @DisplayName("Переход к разделу 'Начинки' делает вкладку активной и загружает ингредиенты")
    void navigateToFillingsSection(String browserType, String browserName) {
        initDriver(browserType);
        new MainPage(driver).open();

        ConstructorPage constructorPage = new ConstructorPage(driver);
        constructorPage.clickFillingsTab();

        assertTrue(constructorPage.isFillingsTabActive(),
                "После клика на 'Начинки' эта вкладка должна быть активной");
        assertTrue(constructorPage.isIngredientsListDisplayed(),
                "Список ингредиентов в разделе 'Начинки' должен отображаться");
    }
}