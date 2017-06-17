# WeatherMap

WeatherMap is a showcase app to display weather for user-selected cities using open weather map service. The code follows MVP architecture.
The application consists of a single activity (HomeActivity) & multiple fragments. 
- Fragments communicate using interface callbacks through the host activity so that fragments are unaware of each other.
- Fragments (View) send requests to presenters which communicate with suitable data respositories so that code is modular & adding/replacing/removing repository should be an easy job.
- Application's class "WeatherMapApp" has static methods to provided dependencies. 
- The code contain one sampple unit test

![Code architecture](WeatherMap.png?raw=true "Code architecture")

# Overall features
The application contains the following screens:
- Home screen:
    - Showing a list of locations that the user has bookmarked previously.
    - Show a way to remove locations from the list
    - Add locations by placing a pin on map.
- City screen: once the user clicks on a bookmarked city this screen will appear. On this screen the user will be able to see:
    - Today’s forecast, including: temperature, humidity, rain chances and wind information
    - Show the 5-days forecast, including: temperature, humidity, rain chances and wind information.
- Help screen: The help screen should be done using a webview, and contain information of how to use the app, gestures available if any, etc.
- Settings page: where the user can select some preferences like: unit system (metric/imperial), any other user setting you consider relevant, e.g. reset cities bookmarked.


# What's next?
- Using proper dependency injection 
