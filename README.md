# Enterprise search using speech via PocketSphinx

An Android application developed with voice recognition with the help of a language model for an option to search on an online website. A language model is generated by a large set of data and the dictionary with phonetic symbols. To generate a language model, the CMU SLM Language toolkit is used. CMU SLM toolkit is designed to facilitate the language modeling work research community. This is used to process the textual data into word frequency lists and vocabularies. Along with the CMU SLM toolkit, another tool is PocketSphinx, which is also a part of the CMU Sphinx Open Source Toolkit for Speech Recognition. It is an iterator for continuous recognition or keyword search from a microphone.

## System Requirement:
### For generating a Language model:
* To install on Ubuntu (or any other unix-like system), we first need to install a few dependencies.

  * ``` sudo apt-get install gcc automake autoconf libtool bison swig python-dev libpulse-dev```

* For using pocketsphinx or sphinx4, we to need to install sphinxbase first.
  * ```git clone https://github.com/cmusphinx/sphinxbase.git```
  * ```cd sphinxbase/make```
  * ```sudo make install```
* For installing pocketsphinx:
  * ```git clone https://github.com/cmusphinx/pocketsphinx.git```
  * ```cd pocketsphinx/make```
  * ```sudo make install```
### For developing an Android Application:
* Windows version: Microsoft Windows 7/8/10 (64-bit)
* IDE: Android Studio
* RAM: 4 GB RAM minimum, 8 GB RAM recommended.
* Size: 2 GB of available disk space minimum, 4 GB Recommended (500 MB for IDE + 1.5 GB for Android SDK and emulator system image)
* Resolution: 1280 x 800 minimum screen resolution.
### Deployment Requirement
* Android Version: 8 or higher

## To run the existing code:
* Open Android Studio and select Open an Existing Android Studio Project or File, Open. Locate the folder you downloaded from Dropsource and unzipped, choosing the “build.gradle” file in the root directory.
Android Studio will import the project. You may see warnings or errors regarding SDK and Android versions – if so click the links in the messages to set up your installation of Android Studio with the required versions and components.
* Select Project on the left side to view and explore the files in your app.
* Open the “res/layout” directory to access the UI configuration for the app, including pages and other layout components. Select an XML file with the name of one of the pages you created in Dropsource to view it graphically. Select the Text tab to view the XML markup code for your layout.
* In the “java/&lt;package-name&gt;/activities” directory you will find the Java programming code for each of your app pages, with additional functionality defined in the other Java files in the directory.
* Click the Run button to run the app – if you have not already created an Android emulator, Android Studio will prompt you to do so at this point. Once you have an emulator, select it to launch your app on it.
* Android Studio will run your app on the emulator.
