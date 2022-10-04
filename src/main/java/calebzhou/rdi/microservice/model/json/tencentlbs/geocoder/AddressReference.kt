package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class AddressReference (

  @SerializedName("business_area" ) var businessArea : BusinessArea? = BusinessArea(),
  @SerializedName("famous_area"   ) var famousArea   : FamousArea?   = FamousArea(),
  @SerializedName("crossroad"     ) var crossroad    : Crossroad?    = Crossroad(),
  @SerializedName("town"          ) var town         : Town?         = Town(),
  @SerializedName("street_number" ) var streetNumber : StreetNumber? = StreetNumber(),
  @SerializedName("street"        ) var street       : Street?       = Street(),
  @SerializedName("landmark_l2"   ) var landmarkL2   : LandmarkL2?   = LandmarkL2()

)