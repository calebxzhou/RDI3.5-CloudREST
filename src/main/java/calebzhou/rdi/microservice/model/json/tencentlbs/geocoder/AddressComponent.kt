package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class AddressComponent (

  @SerializedName("nation"        ) var nation       : String? = null,
  @SerializedName("province"      ) var province     : String? = null,
  @SerializedName("city"          ) var city         : String? = null,
  @SerializedName("district"      ) var district     : String? = null,
  @SerializedName("street"        ) var street       : String? = null,
  @SerializedName("street_number" ) var streetNumber : String? = null

)