package helpers;

import config.Config;
import data.UserData;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiHelper {

    @Step("Создание пользователя через API: {user.email}")
    public static Response createUser(UserData user) {
        String body = String.format(
                "{\"name\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}",
                user.getName(), user.getEmail(), user.getPassword()
        );

        return given()
                .filter(new AllureRestAssured()) // ← Исправлено здесь
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(Config.REGISTER_ENDPOINT);
    }

    @Step("Получение токена пользователя через API для удаления")
    public static String loginUserAndGetToken(UserData user) {
        String body = String.format(
                "{\"email\":\"%s\",\"password\":\"%s\"}",
                user.getEmail(), user.getPassword()
        );

        return given()
                .filter(new AllureRestAssured()) // ← Исправлено здесь
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(Config.LOGIN_ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");
    }

    @Step("Удаление пользователя через API")
    public static void deleteUser(UserData user) {
        String token = loginUserAndGetToken(user);

        given()
                .filter(new AllureRestAssured()) // ← Исправлено здесь
                .header("Authorization", token)
                .when()
                .delete(Config.USER_ENDPOINT)
                .then()
                .statusCode(202);
    }
}