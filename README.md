<!-- INTRODUCTION -->
<br />
<p align="center">
    <a href="https://play.google.com/store/apps/details?id=com.rsr.android">
    <img src="https://user-images.githubusercontent.com/42817904/116996726-28124280-acdc-11eb-8ba5-e536b1b6f859.jpg"  height="600">
    </a>
  <h2 align="center">RSR Peschhulp Copy</h2>
  <p align="center">
    Android application, which was build as part of hiring mockup test.
    <br />
    <a href="https://play.google.com/store/apps/details?id=com.rsr.android"><strong>Explore the original RSR Peschhulp application»</strong></a>
    <br />
  </p>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
        <li>
      <a href="#side-notes">Side Notes</a>
      <ul>
        <li><a href="#exception">Exception</a></li>
        <li><a href="#Generating-signed-APK">Generating signed APK</a></li>
      </ul>
    </li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

Android application made for [DTT](https://www.d-tt.nl/).

<strong>Features:</strong>
* Google Maps
* Support for different displaying on mobile and tabled devices
* Made using MVP Architecture

### Built With

* [AndroidStudio](https://developer.android.com/studio) -  - official IDE for Google's Android operating system

<!-- GETTING STARTED -->
## Getting Started

The application is not deployed, therefore it is necessary to run the application by cloning the project and building it.

Runs on Android version 6.0 Marshmallow - 9.0 Pie
(Other versions of Android might not be compatible due to some deprecations)

### Prerequisites

* [AndroidStudio](https://developer.android.com/studio)

### Installation

1. Clone the repository:
```
git clone https://github.com/davele-itsme/DTT-project.git
```
2. Open the project in Android Studio
3. The application uses Google Maps Android API. Follow: [Google Maps](https://developers.google.com/maps/documentation/android-sdk/start) to set up the API key.
4. Once you have API key, put it inside  `res/values/google_maps_api.xml` file.
5. Run the application using either mobile phone/tablet or virtual device provided by AVD manager

<!-- SIDE NOTES -->
## Side Notes

### Exception

If you receive this exception "javax/xml/bind/JAXBException"
Go to File -> Project Structure (Shortcut: Ctrl+Alt+Shift+S)

Navigate to SDK Location

From the JDK location dropdown, choose Embedded JDK

### Generating signed APK
From Android Studio:

#### Build menu
Generate Signed APK...
Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)

