package calebzhou.rdi.microservice.model

import calebzhou.rdi.microservice.constant.ResponseCode

/**
 * Created by calebzhou on 2022-10-04,18:42.
 */
class ResponseData<T>(val stat:Int, val msg:String, data:T?){
    constructor(responseCode: ResponseCode,data: T?) : this(responseCode.code,responseCode.msg,data)
    constructor(responseCode: ResponseCode) : this(responseCode,null)

    companion object{

    }
}
