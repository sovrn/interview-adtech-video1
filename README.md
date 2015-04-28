AdTech Coding Project for the Video Position
===========

To complete this project you will need:

* A local install of the Play framework https://www.playframework.com/download
* Java 1.7 (1.6 and 1.8 may work, but so far we've been using 1.7)
* git

===========

Instructions

* Fork this repository and clone it.
* Download and install the Play Framework
* Run activator in the project directory (Loads project definition and all dependencies)
* Optionally setup your favorite IDE
* Once the activator / sbt console is running compile and run the application via "compile" and "run"
* Open a browser and visit the application at http://localhost:9000

You should see a see a blog w/ 2 ad units on it:
The main view is defined in index.scala.html
The top ad unit is 728x90 (leaderboard) and is identified as zoneid 1
The 300x250 (medium rectangle) on the right is identified as zoneid 2

Both tags setup the following parameters (zoneid, width, height) then run the "async tag"

```
<!-- sovrn test ad unit -->
<script type="text/javascript">
    var sovrn_zoneid = 2;
    var sovrn_width = 300;
    var sovrn_height = 250;
</script>
<script type="text/javascript" src="@routes.Assets.at("javascripts/async-tag.js")"></script>
```

===========

# Async tag instructions

The async tag creates an iframe to make the ad call asynchronous and bootstraps the sync tag w/ the zone/tags
configuration settings

1.) Using the ad tag configuration (zoneid, width, height) create a friendly iframe on the publisher's page
2.) Inside the iframe load a script whose src is adtag.js ( Using document write )
3.) Pass any configuration data to adtag.js

===========

# adtag instructions

The adtag will collect it's configuration properties and some basic information such as the page URL
and frame depth before making a call to the adserver for advertising.

1.) Collect configuration parameters from async-tag.js
2.) Determine frame depth ( How many iframes nested is the tag )
3.) Determine the page URL
4.) Make a JSONP request to adserver and pass along all of the above parameters.
    e.g. http://localhost:9000/addelivery/1/720/90/1/http%3A%2F%2Flocalhost%3A9000%2F

===========

===========

Summary



===========
