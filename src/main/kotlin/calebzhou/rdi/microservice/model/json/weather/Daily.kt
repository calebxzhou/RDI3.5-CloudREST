package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Daily (

  @SerializedName("status"         ) var status        : String?                  = null,
  @SerializedName("astro"          ) var astro         : ArrayList<Astro>         = arrayListOf(),
  @SerializedName("precipitation"  ) var precipitation : ArrayList<Precipitation> = arrayListOf(),
  @SerializedName("temperature"    ) var temperature   : ArrayList<Temperature>   = arrayListOf(),
  @SerializedName("wind"           ) var wind          : ArrayList<Wind>          = arrayListOf(),
  @SerializedName("humidity"       ) var humidity      : ArrayList<Humidity>      = arrayListOf(),
  @SerializedName("cloudrate"      ) var cloudrate     : ArrayList<Cloudrate>     = arrayListOf(),
  @SerializedName("pressure"       ) var pressure      : ArrayList<Pressure>      = arrayListOf(),
  @SerializedName("visibility"     ) var visibility    : ArrayList<Visibility>    = arrayListOf(),
  @SerializedName("dswrf"          ) var dswrf         : ArrayList<Dswrf>         = arrayListOf(),
  @SerializedName("air_quality"    ) var airQuality    : AirQuality?              = AirQuality(),
  @SerializedName("skycon"         ) var skycon        : ArrayList<Skycon>        = arrayListOf(),
  @SerializedName("skycon_08h_20h" ) var skycon08h20h  : ArrayList<Skycon08h20h>  = arrayListOf(),
  @SerializedName("skycon_20h_32h" ) var skycon20h32h  : ArrayList<Skycon20h32h>  = arrayListOf(),
  @SerializedName("life_index"     ) var lifeIndex     : LifeIndex?               = LifeIndex()

)