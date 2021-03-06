# AdTech Coding Project for the Video Position

In this project you'll be putting together a simple ad workflow.  The workflow starts off with async tags
on a fake blog.  The async tags will need to create a friendly iframe and put the full ad tag into the friendly iframe.
The adtag code then collects the page url / loc and frame_depth and passes it into the adserver's addelivery method.  The
addelivery method then checks the page url / loc against a whitelist and returns a jsonp response.  The adtag code
should define a callback for this method and write the results to the ad tag.

===========

# Prerequisites

To complete this project you will need:

* A local install of the Play framework https://www.playframework.com/download
* Java 1.7 (1.6 and 1.8 may work, but so far we've been using 1.7)
* git

# Instructions

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


===========

# Async tag instructions

File: public/javascripts/async-tag.js

There are two async tags on the page already ( outlined w/ the grey border )

The tags setup the following parameters (zoneid, width, height) then run the "async tag" via the script element below.

```
<!-- sovrn test ad unit -->
<script type="text/javascript">
    var sovrn_zoneid = 2;
    var sovrn_width = 300;
    var sovrn_height = 250;
</script>
<script type="text/javascript" src="@routes.Assets.at("javascripts/async-tag.js")"></script>
```

If you look at your console log you'll see the following:

```
Async call for zoneid 1
Async call for zoneid 2
```

Those msgs are from the skeleton async tag running.

You will need to complete the async tag (async-tag.js) by creating an iframe to make the ad call asynchronous and
bootstrap the sync tag w/ the zone/tags configuration settings.

* Using the ad tag configuration (zoneid, width, height) create a friendly iframe on the publisher's page
* Inside the iframe load a script element whose src is adtag.js ( Using document write )
* Pass any configuration data to adtag.js so that it can use the params

===========

# Adtag instructions

File: public/javascripts/adtag.js

The adtag will collect it's configuration properties and some basic information such as the page URL
and frame depth before making a call to the "adserver".

* Collect configuration parameters from the async-tag.js
* BONUS - Determine frame depth ( How many iframes nested is the tag )
* Determine the page URL
* Make a JSONP request to adserver and pass along all of the above parameters:

Here's an example of the URL you will be using:

http://localhost:9000/addelivery/1/720/90/1/http%3A%2F%2Flocalhost%3A9000%2F

If you look at the conf/routes file you'll see that URL contains the following parameters
```
/addelivery/:zoneid/:width/:height/:framedepth/:loc
```

If you go the above URL you'll see:
```
renderads({"zoneid":1,"isWhitelisted":false});
```

The page url / loc that you determine in Javascript should be encoded and passed into the back-end addelivery endpoint.
Note: The back-end handler always returns false for now.  You will be filling the class out to check against a real
whitelist.

* In the adtag.js code you will also need to define a method called "renderads" for the callback and write
the loc and the results of isWhitelisted into the tag's div.  You can use simple span or other inline elements.

===========

# WhitelistManager

File: app/util/WhitelistManager.java

In app/util/WhitelistManager there is a simple class to check a loc/url for membership on a whitelist

> In order to help reduce fraud, Sovrn implements the concept of a whitelist.
> That is, for Sovrn to deliver an ad to a given web site, that site must
> be on a whitelist. Your job for this test will be to implement a class that
> determines whether or not a given site is on the whitelist.

> Code your solution as if it were a real project you would be executing as part of our team.
> That is efficiency, clarity, documentation, testing, etc. should all be taken into consideration.
> You should use the following skeleton code as your starting point, but feel free to modify
> the skeleton if you feel you have a better solution (use comments to explain why you think your solution is better).

The addelivery handler uses the isOnWhitelist method, passing in the loc from the front-end javascript code.  The
result is part of the jsonp response.

===========

