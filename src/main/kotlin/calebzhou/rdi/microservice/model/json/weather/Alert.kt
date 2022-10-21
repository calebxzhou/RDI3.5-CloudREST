package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Alert (

  @SerializedName("status"  ) var status  : String?            = null,
  @SerializedName("content" ) var content : ArrayList<Content> = arrayListOf()

)