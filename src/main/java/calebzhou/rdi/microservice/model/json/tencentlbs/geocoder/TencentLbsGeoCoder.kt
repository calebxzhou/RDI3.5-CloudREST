package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class TencentLbsGeoCoder (

  @SerializedName("status"     ) var status    : Int?    = null,
  @SerializedName("message"    ) var message   : String? = null,
  @SerializedName("request_id" ) var requestId : String? = null,
  @SerializedName("result"     ) var result    : Result? = Result()

)