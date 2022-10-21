package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class CaiyunWeather (

  @SerializedName("status"      ) var status     : String?           = null,
  @SerializedName("api_version" ) var apiVersion : String?           = null,
  @SerializedName("api_status"  ) var apiStatus  : String?           = null,
  @SerializedName("lang"        ) var lang       : String?           = null,
  @SerializedName("unit"        ) var unit       : String?           = null,
  @SerializedName("tzshift"     ) var tzshift    : Double?              = null,
  @SerializedName("timezone"    ) var timezone   : String?           = null,
  @SerializedName("server_time" ) var serverTime : Double?              = null,
  @SerializedName("location"    ) var location   : ArrayList<Double> = arrayListOf(),
  @SerializedName("result"      ) var result     : Result?           = Result()

)