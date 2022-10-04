package calebzhou.rdi.microservice.model.json.tencentlbs.ip

import com.google.gson.annotations.SerializedName


data class AdInfo (

  @SerializedName("nation"   ) var nation   : String? = null,
  @SerializedName("province" ) var province : String? = null,
  @SerializedName("city"     ) var city     : String? = null,
  @SerializedName("district" ) var district : String? = null,
  @SerializedName("adcode"   ) var adcode   : Int?    = null

)