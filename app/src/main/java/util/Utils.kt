package util

import org.json.JSONException
import org.json.JSONObject

class Utils {
    companion object {
        val  BASE_URL:String = "http://api.openweathermap.org/data/2.5/weather?q="
        val  ICON_URL:String = "http://api.openweathermap.org/img/w/"

        @Throws(JSONException::class)
        fun getObject(tagName:String, jsonObject:JSONObject):JSONObject{

            var jobj:JSONObject = jsonObject.getJSONObject(tagName)
            return jobj
        }

        @Throws(JSONException::class)
        fun getString(tagName: String,jsonObject: JSONObject):String{

            return jsonObject.getString(tagName)
        }

        @Throws(JSONException::class)
        fun getfloat (tagName: String , jsonObject: JSONObject):Double{
            return jsonObject.getDouble(tagName)
        }

        @Throws(JSONException::class)
        fun getdouble (tagName: String , jsonObject: JSONObject):Double{
            return jsonObject.getDouble(tagName)
        }

        @Throws(JSONException::class)
        fun getInt (tagName: String , jsonObject: JSONObject):Int{
            return jsonObject.getInt(tagName)
        }

    }


}