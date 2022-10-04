package calebzhou.rdi.microservice.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by calebzhou on 2022-10-03,21:14.
 */
class RdiSerializer {
    companion object{
        val gson:Gson = GsonBuilder().serializeNulls().create()
    }

}