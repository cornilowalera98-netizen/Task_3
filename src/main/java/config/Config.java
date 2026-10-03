package config;

public class Config {
    // Официальный URL сайта для твоего задания
    public static final String BASE_URL = "https://stellarburgers.education-services.ru/";

    // API
    public static final String API_URL = "https://stellarburgers.education-services.ru/api/";
    public static final String REGISTER_ENDPOINT = API_URL + "auth/register";
    public static final String LOGIN_ENDPOINT = API_URL + "auth/login";
    public static final String USER_ENDPOINT = API_URL + "auth/user";

    // Таймауты (в секундах)
    public static final int IMPLICIT_WAIT = 15;
    public static final int EXPLICIT_WAIT = 25;

    // Пути к браузерам (Windows)
    public static final String CHROME_BINARY_PATH = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
    public static final String YANDEX_BINARY_PATH = "C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe";

    // Имена браузеров
    public static final String BROWSER_CHROME = "chrome";
    public static final String BROWSER_YANDEX = "yandex";

    private Config() {
    }
}