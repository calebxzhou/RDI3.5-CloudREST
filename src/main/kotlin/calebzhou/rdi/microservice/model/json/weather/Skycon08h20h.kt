package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Skycon08h20h (

  @SerializedName("date"  ) var date  : String? = null,
  @SerializedName("value" ) var value : String? = null

)