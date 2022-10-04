package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Local (

  @SerializedName("status"     ) var status     : String? = null,
  @SerializedName("datasource" ) var datasource : String? = null,
  @SerializedName("intensity"  ) var intensity  : Int?    = null

)