package calebzhou.rdi.microservice.ctrler;

import calebzhou.rdi.microservice.constants.ColorConst;
import calebzhou.rdi.microservice.model.geo.GeoLocation;
import calebzhou.rdi.microservice.model.geo.Weather;
import calebzhou.rdi.microservice.constants.CloudConst;
import calebzhou.rdi.microservice.constants.WeatherConst;
import calebzhou.rdi.microservice.model.geo.WeatherRealTime;
import calebzhou.rdi.microservice.utils.GeographyUtils;
import calebzhou.rdi.microservice.utils.HttpUtils;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

@RestController
@RequestMapping("/misc")
public class MiscCtrler {
    //天气预报
    @RequestMapping(value = "/weather",method = RequestMethod.GET)
    public String getWeather(@RequestParam String ip, HttpServletRequest req){
        //未指定ip参数，就使用远程的ip
        if(StringUtils.isEmpty(ip))
            ip=req.getRemoteAddr();
        StringBuilder message = new StringBuilder();
        GeoLocation location = GeographyUtils.getGeoLocationFromIP(ip);
        //本地ip等特殊情况
        if (location.status != 0) {
            location = GeographyUtils.getGeoLocationFromIP("202.107.26.39");
        }
        String nation = location.result.ad_info.nation;
        /*if (!nation.equals("中国")) {
            return null;
        }*/
        String province = location.result.ad_info.province.replace("省","");
        String city = location.result.ad_info.city.replace("市", "");
        String district = location.result.ad_info.district.replace("区", "")
                .replace("市", "");
        message.append(String.format("@%s,%s,%s,%s@\n",nation,province,city,district));
        //纬度
        double latitude = location.result.location.lat;
        //经度
        double longitude = location.result.location.lng;
        //获取天气预报json
        String weatherJsonData = HttpUtils.doGet(CloudConst.caiyunUrl + longitude + "," + latitude + "/daily.json");
        String weatherRTJsonData = HttpUtils.doGet(CloudConst.caiyunUrl + longitude + "," + latitude + "/realtime.json?alert=true");
        Weather we = new Gson().fromJson(weatherJsonData, Weather.class);
        WeatherRealTime rtwe = new Gson().fromJson(weatherRTJsonData, WeatherRealTime.class);
        Weather.Result.Daily daily = we.result.daily;
        WeatherConst weatherState = WeatherConst.valueOf(daily.skycon[0].value);//daily.skycon[0].value;

        int lowTmp = (int) Math.round(daily.temperature[0].min);
        int hiTmp = (int) Math.round(daily.temperature[0].max);
        int preci = (int) Math.round(daily.precipitation[0].max * 100);
        int humid = (int) Math.round(daily.humidity[0].max *100  );
        float wind = (float) (daily.wind[0].max.speed / 10f);

        WeatherConst weatherState2 = WeatherConst.valueOf(daily.skycon[1].value);//daily.skycon[0].value;
        int lowTmp2 = (int) Math.round(daily.temperature[1].min);
        int hiTmp2 = (int) Math.round(daily.temperature[1].max);
        int preci2 = (int) Math.round(daily.precipitation[1].max * 100);
        int humid2 = (int) Math.round(daily.humidity[1].max *100  );
        float wind2 = (float) (daily.wind[1].max.speed / 10f);
        int alertLength = rtwe.result.alert.content.length;
        for (int i = 0; i < alertLength; ++i) {
            WeatherRealTime.Result.Alert.Content cont = rtwe.result.alert.content[i];

            message.append(ColorConst.ORANGE.code).append(cont.title).append(cont.description).append("\n");
        }
        String airQ = rtwe.result.realtime.air_quality.description.chn;
        int aqi = (int) rtwe.result.realtime.air_quality.aqi.chn;
        float tempNow = rtwe.result.realtime.temperature;
        int hour = LocalTime.now().getHour();


        String msgCityTemp = String.format("\n%s-%s %s 现在 %s%s ℃ ",
                city, district, ColorConst.BRIGHT_GREEN.code, ColorConst.RESET.code, (int) tempNow);
        if (aqi < 50)
            airQ = ColorConst.BRIGHT_GREEN.code + airQ;
        else if (aqi > 50 && aqi < 100)
            airQ = ColorConst.AQUA.code + airQ;
        else if (aqi > 100 && aqi < 150)
            airQ = ColorConst.ORANGE.code + airQ;
        else if (aqi > 150 && aqi < 200)
            airQ = ColorConst.PINK.code + airQ;
        else if (aqi > 200 && aqi < 300)
            airQ = ColorConst.PURPLE.code + airQ;
        else if (aqi > 300)
            airQ = ColorConst.DARK_RED.code + airQ;
        String msgAir = "空气" + airQ + "(" + aqi + ") "+ ColorConst.RESET.code;
        String msgLine1 = msgCityTemp.concat(msgAir);
        String msgLine2 = String.format("\n%s今天 %s%s %s / %s℃ 湿%s%% 降水%s%% 风速%.2fm/s ",
                ColorConst.AQUA.code, weatherState.getName(), ColorConst.RESET.code, lowTmp, hiTmp, humid, preci, wind);
        String msgLine3 = String.format("\n%s明天 %s%s %s / %s℃ 湿%s%% 降水%s%% 风速%.2fm/s ",
                ColorConst.AQUA.code, weatherState2.getName(), ColorConst.RESET.code, lowTmp2, hiTmp2, humid2, preci2,wind2);
        //xx(市) 2020年x月x日 晴 xx~xxC 湿度xx% 降水率xx%
        message.append(msgLine1);
        message.append(msgLine2);
        if (hour >= 17)
            message.append(msgLine3);
        return message.toString();

    }
}
