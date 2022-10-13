package calebzhou.rdi.microservice.utils

import calebzhou.rdi.microservice.annotation.ExcludeSerialize
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 * Created by calebzhou on 2022-10-03,21:14.
 */
class RdiSerializer {
    companion object{
        private val gsonStrategy = object : ExclusionStrategy{
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.getAnnotation(ExcludeSerialize::class.java)!=null
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }

        }
        val gson:Gson = GsonBuilder().serializeNulls().addSerializationExclusionStrategy(gsonStrategy).create()
    }

}