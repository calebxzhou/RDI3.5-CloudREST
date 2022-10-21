package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class AirQuality (

  @SerializedName("aqi"  ) var aqi  : ArrayList<Aqi>  = arrayListOf(),
  @SerializedName("pm25" ) var pm25 : ArrayList<Pm25> = arrayListOf()

)