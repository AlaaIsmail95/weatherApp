package com.example.alaaismail.weatherapp

import android.graphics.Bitmap
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.squareup.picasso.Picasso
import data.*
import kotlinx.android.synthetic.main.activity_main.*
import model.Weather
import util.Utils
import java.net.HttpURLConnection
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    var weather = Weather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var cityPref = CityPreference(this)
        renderweatherData(cityPref.getCity())
    }

    fun renderweatherData(city: String) {

        var weathertask = weatherTask()
        weathertask.execute((city + "&units=metric"))

    }


    inner class weatherTask : AsyncTask<String, Unit, Weather>() {
        override fun doInBackground(vararg params: String?): Weather? {

            //var data:String = weatherHttpClient().getWeatherData(params[0]!!)
            var data = ((weatherHttpClient()).getWeatherData(params[0]!!))
            weather = JsonWeatherParser.getWeather(data!!)!!


            Log.v("Data", weather.place!!.city)


            return weather
        }

        override fun onPostExecute(result: Weather?) {
            super.onPostExecute(result)

            var df: DateFormat = DateFormat.getTimeInstance()
            var sunriseDate = df.format(Date(weather.place!!.sunRise))
            var sunsetDate = df.format(Date(weather.place!!.sunSet))
            var updateDate = df.format(Date(weather.place!!.lastUpdate))

            var decimalFormat: DecimalFormat = DecimalFormat("#.#")

            var temperatureFormat = decimalFormat.format(weather.currentCondition.temperature)

            cityText.setText(weather.place!!.city + "," + weather.place!!.country)
            tempText.setText("" + temperatureFormat + " °C")
            humidText.setText("Humidity: " + weather.currentCondition.humidity + "%")
            pressureText.setText("Pressure: " + weather.currentCondition.pressure + " hPa")
            windText.setText("Wind: " + weather.wind.speed + " mps")
            riseText.setText("Sunrise: " + sunriseDate)
            SunsetText.setText("Sunset: " + sunsetDate)
            updateText.setText("Last Update: " + updateDate)
            CloudText.setText("Condtion: " + weather.currentCondition.condition + " (" + weather.currentCondition.description + ")")

            Picasso.get().load(Utils.ICON_URL + weather.currentCondition.icon + ".png").into(thumbnailIcon)

        }
    }

    fun showInputDialog(){
        var builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Change City")

        val cityInput:EditText = EditText(this)
        cityInput.inputType = InputType.TYPE_CLASS_TEXT
        cityInput.hint = "Enter Your City"
        builder.setView(cityInput)
        builder.setPositiveButton("Submit") { dialog, which ->
            var cityPreference:CityPreference = CityPreference(this)
            cityPreference.setCity(cityInput.text.toString())
            var newCity =  cityPreference.getCity()
            renderweatherData(newCity)

        }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var id:Int = item!!.itemId
        if (id == R.id.change_cityid){
            showInputDialog()
        }
        return super.onOptionsItemSelected(item)
    }
}
