package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class StreetNumber (

  @SerializedName("id"        ) var id       : String?   = null,
  @SerializedName("title"     ) var title    : String?   = null,
  @SerializedName("location"  ) var location : Location? = Location(),
  @SerializedName("_distance" ) var Distance : Double?   = null,
  @SerializedName("_dir_desc" ) var DirDesc  : String?   = null

)