package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Minutely (

  @SerializedName("status"           ) var status          : String?        = null,
  @SerializedName("datasource"       ) var datasource      : String?        = null,
  @SerializedName("precipitation_2h" ) var precipitation2h : ArrayList<Double> = arrayListOf(),
  @SerializedName("precipitation"    ) var precipitation   : ArrayList<Double> = arrayListOf(),
  @SerializedName("probability"      ) var probability     : ArrayList<Double> = arrayListOf(),
  @SerializedName("description"      ) var description     : String?        = null

)