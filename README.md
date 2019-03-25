# BioWear

BioWear is an IoT based remote health monitoring system which has sensors embedded within a jacket.
The sensors read data from LM-35 temperature sensor, pulse rate sensor and an ADXL345 accelerometer. This data is uploaded to Firebase cloud via an ESP8266 module interfaced with an Arduino Lilypad.

The companion Android app has been written in Kotlin and uses AndroidX artifacts. It will run on all devices running Android M and above.

#### Android Libraries used
* [Glide](https://github.com/bumptech/glide) for loading images and GIFs
* [Circle Progress Indicator](https://github.com/CardinalNow/Android-CircleProgressIndicator) for circular progress bar
* [AnyChart](https://github.com/AnyChart/AnyChart-Android) for data visualization as graphs and charts
* [Gson](https://github.com/google/gson) for POJO-JSON serialization/deserialization
* [Timber](https://github.com/JakeWharton/timber) for logging
* [Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker) for material design date & time selection
* [InflationX](https://github.com/InflationX/Calligraphy) for loading custom fonts