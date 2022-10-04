package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Dressing (

  @SerializedName("date"  ) var date  : String? = null,
  @SerializedName("index" ) var index : String? = null,
  @SerializedName("desc"  ) var desc  : String? = null

)