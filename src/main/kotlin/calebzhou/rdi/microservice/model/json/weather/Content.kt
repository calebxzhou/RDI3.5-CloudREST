package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Content (

  @SerializedName("province"       ) var province      : String?           = null,
  @SerializedName("status"         ) var status        : String?           = null,
  @SerializedName("code"           ) var code          : String?           = null,
  @SerializedName("description"    ) var description   : String?           = null,
  @SerializedName("regionId"       ) var regionId      : String?           = null,
  @SerializedName("county"         ) var county        : String?           = null,
  @SerializedName("pubtimestamp"   ) var pubtimestamp  : Double?              = null,
  @SerializedName("latlon"         ) var latlon        : ArrayList<Double> = arrayListOf(),
  @SerializedName("city"           ) var city          : String?           = null,
  @SerializedName("alertId"        ) var alertId       : String?           = null,
  @SerializedName("title"          ) var title         : String?           = null,
  @SerializedName("adcode"         ) var adcode        : String?           = null,
  @SerializedName("source"         ) var source        : String?           = null,
  @SerializedName("location"       ) var location      : String?           = null,
  @SerializedName("request_status" ) var requestStatus : String?           = null

)