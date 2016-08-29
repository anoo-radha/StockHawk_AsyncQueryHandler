## StockHawk
Android Application for stock details.
StockHawk application for Android with a collection widget. The stock information is updated every hour(using GCMTaskService). 

I have implemented this app using AsyncQueryHandler for background communication to content provider and API.
The same app has been iplemented using IntentService [link](https://github.com/anoo-radha/StockHawk_App)

Executed the improvements from User Feedback!
#### Required Components

* Each stock quote on the main screen is clickable and leads to a new screen which graphs the stock’s value over time.
* Stock Hawk does not crash when a user searches for a non-existent stock.
* Stock Hawk Stocks can be displayed in a collection widget.
* Stock Hawk app has content descriptions for all buttons.
* Stock Hawk app supports layout mirroring using both the LTR attribute and the start/end tags.
* Strings are all included in the strings.xml file and untranslatable strings have a translatable tag marked to false.
* Stock Hawk displays a default text on screen when offline, to inform users that the list is empty or out of date.

*Implements:* AsyncQueryHandler for background communication to content provider and API;  Share Action Provider; GCMTaskService;  RecyclerView;  AppWidgetProvider;  CursorAdapter;  AppBarLayout;  SharedPreferences;  RTL;  Accessibility; [Schematic library](https://github.com/SimonVT/schematic) for creating ContentProvider; FloatingActionButton; [Stetho](http://facebook.github.io/stetho/")  
This is an assignment for udacity AND-P3:Advanced Android Development.

####How to Run the Application:
This app can be run on Android Studio.

1. Download the .zip file from the github.
2. Extract the .zip file’s contents to a convenient location on your hard disk.
3. In the Android Studio, click on File->New->Import Project and select the unzipped project folder.
  Now all of the project files will be displayed in the project structure. Press the ‘Run’ button and choose the running device to run the application.
