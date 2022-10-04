package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Description (

  @SerializedName("usa" ) var usa : String? = null,
  @SerializedName("chn" ) var chn : String? = null

)