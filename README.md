# Movies DB


MoviesDb is a video player Android application which fetches videos from online server and display all videos
# Prerequisits

  - Android SDK 28
  - Build tools 28
Install the dependencies and build apk in (Debug environment)
### Dependencies

* [Glide]
* [RetroFit] - for Networking
* [MVVM]
* [Android LifeCycle Extension] - Android ViewModel lifecycle aware &LiveData
* [DataBinding]
* [Paging Livrary]

### Technicality

This application uses MVVM design pattern and has the following Layers

- View
- ViewModel(Using liveData to notify views with data)
- Model 
- DataSource(Responsible for fetching data)

You can find local unit tests for ViewModel and Utils classes



