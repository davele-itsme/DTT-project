# Android Application - RSR Pechhulp copy
The purpose of the Android project was to make a copy of 

https://play.google.com/store/apps/details?id=com.rsr.android as part of hiring mockup test for https://www.d-tt.nl/.
<br/>
<img src="https://user-images.githubusercontent.com/42817904/116996726-28124280-acdc-11eb-8ba5-e536b1b6f859.jpg"  height="800">

## Content
The code is located in DTT-project/app/src/main.

The application was built using MVP architecture. The application supports different version for displaying on tablet and a different version on mobile phone.

## Installation
Clone this repository and import into Android Studio

```
$ gh repo clone davele-itsme/DTT-project
```


## Configuration
The application uses Google Maps Android API.
Follow: https://developers.google.com/maps/documentation/android-sdk/start to set up the API key.
Once you have API key, put it inside  `res/values/google_maps_api.xml` files.

### javax/xml/bind/JAXBException

If you receive this exception:
Go to File -> Project Structure (Shortcut: Ctrl+Alt+Shift+S)

Navigate to SDK Location

From the JDK location dropdown, choose Embedded JDK

### Generating signed APK
From Android Studio:

### Build menu
Generate Signed APK...
Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)
