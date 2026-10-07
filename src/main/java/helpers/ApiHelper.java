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
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(Config.REGISTER_ENDPOINT);
    }

    @Step("Получение токена пользователя через API для удаления")
    public static String loginUserAndGetToken(UserData user) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "application/json")
                .body(user)
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
                .filter(new AllureRestAssured())
                .header("Authorization", token)
                .when()
                .delete(Config.USER_ENDPOINT)
                .then()
                .statusCode(202);
    }
}