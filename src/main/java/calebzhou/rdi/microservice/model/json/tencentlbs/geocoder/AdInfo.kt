package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class AdInfo (

  @SerializedName("adcode"   ) var adcode   : String? = null,
  @SerializedName("province" ) var province : String? = null,
  @SerializedName("city"     ) var city     : String? = null,
  @SerializedName("district" ) var district : String? = null

)