package data

import util.Utils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class weatherHttpClient {

    fun getWeatherData(place:String): String? {
        var connection:HttpURLConnection?=null
        var inputStream:InputStream?=null

        try {
            connection =(URL(Utils.BASE_URL+place+"&APPID=177d8566a481fdb514e722d1878dfd2e")).openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.doInput = true
            connection.connect()

            //Read Response

            var stringBuffer = StringBuffer()
            inputStream = connection.inputStream
            var bufferReader:BufferedReader = InputStreamReader(inputStream).buffered()
            var line:String? = null
            line = bufferReader.readLine().toString()
            while (line !=null){
                stringBuffer!!.append(line+" \r \n ")
                line = bufferReader.readLine()
            }
            inputStream.close()
            connection.disconnect()

            return stringBuffer.toString()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }
}