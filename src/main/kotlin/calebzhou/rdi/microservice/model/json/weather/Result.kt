package calebzhou.rdi.microservice.model.json.weather

import com.google.gson.annotations.SerializedName


data class Result (

  @SerializedName("alert"             ) var alert            : Alert?    = Alert(),
  @SerializedName("realtime"          ) var realtime         : Realtime? = Realtime(),
  @SerializedName("minutely"          ) var minutely         : Minutely? = Minutely(),
  @SerializedName("hourly"            ) var hourly           : Hourly?   = Hourly(),
  @SerializedName("daily"             ) var daily            : Daily?    = Daily(),
  @SerializedName("primary"           ) var primary          : Double?      = null,
  @SerializedName("forecast_keypoint" ) var forecastKeypoint : String?   = null

)