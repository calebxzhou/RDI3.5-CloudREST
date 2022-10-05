package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Cloudrate (

  @SerializedName("date" ) var date : String? = null,
  @SerializedName("max"  ) var max  : Double? = null,
  @SerializedName("min"  ) var min  : Int?    = null,
  @SerializedName("avg"  ) var avg  : Double? = null

)