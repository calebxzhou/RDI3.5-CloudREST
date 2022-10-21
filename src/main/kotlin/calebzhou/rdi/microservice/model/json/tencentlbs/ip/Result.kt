package calebzhou.rdi.microservice.model.json.tencentlbs.ip

import com.google.gson.annotations.SerializedName


data class Result (

  @SerializedName("ip"       ) var ip       : String?   = null,
  @SerializedName("location" ) var location : Location? = Location(),
  @SerializedName("ad_info"  ) var adInfo   : AdInfo?   = AdInfo()

)