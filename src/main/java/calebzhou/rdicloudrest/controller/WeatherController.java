package calebzhou.rdicloudrest.controller;

import calebzhou.rdicloudrest.constants.CloudServiceConstants;
import calebzhou.rdicloudrest.constants.EColor;
import calebzhou.rdicloudrest.constants.EWeather;
import calebzhou.rdicloudrest.model.IP2Location;
import calebzhou.rdicloudrest.model.WeatherRealTime;
import calebzhou.rdicloudrest.model.Weather;
import calebzhou.rdicloudrest.utils.GeographyUtils;
import calebzhou.rdicloudrest.utils.HttpUtils;
import calebzhou.rdicloudrest.utils.ResponseUtils;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

@WebServlet("/getWeather")
public class WeatherController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        StringBuilder message = new StringBuilder();
        String playerIp = req.getParameter("ip");
        IP2Location location = GeographyUtils.getGeoLocationFromIP(playerIp);
        //本地ip等特殊情况
        if (location.status != 0) {
            message.append("无法获取天气预报，将显示默认城市的天气预报。\n");
            location = GeographyUtils.getGeoLocationFromIP("202.107.26.39");
        }
        String nation = location.result.ad_info.nation;
        if (!nation.equals("中国")) {
            ResponseUtils.write(resp, "抱歉，无法获取您所在地区的天气预报。");
            return;
        }
        String city = location.result.ad_info.city.replace("市", "");
        String district = location.result.ad_info.district.replace("区", "")
                .replace("市", "");
        //纬度
        double latitude = location.result.location.lat;
        //经度
        double longitude = location.result.location.lng;
        //获取天气预报json
        String weatherJsonData = HttpUtils.doGet(CloudServiceConstants.caiyunUrl + longitude + "," + latitude + "/daily.json");
        String weatherRTJsonData = HttpUtils.doGet(CloudServiceConstants.caiyunUrl + longitude + "," + latitude + "/realtime.json?alert=true");
        Weather we = new Gson().fromJson(weatherJsonData, Weather.class);
        WeatherRealTime rtwe = new Gson().fromJson(weatherRTJsonData, WeatherRealTime.class);
        Weather.Result.Daily daily = we.result.daily;
        EWeather weatherState = EWeather.valueOf(daily.skycon[0].value);//daily.skycon[0].value;

        int lowTmp = (int) Math.round(daily.temperature[0].min);
        int hiTmp = (int) Math.round(daily.temperature[0].max);
        int preci = (int) Math.round(daily.precipitation[0].max * 100);
        int humid = (int) Math.round(daily.humidity[0].max *100  );
        float wind = (float) (daily.wind[0].max.speed / 10f);

        EWeather weatherState2 = EWeather.valueOf(daily.skycon[1].value);//daily.skycon[0].value;
        int lowTmp2 = (int) Math.round(daily.temperature[1].min);
        int hiTmp2 = (int) Math.round(daily.temperature[1].max);
        int preci2 = (int) Math.round(daily.precipitation[1].max * 100);
        int humid2 = (int) Math.round(daily.humidity[1].max *100  );
        float wind2 = (float) (daily.wind[1].max.speed / 10f);
        int alertLength = rtwe.result.alert.content.length;
        for (int i = 0; i < alertLength; ++i) {
            WeatherRealTime.Result.Alert.Content cont = rtwe.result.alert.content[i];

            message.append(EColor.ORANGE.code + cont.title + cont.description + "\n");
        }
        String airQ = rtwe.result.realtime.air_quality.description.chn;
        int aqi = (int) rtwe.result.realtime.air_quality.aqi.chn;
        float tempNow = rtwe.result.realtime.temperature;
        int hour = LocalTime.now().getHour();


        String msgCityTemp = String.format("%s-%s %s现%s%s℃ ",
                city, district, EColor.BRIGHT_GREEN.code, EColor.RESET.code, (int) tempNow);
        if (aqi < 50)
            airQ = EColor.BRIGHT_GREEN.code + airQ;
        else if (aqi > 50 && aqi < 100)
            airQ = EColor.AQUA.code + airQ;
        else if (aqi > 100 && aqi < 150)
            airQ = EColor.ORANGE.code + airQ;
        else if (aqi > 150 && aqi < 200)
            airQ = EColor.PINK.code + airQ;
        else if (aqi > 200 && aqi < 300)
            airQ = EColor.PURPLE.code + airQ;
        else if (aqi > 300)
            airQ = EColor.DARK_RED.code + airQ;
        String msgAir = "空气" + airQ + "(" + aqi + ") "+EColor.RESET.code;
        String msgLine1 = msgCityTemp.concat(msgAir);
        String msgLine2 = String.format("%s今%s%s %s/%s℃ 湿%s%% 降水%s%% 风速%.2fm/s ",
                EColor.AQUA.code, weatherState.getName(), EColor.RESET.code, lowTmp, hiTmp, humid, preci, wind);
        String msgLine3 = String.format("%s明%s%s %s/%s℃ 湿%s%% 降水%s%% 风速%.2fm/s ",
                EColor.AQUA.code, weatherState2.getName(), EColor.RESET.code, lowTmp2, hiTmp2, humid2, preci2,wind2);
        //xx(市) 2020年x月x日 晴 xx~xxC 湿度xx% 降水率xx%
        message.append(msgLine1 + "\n");
        message.append(msgLine2 + "\n");
        if (hour >= 17)
            message.append(msgLine3 + "\n");
        ResponseUtils.write(resp, message.toString());
    }
}
