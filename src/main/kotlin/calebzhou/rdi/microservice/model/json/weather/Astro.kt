package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Astro (

  @SerializedName("date"    ) var date    : String?  = null,
  @SerializedName("sunrise" ) var sunrise : Sunrise? = Sunrise(),
  @SerializedName("sunset"  ) var sunset  : Sunset?  = Sunset()

)