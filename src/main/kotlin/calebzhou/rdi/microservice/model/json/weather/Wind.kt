package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Wind (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Max?    = Max(),
  @SerializedName("min"  ) var min  : Min?    = Min(),
  @SerializedName("avg"  ) var avg  : Avg?    = Avg()

)