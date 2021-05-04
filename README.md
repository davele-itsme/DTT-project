# Android Application - RSR Pechhulp copy
The purpose of the Android project was to make a copy of 

https://play.google.com/store/apps/details?id=com.rsr.android as part of hiring mockup test for https://www.d-tt.nl/.
<br/>


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
Once you have API key, put it inside  `res/values/google_maps_api.xml` file.

### Generating signed APK
From Android Studio:

### Build menu
Generate Signed APK...
Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)

## Side notes

### javax/xml/bind/JAXBException

If you receive this exception:
Go to File -> Project Structure (Shortcut: Ctrl+Alt+Shift+S)

Navigate to SDK Location

From the JDK location dropdown, choose Embedded JDK

### Android version of device

Either use a mobile phone/tablet or virtual device provided by AVD manager with Android version 5.0 Lollipop - 9.0 Pie.
Other versions of Android might not be compatible due to some deprecations.



<!-- INTRODUCTION -->
<br />
<p align="center">
    <img src="https://user-images.githubusercontent.com/42817904/116996726-28124280-acdc-11eb-8ba5-e536b1b6f859.jpg"  height="600">
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
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

Unity game inspired by <strong>Crossy road</strong> game on mobile platforms. Castle road combines <strong>Arcade runner</strong> genre with <strong>RPG</strong> elements.  

<strong>Features:</strong>
* Spawning of enemies attacking you
* Spawning of obstacles when running
* Boss fight at the end of the journey

### Built With

* [Unity](https://unity.com/) - cross-platform game engine
* [MagicaVoxel](https://ephtracy.github.io/) - lightweight GPU-based voxel art editor for modelling
* [Mixamo](https://www.mixamo.com/#/) - library with full-body character animations and autorigging
* [Unity Asset Store](https://assetstore.unity.com/) - store with Unity Assets
* [Sketchfab](https://sketchfab.com/feed) - store with models

<!-- GETTING STARTED -->
## Getting Started

The project is right now in development, therefore to run the game, you need to clone the project and run it locally in Unity.

Publishing the game using WebGL is planned.

### Prerequisites

* [Unity](https://unity.com/) Version 2020.3.5f1

### Installation

1. Clone the repository:
  ```
 gh repo clone davele-itsme/Castle-road
  ```
2. Open the game in Unity
3. Open the scene: `/Assets/Scenes/MainScene.unity`
4. Play the game

