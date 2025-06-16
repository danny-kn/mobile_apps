# Find Your Recipe - Kotlin Mobile Application

## Backend Architecture & Infrastructure

### Backend Containers Running
![Backend Containers Running](docs/Backend%20Containers%20Running.png)

### Backend Schema with Progress Tracking
![Backend Schema with Progress Marked](docs/Backend%20Schema%20with%20progress%20marked.png)

### All Application Screenshots
<div style="display: flex; flex-wrap: wrap; gap: 10px;">

<img src="docs/img/Discover Fragment.png" alt="Discover Fragment" width="200"/>
<img src="docs/img/Loading Screens.png" alt="Loading Screens" width="200"/>
<img src="docs/img/Recipe Detail Activity.png" alt="Recipe Detail Activity" width="200"/>
<img src="docs/img/Search Fragment.png" alt="Search Fragment" width="200"/>
<img src="docs/img/Search Fragment With Query.png" alt="Search Fragment With Query" width="200"/>
<img src="docs/img/Trending Fragment.png" alt="Trending Fragment" width="200"/>

</div>

<div style="display: flex; flex-wrap: wrap; gap: 10px;">

<img src="docs/img/Screenshot from 2025-03-31 18-13-20.png" alt="Sign In Screen" width="300"/>
<img src="docs/img/Screenshot from 2025-03-31 18-13-23.png" alt="Sign Up Screen" width="300"/>
<img src="docs/img/Screenshot from 2025-03-31 18-13-35.png" alt="User Profile" width="300"/>

</div>

---

# Checkpoint 3: Installing, Debugging, Profiling, Application Lifecycle Management, and Logging

__Date of Submission:__ February 21, 2025

