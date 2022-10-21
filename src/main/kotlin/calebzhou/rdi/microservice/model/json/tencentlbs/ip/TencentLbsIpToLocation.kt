package calebzhou.rdi.microservice.model.json.tencentlbs.ip

import com.google.gson.annotations.SerializedName


data class TencentLbsIpToLocation (

  @SerializedName("status"  ) var status  : Int?    = null,
  @SerializedName("message" ) var message : String? = null,
  @SerializedName("result"  ) var result  : Result? = Result()

)