# Github Users App
<img src="/previews/preview_1.gif" align="right" width="32%"/>

An example of a github users search app by implementing Android Jetpack.

Github Users App is an app to search for github users by using Github REST API v3. 
This app was built by following best practices using Android Jetpack.

**NOTE** This app is in under development and made for experiments with the implementation of the modern tech stack in the future. 
That some changes (such as database schema modifications) are not backwards compatible and may cause the app to crash. In this case, please uninstall and reinstall the app.

## Getting Started

1. Copy url from download or clone button
2. Open Android Studio -> New -> Project from Version Control
3. Use Git as Version Control
4. Paste the url in the URL field
5. Clone and click yes when the dialog box appear
6. Finally you can build the project

### Github Accsess Token

This app uses the [Github REST API v3](https://docs.github.com/en/rest) to load users data into the app. 
To get an access token you can follow this instructions [Creating Personal Access Token](https://docs.github.com/en/rest/overview/other-authentication-methods#basic-authentication).

Once you have the token, add this line to the `gradle.properties` file:

```
github_token=<your Github access token>
```

The app is still usable without an access token, but limited to 60 request per hour. 
See [Rate limiting](https://developer.github.com/v3/#rate-limiting) for details.

## Project Functionality

This app has 3 main screens namely : `SearchUserFragment`, `UserDetailFragment` and `SavedUserFragment`. 

- `SearchUserFragment` Allows you to search Github users. 
Initially only 20 users data will be displayed, but the next 20 data will be displayed by scrolling to the last line displayed on the screen.

- `UserDetailFragment` Contains detailed information about the user such as username, name, bio, number of followers and following.
This fragment has 2 child screens displaying the user's followers and following list which can be swiped right or left to navigate.
In this fragment you can also save the user's data by clicking the love button on the bottom right

- `SavedUserFragment` Contains the list of saved users. This list is stored in a local database. You can remove a saved user by swiping left, 
but you can still undo last removed user by clicking undo on the displayed snackbar.

## Tech Stack & Libraries
- Minimum SDK level 22
- [Kotlin](https://kotlinlang.org/) based, using [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- [Architecture](https://developer.android.com/jetpack/arch/) - A collection of libraries that help you design robust, testable, and
  maintainable apps. Start with classes for managing your UI component lifecycle and handling data
  persistence.
  - [Data Binding](https://developer.android.com/topic/libraries/architecture/navigation/) - Declaratively bind observable data to UI elements.
  - [Navigation](https://developer.android.com/topic/libraries/data-binding/) - Handle everything needed for in-app navigation.
  - [Lifecycles](https://developer.android.com/topic/libraries/architecture/lifecycle/) - Build lifecycle-aware components that can adjust behavior based on the current lifecycle state of an activity or fragment.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata/) - Build data objects that notify views when the underlying database changes.
  - [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) (Alpha) - Load and display pages of data from a larger dataset over network.
  - [Room](https://developer.android.com/topic/libraries/architecture/room/) - Access your app's SQLite database with in-app objects and compile-time checks.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Store UI-related data that isn't destroyed on app rotations. Easily schedule
     asynchronous tasks for optimal execution.
- Third party
  - [Retrofit2 & OkHttp3](https://square.github.io/retrofit/) for REST api communication
  - [Glide](https://bumptech.github.io/glide/) for image loading
