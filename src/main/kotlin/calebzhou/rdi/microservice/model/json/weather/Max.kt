package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Max (

  @SerializedName("chn" ) var chn : Double? = null,
  @SerializedName("usa" ) var usa : Double? = null

)