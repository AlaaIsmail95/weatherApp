package data

import model.Place
import model.Weather
import org.json.JSONArray
import org.json.JSONObject
import util.Utils

class JsonWeatherParser {
    companion object {
        fun getWeather(data: String): Weather? {

            var weather= Weather()

            //Create Json Object from data

            try {

                var jsonobject = JSONObject(data)
                var place = Place()

                var coordObj: JSONObject = Utils.getObject("coord", jsonobject)
                place.lat = Utils.getfloat("lat", coordObj)
                place.lon = Utils.getfloat("lon", coordObj)

                //Get the Sys obj

                var sysObj: JSONObject = Utils.getObject("sys", jsonobject)
                place.country = Utils.getString("country", sysObj)
                place.lastUpdate = Utils.getInt("dt", jsonobject).toLong()
                place.sunRise = Utils.getInt("sunrise", sysObj).toLong()
                place.sunSet = Utils.getInt("sunset", sysObj).toLong()
                place.city = Utils.getString("name",jsonobject)
                weather.place = place

                //Get Weather info

                var jsonArray:JSONArray = jsonobject.getJSONArray("weather")
                var jsonweather:JSONObject = jsonArray.getJSONObject(0)
                weather.currentCondition!!.weatherid = Utils.getInt("id",jsonweather)
                weather.currentCondition!!.description = Utils.getString("description",jsonweather)
                weather.currentCondition!!.condition = Utils.getString("main",jsonweather)
                weather.currentCondition!!.icon = Utils.getString("icon",jsonweather)

                var mainObj:JSONObject = Utils.getObject("main",jsonobject)
                weather.currentCondition.humidity = Utils.getInt("humidity",mainObj)
                weather.currentCondition.pressure = Utils.getdouble("pressure",mainObj)
                weather.currentCondition.minTemp = Utils.getdouble("temp_min",mainObj)
                weather.currentCondition.maxTemp = Utils.getdouble("temp_max",mainObj)
                weather.currentCondition.temperature = Utils.getdouble("temp",mainObj)

                var WindObj:JSONObject =Utils.getObject("wind",jsonobject)
                weather.wind!!.speed = Utils.getfloat("speed",WindObj)
                weather.wind!!.degree = Utils.getfloat("deg",WindObj)


                var cloudObj:JSONObject = Utils.getObject("clouds",jsonobject)
                weather.clouds!!.precipitation = Utils.getInt("all",cloudObj)

                return weather

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}