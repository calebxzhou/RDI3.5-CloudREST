package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Minutely (

  @SerializedName("status"           ) var status          : String?        = null,
  @SerializedName("datasource"       ) var datasource      : String?        = null,
  @SerializedName("precipitation_2h" ) var precipitation2h : ArrayList<Int> = arrayListOf(),
  @SerializedName("precipitation"    ) var precipitation   : ArrayList<Int> = arrayListOf(),
  @SerializedName("probability"      ) var probability     : ArrayList<Int> = arrayListOf(),
  @SerializedName("description"      ) var description     : String?        = null

)