package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Aqi (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Max?    = Max(),
  @SerializedName("avg"  ) var avg  : Avg?    = Avg(),
  @SerializedName("min"  ) var min  : Min?    = Min()

)