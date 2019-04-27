package model

class Weather {

    public var place:Place? = null
    public var iconData:String = ""
    public var  currentCondition= currentCondition()
    public var temperature = Temperature()
    public var clouds = Clouds()
    public var snow = snow()
    public var  wind = Wind()



}