package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Nearest (

  @SerializedName("status"    ) var status    : String? = null,
  @SerializedName("distance"  ) var distance  : Double? = null,
  @SerializedName("intensity" ) var intensity : Double? = null

)