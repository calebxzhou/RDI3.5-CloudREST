package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Realtime (

  @SerializedName("status"               ) var status              : String?        = null,
  @SerializedName("temperature"          ) var temperature         : Float?           = null,
  @SerializedName("humidity"             ) var humidity            : Float?        = null,
  @SerializedName("cloudrate"            ) var cloudrate           : Float?        = null,
  @SerializedName("skycon"               ) var skycon              : String?        = null,
  @SerializedName("visibility"           ) var visibility          : Float?           = null,
  @SerializedName("dswrf"                ) var dswrf               : Float?        = null,
  @SerializedName("wind"                 ) var wind                : RtWind?          = RtWind(),
  @SerializedName("pressure"             ) var pressure            : Float?        = null,
  @SerializedName("apparent_temperature" ) var apparentTemperature : Float?        = null,
  @SerializedName("precipitation"        ) var precipitation       : Precipitation? = Precipitation(),
  @SerializedName("air_quality"          ) var airQuality          : RtAirQuality?    = RtAirQuality(),
  @SerializedName("life_index"           ) var lifeIndex           : RtLifeIndex?     = RtLifeIndex()

)
data class RtWind(
  @SerializedName("speed" ) var speed : Float? = null,
  @SerializedName("direction"  ) var direction  : Float?    = null,
)
data class RtAirQuality(
  @SerializedName("aqi" ) var aqi : RtAqi? = RtAqi(),
  @SerializedName("description"  ) var description  : RtAqiDescription?    = RtAqiDescription(),
)

data class RtAqiDescription(@SerializedName("chn" ) var chn : String? = null,
                            @SerializedName("usa"  ) var usa  : String?    = null,)
data class RtAqi(
  @SerializedName("chn" ) var chn : Double? = null,
  @SerializedName("usa"  ) var usa  : Double?    = null,
)
data class RtLifeIndex (
  @SerializedName("ultraviolet" ) var ultraviolet : RtUltraviolet? = RtUltraviolet(),
  @SerializedName("comfort"     ) var comfort     : RtComfort?     = RtComfort()

)
data class RtComfort (

  @SerializedName("index" ) var index : Double?    = null,
  @SerializedName("desc"  ) var desc  : String? = null

)
data class RtUltraviolet (

  @SerializedName("index" ) var index : Double?    = null,
  @SerializedName("desc"  ) var desc  : String? = null

)