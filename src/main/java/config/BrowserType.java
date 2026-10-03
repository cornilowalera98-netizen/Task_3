package config;

public enum BrowserType {
    CHROME("chrome"),
    YANDEX("yandex");

    private final String name;

    BrowserType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
