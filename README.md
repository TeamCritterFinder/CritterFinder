# Critter Finder

## Overview

The goal of Critter Finder is to help dogs and cats find their forever home.

The problem

7.6 Million companion animals enter shelters every year, of these 2.7 Million are adopted. Unfortunately each year another 2.7 Million Animals don't find a home and are euthanized.   *Source SPCA

The app utilizes [PetFinder REST API](https://www.petfinder.com/developers/api-docs).

## User Stories - Sprint 2

The following **required** functionality is completed:

* [X] All of the Sprint 1 User Stories
* [X] A user should be able to use a swipe mechanic to swipe through a list of pets looking for a home. Swiping right marks the pet as a favorite. Swiping left marks a pet as not a good fit.
* [X] A user should be able to tap on a swipe card from the pet browser view and be shown more details about the pet
* [X] A user should be able to mark pets she is interested in learning more about as favorites and then view her list of favorite pets
* [X] A user should be able to search by breed
* [ ] A user should see a Pet's breed on the swipe card, favorites view and details view
* [ ] Don't resurface pets the user is not interested in seeing
* [X] A user should be able to see contact information about the shelter for a pet
* [ ] A user should be able to reach out to the shelter via e-mail from the details activity
* [ ] The information design should be complete for the swipe card view (a user should be able to see name, age, breed, and size).
* [ ] The information design should be complete for the favorites view (a user should be able to see name, age, breed, and size).
* [ ] The information design should be complete for the details view (a user should be able to see name, age, breed, size, description, contact info)
* [X] A user should be able to see multiple images of the pet from the details screen

The following **optional** features are implemented:
* [X] Allow the user to browse a gallery of pet images for the selected pet from the details screen
* [X] Indicate how many images are available for browsing in the image gallery using a circular view page indicator
* [X] Use scene transitions to animate the transition from a pet in the favorites list to the pet details screen. And from the the swipe card screen to the details screen.
* [X] Give the details screen a transparent action bar and load the first pet image underneath the action bar
* [X] Add a custom slide in from the right and out from the left animation when opening the search filter activity from the swipe card screen and back again.
* [X] We have an app icon!
* [X] First pass at a material color palette / theme

## Video Walkthrough 

Here's a walkthrough of the implemented user stories:

<img src='https://cloud.githubusercontent.com/assets/1521460/13561218/36c67498-e3df-11e5-8c57-da1fad2bec0b.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## User Stories - Sprint 1

The following **required** functionality is completed:

* [X] A user should be able to see a list of neary by pets available for adoption.
* [X] User should see a list of pets and be able to like or not like a particular pet. After taking a like/not interested action, the user should see the next pet in the list
* [X] From the pet browsing view, a user should be able to see a list pets marked as favorites
* [X] Tapping on a pet from the list of favorite pets should show the user more details about the pet.
* [X] User can search for a list of pets by Location, Species, Breed, Gender, Age and Size.
* [X] User should be able to change the search criteria and see a new list of results

The following **optional** features are implemented:
* [X] On first launch, the application uses the GPS to determine the current users location, turns that lat and longitude into a zip code and presents a set of pet results near the user. This was done as an AsyncTask.
* [X] User should be able to see a list of breeds for a particular animal. This list should come from PetFinder
* [X] User should be able to select multiple breeds, ages and sizes and see results that meet that advanced criteria

## Video Walkthrough 

Here's a walkthrough of the implemented user stories:

<img src='https://cloud.githubusercontent.com/assets/1521460/13561218/36c67498-e3df-11e5-8c57-da1fad2bec0b.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## Wireframe 

Here's the wireframe of implemented user stories:
Please click [here](http://htmlpreview.github.io/?https://github.com/TeamCritterFinder/CritterFinder/blob/master/CFWireframe/index.html) to see the wireframe.

Wireframe created with [pencils](http://pencil.evolus.vn).

## Open-source libraries used

- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Material Dialogs](https://github.com/afollestad/material-dialogs) - for implementing parts of the pet search filters UI
- [Permission Dispatcher](https://github.com/hotchemi/PermissionsDispatcher) - for dealing with permissions on Marshmallow
- [Picasso](https://github.com/square/picasso) - Used for async image loading and caching them in memory and on disk.
- [ActiveAndroid](https://github.com/pardom/ActiveAndroid) - Simple ORM for persisting a local SQLite database on the Android device
- [Butterknife](http://jakewharton.github.io/butterknife/) - Remove view binding boilerplate
- [Gson](https://github.com/google/gson) - streamline JSON parsing into models
- [Parceler](https://github.com/johncarl81/parceler) - Remove boilerplate around making model objects parcelable
- [SDK Plugin Manager](https://github.com/JakeWharton/sdk-manager-plugin) - Jake Wharton's SDK plugin manager.
- [View Pager Indicator](http://viewpagerindicator.com/) - Jake Wharton's View pager indicator library for our image gallery
- [Circle Image View](https://github.com/hdodenhof/CircleImageView) - A fast ciruclar image view
- [Swipe Cards](https://github.com/Diolor/Swipecards) - A framework to assist with the swipe / fling animation for pet cards
- 
## License

    Copyright 2016 Carly Baja, Scott Richards, and Scott MacGregor

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
