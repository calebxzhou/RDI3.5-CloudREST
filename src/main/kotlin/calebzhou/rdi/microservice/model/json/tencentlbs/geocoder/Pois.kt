package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class Pois (

  @SerializedName("id"        ) var id       : String?   = null,
  @SerializedName("title"     ) var title    : String?   = null,
  @SerializedName("address"   ) var address  : String?   = null,
  @SerializedName("category"  ) var category : String?   = null,
  @SerializedName("location"  ) var location : Location? = Location(),
  @SerializedName("ad_info"   ) var adInfo   : AdInfo?   = AdInfo(),
  @SerializedName("_distance" ) var Distance : Int?      = null,
  @SerializedName("_dir_desc" ) var DirDesc  : String?   = null

)