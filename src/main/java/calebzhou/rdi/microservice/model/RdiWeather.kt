package calebzhou.rdi.microservice.model

import calebzhou.rdi.microservice.model.json.weather.Astro
import calebzhou.rdi.microservice.model.json.weather.CaiyunWeather
import calebzhou.rdi.microservice.model.json.weather.Temperature
import calebzhou.rdi.microservice.utils.RdiSerializer

/**
 * Created by calebzhou on 2022-10-03,20:39.
 */
fun parseCaiyunWeather(weatherJson:String) : RdiWeather{
    val caiyunWeather = RdiSerializer.gson.fromJson(weatherJson,CaiyunWeather::class.java)
    val rdiWeather = RdiWeather()
    val realTimeWeather = RdiRealTimeWeather()
    caiyunWeather.result?.alert?.content?.forEach{
        rdiWeather.alert+= """
            |${it.source} ${it.status}
            |${it.location}：${it.title}
            |${it.description}
            |=======================
        """.trimMargin()
    }
    val realtime = caiyunWeather.result?.realtime
    realTimeWeather.temp= realtime?.temperature!!
    realTimeWeather.humi= realtime.humidity!!
    realTimeWeather.skycon= realtime.skycon!!
    realTimeWeather.skyDesc= caiyunWeather?.result?.hourly?.description!!
    realTimeWeather.visi= realtime.visibility!!
    realTimeWeather.windSpd= realtime.wind?.speed!!
    realTimeWeather.windDir= realtime.wind?.direction!!
    realTimeWeather.pres= realtime.pressure!!
    realTimeWeather.aqi= realtime.airQuality?.aqi?.chn!!
    realTimeWeather.aqiDesc= realtime.airQuality?.description?.chn!!
    realTimeWeather.rainDesc= caiyunWeather.result?.minutely?.description!!
    realTimeWeather.uv= realtime.lifeIndex?.ultraviolet?.desc!!
    realTimeWeather.feel= realtime.lifeIndex?.comfort?.desc!!
    rdiWeather.realTimeWeather = realTimeWeather

    val daily = caiyunWeather.result!!.daily

    daily?.temperature?.forEachIndexed{ index: Int, temp: Temperature ->
        val dailyWeather=RdiDailyWeather()
        dailyWeather.skycon= daily.skycon[index].value!!
        dailyWeather.tempMin=daily.temperature[index].min!!
        dailyWeather.tempMax=daily.temperature[index].max!!
        dailyWeather.visiMin=daily.visibility[index].min!!
        dailyWeather.visiMax=daily.visibility[index].max!!
        dailyWeather.sunRise=daily.astro[index].sunrise?.time!!
        dailyWeather.sunSet=daily.astro[index].sunset?.time!!
        dailyWeather.preci=daily.precipitation[index].max!!
        dailyWeather.humiMin=daily.humidity[index].min!!
        dailyWeather.humiMax=daily.humidity[index].max!!
        dailyWeather.uv= daily.lifeIndex!!.ultraviolet[index].desc!!
        dailyWeather.feel=daily.lifeIndex!!.comfort[index].desc!!
        dailyWeather.cloth=daily.lifeIndex!!.dressing[index].desc!!
        dailyWeather.coldRisk=daily.lifeIndex!!.coldRisk[index].desc!!
        rdiWeather.dailyWeather.add(dailyWeather)
    }

    return rdiWeather

}

//RDI天气
data class RdiWeather(
    var alert:String="",
    var realTimeWeather: RdiRealTimeWeather?=null,
    val dailyWeather: MutableList<RdiDailyWeather> = mutableListOf(),
)
//RDI天气 实时
data class RdiRealTimeWeather(
    var temp:Float=0f,
    var humi:Float=0f,
    var skycon:String="",
    var skyDesc:String="",
    var visi: Float=0f,
    var windSpd: Float=0f,
    var windDir: Float=0f,
    var pres: Float=0f,
    var aqi: Int=0,
    var aqiDesc: String="",
    var rainDesc: String="",
    var uv: String="",
    var feel: String="",
)
//RDI天气 全天
data class RdiDailyWeather(
    var skycon: String="",
    var tempMin:Float=0f,
    var tempMax:Float=0f,
    var visiMin:Float=0f,
    var visiMax:Float=0f,
    var sunRise:String="",
    var sunSet:String="",
    var preci:Float=0f,
    var humiMin:Float=0f,
    var humiMax:Float=0f,
    var uv:String="",
    var feel:String="",
    var cloth:String="",
    var coldRisk:String="",
)