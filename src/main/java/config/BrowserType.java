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

    public static BrowserType getCurrent() {
        String browserName = System.getProperty("browser", "chrome").toLowerCase();
        for (BrowserType type : BrowserType.values()) {
            if (type.getName().equalsIgnoreCase(browserName)) {
                return type;
            }
        }
        return CHROME;
    }
}
