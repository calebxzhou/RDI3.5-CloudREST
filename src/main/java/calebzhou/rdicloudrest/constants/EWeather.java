package calebzhou.rdicloudrest.constants;
public enum EWeather {

    CLEAR_DAY(EColor.GOLD.code+"昼晴"+EColor.RESET.code, 0),
    CLEAR_NIGHT(EColor.GOLD.code+"夜晴"+EColor.RESET.code, 1),
    PARTLY_CLOUDY_DAY("昼多云", 2),
    PARTLY_CLOUDY_NIGHT("夜多云", 3),
    CLOUDY(EColor.GRAY.code+"阴"+EColor.RESET.code, 4),

    LIGHT_HAZE("轻霾", 5),
    MODERATE_HAZE("中霾", 6),
    HEAVY_HAZE("重霾", 7),

    LIGHT_RAIN(EColor.ORANGE.code+"小雨"+EColor.RESET.code, 8),
    MODERATE_RAIN(EColor.ORANGE.code+"中雨"+EColor.RESET.code, 9),
    HEAVY_RAIN(EColor.ORANGE.code+EColor.BOLD.code+"大雨"+EColor.RESET.code, 10),
    STORM_RAIN(EColor.ORANGE.code+EColor.BOLD.code+"暴雨"+EColor.RESET.code, 11),

    FOG(EColor.GRAY.code+"雾", 12),

    LIGHT_SNOW("小雪", 13),
    MODERATE_SNOW("中雪", 14),
    HEAVY_SNOW(EColor.BOLD.code+"大雪"+EColor.RESET.code, 15),
    STORM_SNOW(EColor.BOLD.code+"暴雪"+EColor.RESET.code, 16),

    DUST("浮尘", 17),
    SAND("沙尘", 18),
    WIND("大风", 19);
    private String name;
    private int index;

    // 构造方法
    private EWeather(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
