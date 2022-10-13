package calebzhou.rdi.microservice.component

import calebzhou.rdi.microservice.constant.ResponseCode
import calebzhou.rdi.microservice.model.ResponseData
import calebzhou.rdi.microservice.utils.IpGeoUtils.Companion.getClientIP
import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import javax.servlet.FilterChain
import javax.servlet.http.HttpFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by calebzhou on 2022-10-10,11:28.
 */
@Component
class RateLimitFilter: HttpFilter() {
    private val MaxRequestsPerMinute = 10 //or whatever you want it to be
    private var requestCountsPerIpAddress: LoadingCache<String, Int> = Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build { 0 }

    override fun doFilter(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        if (isMaximumRequestsPerSecondExceeded(getClientIP(req))) {
            res.status = HttpStatus.TOO_MANY_REQUESTS.value()
            res.writer.println(ResponseData(ResponseCode.TooManyRequests,null))
            return
        }
        chain.doFilter(req,res)
    }
    private fun isMaximumRequestsPerSecondExceeded(clientIpAddress: String): Boolean {
        var requests = 0
        requests = requestCountsPerIpAddress[clientIpAddress]
        if (requests != null) {
            if (requests > MaxRequestsPerMinute) {
                requestCountsPerIpAddress.asMap().remove(clientIpAddress)
                requestCountsPerIpAddress.put(clientIpAddress, requests)
                return true
            }
        } else {
            requests = 0
        }
        requests++
        requestCountsPerIpAddress.put(clientIpAddress, requests)
        return false
    }


}