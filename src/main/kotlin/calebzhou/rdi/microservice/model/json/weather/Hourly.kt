package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Hourly (

  @SerializedName("status"        ) var status        : String?                  = null,
  @SerializedName("description"   ) var description   : String?                  = null,
  @SerializedName("precipitation" ) var precipitation : ArrayList<Precipitation> = arrayListOf(),
  @SerializedName("temperature"   ) var temperature   : ArrayList<Temperature>   = arrayListOf(),
  @SerializedName("wind"          ) var wind          : ArrayList<Wind>          = arrayListOf(),
  @SerializedName("humidity"      ) var humidity      : ArrayList<Humidity>      = arrayListOf(),
  @SerializedName("cloudrate"     ) var cloudrate     : ArrayList<Cloudrate>     = arrayListOf(),
  @SerializedName("skycon"        ) var skycon        : ArrayList<Skycon>        = arrayListOf(),
  @SerializedName("pressure"      ) var pressure      : ArrayList<Pressure>      = arrayListOf(),
  @SerializedName("visibility"    ) var visibility    : ArrayList<Visibility>    = arrayListOf(),
  @SerializedName("dswrf"         ) var dswrf         : ArrayList<Dswrf>         = arrayListOf(),
  @SerializedName("air_quality"   ) var airQuality    : AirQuality?              = AirQuality()

)