package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Pm25 (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Int?    = null,
  @SerializedName("avg"  ) var avg  : Int?    = null,
  @SerializedName("min"  ) var min  : Int?    = null

)