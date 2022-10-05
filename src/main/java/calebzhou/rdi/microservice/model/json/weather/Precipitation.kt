package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Precipitation (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Float?    = null,
  @SerializedName("min"  ) var min  : Int?    = null,
  @SerializedName("avg"  ) var avg  : Int?    = null

)