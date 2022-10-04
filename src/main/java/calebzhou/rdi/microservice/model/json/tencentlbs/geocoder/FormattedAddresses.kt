package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class FormattedAddresses (

  @SerializedName("recommend" ) var recommend : String? = null,
  @SerializedName("rough"     ) var rough     : String? = null

)