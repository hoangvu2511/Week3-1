﻿# Project 3 -  Twitter Client

**Twitter Client** is an android app that allows a user to displays the home timeline and can compose a tweet. 
Time spent: **32** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can sign in to Twitter using OAuth login.
* [x] User can view the tweets from their home timeline
        User should be displayed the username, name, and body for each tweet. 
        User should be displayed the relative timestamp for each tweet "8m", "7h". 
        User can view more tweets as they scroll with infinite pagination. 
* [x] User can compose a new tweet
        User can click a “Compose” icon in the Action Bar on the top right. 
        User can then enter a new tweet and post this to twitter. 
        User is taken back to home timeline with new tweet visible in timeline. 

The following **optional** features are implemented:

* [x] While composing a tweet, user can see a character counter with characters remaining for tweet out of 140.
* [x] Links in tweets are clickable and will launch the web browser.
* [x] User can refresh tweets timeline by pulling down to refresh (i.e pull-to-refresh) .
* [ ] User can open the twitter app offline and see last loaded tweets
         Tweets are persisted into sqlite and can be displayed from the local DB.
* [x] User can tap a tweet to display a "detailed" view of that tweet .
* [ ] User can select "reply" from detail view to respond to a tweet.
* [x] Improve the user interface and theme the app to feel "twitter branded" .
* [x] Stretch: User can see embedded image media within the tweet detail view.
* [ ] Stretch: User can watch embedded video within the tweet.
* [x] Stretch: Compose activity is replaced with a modal overlay .
* [ ] Stretch: Use Parcelable instead of Serializable using the popular Parceler library.
* [ ] Stretch: Apply the popular Butterknife annotation library to reduce view boilerplate.
* [x] Stretch: Leverage RecyclerView as a replacement for the ListView and ArrayAdapter for all lists of tweets.
* [x] Stretch: Move the "Compose" action to a FloatingActionButton instead of on the AppBar.
* [x] Stretch: Replace all icon drawables and other static image assets with vector drawables where appropriate. 
* [ ] Stretch: Leverage the data binding support module to bind data into one or more activity or fragment layout templates.
* [x] Stretch: Replace Picasso with Glide for more efficient image rendering. 

The following **bonus** features are implemented:

* [x] Use the [ConstraintLayout](https://developer.android.com/reference/android/support/constraint/ConstraintLayout) to display layout.
* [x] Using animation for UI.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='https://imgur.com/aw3rTBv.gif' title='Twitter Client' width='' alt='Video Walkthrough' />

Link to watch: https://i.imgur.com/aw3rTBv.gif

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes


## Open-source libraries used

- [Glide](http://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en) for more efficient image rendering.
- [Retrofit networking library](http://guides.codepath.com/android/Consuming-APIs-with-Retrofit)
- [GSON library](http://guides.codepath.com/android/Using-Android-Async-Http-Client#decoding-with-gson-library) to streamline the parsing of JSON data.


## License

    Copyright 2018 Ginn

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
