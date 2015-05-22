
package sovrn;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

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

    @Resource(name="whitelist")
    private Map<String, Boolean> whitelist;

    /**
     * Takes a domain as a String argument and returns either boolean
     * TRUE if the given domain is on the whitelist or boolean FALSE
     * if the domain is not on the whitelist
     *
     * @param domainInput The domain we are checking for membership on the whitelist
     * @return boolean TRUE if the given domain is on the whitelist, FALSE otherwise
     */

    //assumptions
    //1) abc.com is allowed but abc.com?x=true is not and abc.com/login is not (scrubbed from data before loaded into map)
    public boolean isOnWhitelist(String domainInput) {

        boolean isOnWhiteList = false;

        if(domainInput != null && !domainInput.equals("")) {
            domainInput = domainInput.toLowerCase();
            // see WhitelistManagerTest.testLookUpMethodSpeed() for why I chose this method of iteration
            Iterator<Map.Entry<String, Boolean>> iterator = whitelist.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Boolean> entry = iterator.next();
                String mapDomainName = entry.getKey().trim();
                Boolean allowSubdomains = entry.getValue();
                if (mapDomainName != null && !mapDomainName.isEmpty() && allowSubdomains != null) {
                    // For FALSE only need to look for exact match
                    if (allowSubdomains == Boolean.FALSE) {
                        if (mapDomainName.equalsIgnoreCase(domainInput)) {
                            isOnWhiteList = true;
                            break; // break after found match so iteration stops
                        }
                    // For TRUE need to do REGEX comparison that allows for "zoo.com", "bird.zoo.com",
                    // "eagle.bird.zoo.com", etc. when compared to "zoo.com"
                    } else if (allowSubdomains == Boolean.TRUE) {
                        if (domainInput.indexOf(mapDomainName) != -1) {
                            String str[] = mapDomainName.split("\\.");
                            // abc.com needs to match abc.com and xyz.abc.com and hello.xyz.abc.com but NOT abc.com.net
                            Pattern pattern = Pattern.compile("([a-z0-9]+[.])*" + str[0] + "[.]" + str[1] + "$");
                            //([a-z0-9]+[.])*domain[.]com
                            Matcher matcher = pattern.matcher(domainInput);
                            if (matcher.matches()) {
                                isOnWhiteList = true;
                                break; // break after found match so iteration stops
                            }
                        }
                    }
                }
            }
        }
        return isOnWhiteList;
    }

    public void setWhitelist(Map<String, Boolean> whitelist) {
        this.whitelist = whitelist;
    }

    public Map<String, Boolean> getWhitelist() {
        return whitelist;
    }
}
