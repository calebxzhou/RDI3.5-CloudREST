package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class LifeIndex (

  @SerializedName("ultraviolet" ) var ultraviolet : ArrayList<Ultraviolet> = arrayListOf(),
  @SerializedName("carWashing"  ) var carWashing  : ArrayList<CarWashing>  = arrayListOf(),
  @SerializedName("dressing"    ) var dressing    : ArrayList<Dressing>    = arrayListOf(),
  @SerializedName("comfort"     ) var comfort     : ArrayList<Comfort>     = arrayListOf(),
  @SerializedName("coldRisk"    ) var coldRisk    : ArrayList<ColdRisk>    = arrayListOf()

)