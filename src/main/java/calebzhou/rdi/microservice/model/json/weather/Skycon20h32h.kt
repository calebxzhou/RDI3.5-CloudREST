package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Skycon20h32h (

  @SerializedName("date"  ) var date  : String? = null,
  @SerializedName("value" ) var value : String? = null

)