package calebzhou.rdi.microservice.exception

import calebzhou.rdi.microservice.constant.ResponseCode

/**
 * Created by calebzhou on 2022-10-07,22:43.
 */
class RdiResponseCodeException(code: ResponseCode): Exception(code.msg) {

}