# Weathery

A Photo Viewer

## APK

[![Weathery](https://i.ibb.co/vP9S7gh/ic-launcher.png "Weathery")](https://we.tl/t-w3kuSvktHB)

## Installation

To successfully run this app using Android Studio, you need:

### An OpenWeatherMap API Key
- Obtainable by creating an acc on [OpenWeatherMap's website](https://home.openweathermap.org/users/sign_up "OpenWeatherMap's Sign up Page")
- Access __My API Keys__ through the __Profile__ menu
- Grab the value in the box under Key

### Create an ```apikey.properties``` file
- Create the file and place it in root folder of the project

### Add you API Key in the created file
- Open up the file in any text editor
- Write the following on the first line copying the exact same letter case
```gradle
OPEN_WEATHER_KEY="YOUR_API_KEY"
```
- Replace ```YOUR_API_KEY``` with the key you retrieved from OpenWeatherMap without removing the quotation marks

### Sync Project with __Gradle__ files from the menu in Android Studio

##### That's it, it should run properly without any problems now!