This checkpoint involves demonstrating your ability to do the following:
- Install your application on a device.
- Debug your application (e.g., step through code and set breakpoints).
- Profile your application using one or more profilers (e.g., Android Studio's CPU or memory profilers, or Instruments for iOS).
- Log the application's lifecycle methods. Here, you must implement at least one or two screens (i.e., one `Activity` and two `Fragment`s) in your application and show, via the Android log, that you trigger the application's lifecycle methods (e.g., `onCreate()`, `onStart()`, `onResume()`, `onPause()`, `onStop()`, and `onDestroy()` in the `Activity`; these six methods plus `onCreateView()` and `onDestroyView()` in each `Fragment`).

If you use declarative UI frameworks (such as Jetpack Compose or SwiftUI), include log messages in your composables to see the application's lifecycle.

__Points:__ 10/70

__What to Submit:__

- __Via OneDrive (8/10 points):__
  - A screenshot of at least one type of profiler (e.g., CPU or memory footprint in Android Studio or Xcode).
  - Screenshots of the application's lifecycle methods being triggered (specifically, of Android Logcat or Xcode's console showing lifecycle methods that you trigger).
  - Screenshots of debugging your application in Android Studio or Xcode (specifically, setting a breakpoint, viewing variables' states, and stepping through code). Your application project as a `tar.gz` or a `.zip` file. __Via Carmen: Teamwork Contribution (2/10 points):__ Each team member should submit a short paragraph via Carmen describing how everyone worked during the project for this checkpoint. No one likes working with "slackers" who do little work, leaving the entire project to the remaining team members. If the grader or the instructor determines, in their professional opinions, that certain team members are not contributing fairly, they reserve the right to deduct some or all of the Teamwork Contribution points for these team members. Your paragraph will help the grader and instructor make their decisions. (This __need not__ be a fancy Word or PDF document; a few sentences suffice.). If everyone contributes equally, all team members will receive these points. __Show to Grader:__
- A mobile device running a recent version of Android or iOS (i.e., Android 8+ or iOS 15+).
- Installing your application on the device.
- Debugging your application (setting breakpoints and stepping through code).
- Profiling your application.
- Invoking lifecycle methods and logging them.
- Also, show the grader your teamwork contribution writing.

__Evaluation Criteria:__
- Students show that they understand how to use the IDE.
- Students demonstrate comprehension of the application lifecycle in Android or iOS.
- Students' teamwork contributions (explained above).

__Purchase Advice:__ If your team only has iPhones and you are writing Android applications, I recommend buying an inexpensive phone such as a Nokia 6.1 or a Moto G for testing. (Check out [Wirecutter's guide to budget Android phones](https://www.nytimes.com/wirecutter/reviews/best-budget-android-phone/) for more details.) If you are unable to find an Android phone, talk to me.

# Checkpoint 4: Review of Application Architecture and Data Persistence

__Date of Submission:__ March 7, 2025

For this checkpoint, you need to show that you have setup your application's persistent data storage (either on the mobile device or using a cloud service such as Firebase). Your application should be able to perform data creation, retrieval, update, and deletion (CRUD) operations with your data store.

__Note:__ If you use a NoSQL external service like Firebase, you ___need___ to denormalize the relational data model that you developed for Checkpoint 2. Make sure that you include screenshots of your "JSON tree" or "document collection" if you use Firebase Realtime Database or Cloud Firestore, respectively, for cloud data storage.

Your team ___must___ establish a version control system for your project's source code (e.g., a Github repository). Though incomplete, your code must adhere to object-oriented design principles such as the single responsibility principle and separation of concerns. At the very least, this entails ___consistent naming___ for all `Activities`, `Fragment`s (such as `LoginActivity` and `LoginFragment`). Please separate UI-layer code from ___model-layer___ classes (such as Account) so each type of code is placed in a separate Java or Kotlin package (e.g., `yourapp.ui` and `yourapp.model`).

You ___do not___ need to implement all use cases for your functional requirements, but you ___must___ show evidence of progress toward this goal.

__Points:__ 10/70

__What to Submit:__

- __Via OneDrive (8/10 points):__
  - Screenshots of your application's data storage schema (such as your applications’s `SQLiteOpenHelper` class for on-device storage, your `ViewModel`s and `LiveData`, or your schemata for external NoSQL services like Firebase Realtime Database).
  - Screenshots of your application’s class organization (e.g., Android Studio’s “Project” pane).
  - Screenshots of a version control repository (such as GitHub) for your application’s source code.
  - All your code (just export the project as a `.zip` or `.tar.gz` file).
- __Via Carmen: Teamwork Contribution (2/10 points):__ Each team member should submit a short paragraph via Carmen describing how everyone in the team worked during the project for this checkpoint. No one likes working with "slackers" who do little work, leaving the entire project to the remaining team members. If the grader or the instructor determine, in their professional opinions, that certain team members are not contributing fairly, they reserve the right to deduct some or all of the Teamwork Contribution points for these team members. Your paragraph will help the grader and instructor make their decisions. (This ___need not___ be a fancy Word or PDF document; a few sentences suffice.) If everyone on the team contributes equally, then all team members will receive these points.

__Show to Grader:__
- Your application’s data storage schema (as described previously).
- Your application’s ability to perform creation, retrieval, updates, and deletions (CRUD) with your data store (an on-device relational database or an external service such as Firebase).
- The version control repository for your application.
- The layout of your application’s classes.
- Your teamwork contribution writing (or few sentences).

__Evaluation Criteria:__
- Students demonstrate implementation of their application’s data storage schemata (using a relational database or NoSQL data store).
- Students demonstrate that their app can perform CRUD operations on stored data.
- Students demonstrate use of a version control system.
- Students demonstrate that their application implemented cohesive, loosely-coupled classes.
- Students' teamwork contributions.

# Checkpoint 5: Functional Demonstration of Your Application

__Date of Submission:__ March 31, 2025

For this checkpoint, you have to demonstrate a functioning application. The application should work from beginning to end, but you __need not__ optimize it for performance or resilience to failures (such as losing network connectivity or GPS reception).

__Points:__ 20/70

__What to Submit:__

- __Via OneDrive (16/20 points):__
  - A list of use cases (__only__ a list, no descriptions).
  - Screenshots of your application running.
  - All code (simply export the project as a `.tar.gz` or `.zip` file).
- __Via Carmen: Teamwork Contribution (4/20 points):__ Each team member should submit a short paragraph via Carmen describing how everyone in the team worked during the project for this checkpoint. No one likes working with "slackers" who do little work, leaving the entire project to the remaining team members. If the grader or the instructor determine, in their professional opinions, that certain team members are not contributing fairly, they reserve the right to deduct some or all of the Teamwork Contribution points for these team members. Your paragraph will help the grader and instructor make their decisions. (This __need not__ be a fancy Word or PDF document; a few sentences suffice.) If everyone on the team contributes equally, then all team members will receive these points.

__Show to Grader:__
- Your application working on a __real mobile device__. (If it works only on an emulator, you will only receive partial credit).
- Your teamwork contribution writing (just a few sentences).

__Evaluation Criteria:__ TA will check off working use cases, along with your teamwork contribution.

# Checkpoint 6: Demonstration of Your Application's Non-Functional Aspects

__Date of Submission:__ April 14, 2025

This checkpoint entails showing your application's non-functional capabilities such as usability, performance, availability, maintainability, modifiability, and scalability. The applications should work from beginning to end, should be optimized for performance, and be resilient to failures (such as loss of network connectivity and GPS signal reception, screen rotation, and termination by the OS). Non-functional requirements (NFRs) should be app-specific and (ideally) quantified.

The minimum requirements are as follows:

- Address at leas one performance NFR (e.g., reduction CPU or memory use) and show improvements using "before" and "after" profiler snapshots. You should show this reduction for the __same part of the application__ (e.g., a particular `Activity` or `Fragment`). Try to reduce CPU and memory use by __at least__ 5-10%. Lazy evaluation of objects, data pagination [e.g., showing only ten objects per screen ("page")], and Kotlin coroutines should increase your application's speed and responsiveness.

- Address at least one other NFR (e.g., increased application security, design enhancements, and accessibility support for users with disabilities, or localization in another language).

- Perform three unit tests using an Android or iOS test framework. (e.g., JUnit or Xcode's `XCUnitTest`s).

- Perform three instrumented UI tests using a framework (such as Espresso or the iOS equivalent).

__Points:__ 20/70

__What to Submit;__

- __Via OneDrive (16/20 points):

  - List of use cases and non-functional requirements met.

  - Screenshots of the application.

  - Profiler screenshots showing areas where performance was improved as well as the "baseline" performance before improvement (i.e., "before" and "after" screenshots of CPU, GPU, or memory consumption showing decreased consumption).

  - All code (simply export the project as a `.zip` file).

- __Via Carmen: Teamwork Contribution (4/20 points):__ Each team member should submit a short paragraph via Carmen describing how everyone in the team worked during the project for this checkpoint. No one likes working with "slacker" who do little work, leaving the entire project to the remaining team members. If the grader or the instructor determine, in their professional opinions, that certain team members are not contributing fairly, they reserve the right to deduct some or all of the Teamwork Contribution points for these team members. Your paragraph will help the grader and instructor make their decisions. (This __need not__ be a fancy Word or PDF document; a few sentences suffice.) If everyone on the team contributes equally, then all team members will receive these points.

__Show to Grader:__

- The working application on a device demonstrated to work under failures (network connectivity, GPS, screen rotation).

- Three JUnit or Xcode unit tests working on your mobile device.

- Three instrumented UI tests working on your device.

- Your teamwork contribution writing (just a few sentences).

__Evaluation Criteria:__ Number and quality of working use cases and NFRs met, along with your teamwork contribution.

Check out the [Android developer guide for testing](https://developer.android.com/training/testing) for more information, along with Jorge M.F.'s Medium post on [making your application faster](https://medium.com/@jorgemf/making-your-android-app-faster-735328eaba25) and Weidian Huang's Medium post on [avoiding memory leaks](https://weidianhuang.medium.com/how-to-prevent-common-memory-leaks-inside-android-fragment-c243ed7074d6). Also look up Robert Martin's books _Clean Code_ and _Clean Craftsmanship_ for information on (unit) test design. (Type the titles into [Ohio State's Libraries](https://library.osu.edu/) search bar).
