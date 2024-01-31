# Sample E-commerce App - Coding Challenge

## Introduction
Pixel Shop is sample app built for Coding Challenge with MVVM Architecture using uses the DummyJsone API, and Jetpack Compose, Android Architecture Components using kotlin as programming language.

 ## Screenshots: 
 <img src="assets/Screenshot 1445-07-19 at 12.09.57 AM.png" height=450 width=2100/> 


  # Requirements:
  
- [x] Use Kotlin as the primary programming language.
- [x] Implement MVVM (Model-View-ViewModel) architecture.
- [x] Fetch data asynchronously (Coroutines is preferred).
- [x] Display the retrieved data in a RecyclerView or ListView.
- [x] Navigate to an item details screen once the user clicks on a list item.
- [x] Unit testing (Nice to have but not mandatory).
      
 Bonus point: Showcase how to handle sender and receiver data between 2 devices.
- [x] Try to send some of the received items data model to another android     app on a separate device via Bluetooth or network. 
  Perform some action then send it back again to the sender app.
  Make a toast message to show the received data.


  
   # Features

 - Product List :
    - Displays a paginated list of items fetched from a public API.
  - Detail Page:
     - Users can view detailed information about a selected product.
   - Add To Cart:
     - Allows users to add products to their shopping cart, which is stored locally using Room database.
   -  My Cart Page:
      - Users can view the products they have added to their cart.
     - Search Page:
       - Implements product search functionality to help users find products quickly.
      -  NFC:
         - Implements NFC received items data model from another Android app on a separate device. will receive toast message to show the data is  received data.
    
  ## Tech Stack :

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) -Used to do asynchronous work without blocking the main thread and increasing productivity. 
- [Jetpack Compose for UI](https://developer.android.com/jetpack/compose/) - Android’s recommended modern toolkit for building native UI.
- [MVVM Architecture](https://developer.android.com/jetpack/guide) - The MVVM architecture used to cleanly separate the business and presentation logic of the application, using characteristics of MVVM such as unidirectional data flow, dependency flow, and loose coupling.
 - [Compose Navigation](https://developer.android.com/jetpack/androidx/releases/navigation) - navigate between composables while taking advantage of the Navigation component's infrastructure and features.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library used to access the local SQLite database with object-relational mapper approach.
  - [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt-Dagger library used to use independently of dependencies.
- [Retrofit](https://github.com/square/retrofit) - Used to REST API clients.
- [Coil](https://coil-kt.github.io/coil/compose/) - for loading images with Jetpack Compose.
- [Splash](https://developer.android.com/develop/ui/views/launch/splash-screen#splash-screen-resources) - API Dependency.
-  [Material3](https://m3.material.io/)  Design.
- [Firebase Auth](https://firebase.google.com/docs/auth/android/start) - Firebase Authentication provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to your app.
-  [Desugaring](https://developer.android.com/studio/write/java8-support-table
)  API desugaring allows developers to use more APIs without requiring a minimum API level for your app.
-  [SharedPreferences](https://developer.android.com/training/data-storage/shared-preferences) - Store private primitive data in key-value pairs.
- [Accompanist](https://google.github.io/accompanist/pager/) - Pager layout.
- [NFC](https://developer.android.com/develop/connectivity/nfc/nfc) - provide short-range communication to wirelessly exchange data either between two mobile devices or between a device.




 





   
   
