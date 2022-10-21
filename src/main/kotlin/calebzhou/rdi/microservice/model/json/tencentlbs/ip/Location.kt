package calebzhou.rdi.microservice.model.json.tencentlbs.ip

import com.google.gson.annotations.SerializedName


data class Location (

  @SerializedName("lat" ) var lat : Double? = null,
  @SerializedName("lng" ) var lng : Double? = null

)