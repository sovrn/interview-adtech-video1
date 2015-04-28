package util;

import java.util.Map;

/**
 * A simple class to check for membership on a whitelist
 *
 * In order to help reduce fraud, Sovrn implements the concept of a whitelist.
 * That is, for Sovrn to deliver an ad to a given web site, that site must
 * be on a whitelist. Your job for this test will be to implement a class that
 * determines whether or not a given site is on the whitelist.
 *
 * Code your solution as if it were a real project you would be executing as part of our team.
 * That is efficiency, clarity, documentation, testing, etc. should all be taken into consideration.
 * You should use the following skeleton code as your starting point, but feel free to modify
 * the skeleton if you feel you have a better solution (use comments to explain why you think your solution is better).
 */
public class WhitelistManager {

    /**
     * Assume that the whitelist is held within the following Map.
     * The keys of the Map are the domains on the whitelist and
     * the Boolean values are whether or not subdomains of the domain
     * should also be whitelisted. For example, a whitelist entry
     * of <"car.com", FALSE> matches ONLY the domain of "car.com".
     * A whitelist entry of <"zoo.com", TRUE> would match "zoo.com",
     * "bird.zoo.com", "eagle.bird.zoo.com", etc.
     *
     * Bonus Points - For the purposes of this code test, use dependency injection to
     * supply the whitelist to this class.
     */
    private Map<String, Boolean> whitelist;

    /**
     * Takes a domain as a String argument and returns either boolean
     * TRUE if the given domain is on the whitelist or boolean FALSE
     * if the domain is not on the whitelist
     *
     * @param domain The domain we are checking for membership on the whitelist
     * @return boolean TRUE if the given domain is on the whitelist, FALSE otherwise
     */
    public boolean isOnWhitelist(String domain) {
        // TODO: Implement Me!
        return false;
    }
}
