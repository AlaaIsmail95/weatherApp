package data

import android.app.Activity
import android.content.SharedPreferences

class CityPreference {
    var  pref: SharedPreferences? = null

    constructor(activity:Activity) {
        pref = activity.getPreferences(Activity.MODE_PRIVATE)
    }
    fun getCity():String{
        return pref!!.getString("city","Spokane,US")
    }

    fun setCity(city:String){
        pref!!.edit().putString("city",city).commit()
    }
}