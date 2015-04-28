package controllers;


import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.Jsonp;
import play.mvc.Controller;
import play.mvc.Result;
import util.WhitelistManager;

public class Adserver extends Controller {
    public static Result addelivery(long zoneid, int width, int height, int framedepth, String loc) {
        WhitelistManager wlm = new WhitelistManager();

        ObjectNode node = Json.newObject();
        node.put("zoneid", zoneid);
        node.put("isWhitelisted", wlm.isOnWhitelist(loc));

        return ok(Jsonp.jsonp("renderads", node));
    }
}
