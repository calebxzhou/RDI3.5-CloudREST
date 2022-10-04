package calebzhou.rdi.microservice.model.json.tencentlbs.geocoder

import com.google.gson.annotations.SerializedName


data class Result (

  @SerializedName("location"            ) var location           : Location?           = Location(),
  @SerializedName("address"             ) var address            : String?             = null,
  @SerializedName("formatted_addresses" ) var formattedAddresses : FormattedAddresses? = FormattedAddresses(),
  @SerializedName("address_component"   ) var addressComponent   : AddressComponent?   = AddressComponent(),
  @SerializedName("ad_info"             ) var adInfo             : AdInfo?             = AdInfo(),
  @SerializedName("address_reference"   ) var addressReference   : AddressReference?   = AddressReference(),
  @SerializedName("poi_count"           ) var poiCount           : Int?                = null,
  @SerializedName("pois"                ) var pois               : ArrayList<Pois>     = arrayListOf()

)