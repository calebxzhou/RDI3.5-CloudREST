package calebzhou.rdi.microservice.constant;
public enum WeatherConst {

    CLEAR_DAY(ColorConst.GOLD.code+"白天 晴"+ ColorConst.RESET.code, 0),
    CLEAR_NIGHT(ColorConst.GOLD.code+"夜间 晴"+ ColorConst.RESET.code, 1),
    PARTLY_CLOUDY_DAY("白天 多云", 2),
    PARTLY_CLOUDY_NIGHT("夜间 多云", 3),
    CLOUDY(ColorConst.GRAY.code+"阴"+ ColorConst.RESET.code, 4),

    LIGHT_HAZE("轻霾", 5),
    MODERATE_HAZE("中霾", 6),
    HEAVY_HAZE("重霾", 7),

    LIGHT_RAIN(ColorConst.ORANGE.code+"小雨"+ ColorConst.RESET.code, 8),
    MODERATE_RAIN(ColorConst.ORANGE.code+"中雨"+ ColorConst.RESET.code, 9),
    HEAVY_RAIN(ColorConst.ORANGE.code+ ColorConst.BOLD.code+"大雨"+ ColorConst.RESET.code, 10),
    STORM_RAIN(ColorConst.ORANGE.code+ ColorConst.BOLD.code+"暴雨"+ ColorConst.RESET.code, 11),

    FOG(ColorConst.GRAY.code+"雾", 12),

    LIGHT_SNOW("小雪", 13),
    MODERATE_SNOW("中雪", 14),
    HEAVY_SNOW(ColorConst.BOLD.code+"大雪"+ ColorConst.RESET.code, 15),
    STORM_SNOW(ColorConst.BOLD.code+"暴雪"+ ColorConst.RESET.code, 16),

    DUST("浮尘", 17),
    SAND("沙尘", 18),
    WIND("大风", 19);
    private String name;
    private int index;

    // 构造方法
    private WeatherConst(String name, int index) {
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
