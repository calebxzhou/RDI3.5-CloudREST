package calebzhou.rdi.microservice.model

import java.sql.Timestamp

/**
 * Created by calebzhou on 2022-10-03,21:09.
 */

/**
 *  地理天气
 */
//经度纬度
class GeoLocation(var latitude:Double,var longitude:Double)
//RDI地址位置
class RdiGeoLocation(var nation: String, var province: String, var city: String,
                     var district: String, var isp: String, var location: GeoLocation)
//ip2region开原地址库的信息
class Ip2RegionData(val nation: String, val province: String, val city: String, val isp: String)
