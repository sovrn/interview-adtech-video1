/*
*
* Instructions:
*  1.) Using the ad tag configuration (zoneid, width, height) create a friendly iframe on the publisher's page
*  2.) Inside the iframe load a script whose src is adtag.js ( Using document write )
*  3.) Pass any configuration data to adtag.js
*/

if(window.console) {
    if(typeof(sovrn_zoneid) != "undefined") {
        console.log("Async call for zoneid " + sovrn_zoneid);
    }
}