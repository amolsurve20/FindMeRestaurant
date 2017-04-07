package amols.com.filterlist;
/**
 * Created by amolsurve on 11/2/16.
 */

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
public class YelpSearch
{
    public static String start() {
        // Define your keys, tokens and secrets.  These are available from the Yelp website.
        String CONSUMER_KEY = "qEPDtFbHvv24_11bsrGoSw";
        String CONSUMER_SECRET = "5nL6GwljMnF29SiMV74eGmx8GIc";
        String TOKEN = "skB1cDm1Ntoy4JzB155BtGwQoWFXXfNW";
        String TOKEN_SECRET = "wfkBz2a1Mj2EFsu3RpnLhydPYmo";



        // Some example values to pass into the Yelp search service.
       String lat = "47.6628";
        String lng = "-122.3139";
        String category = "restaurant";


        // Execute a signed call to the Yelp service.
        OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
        Token accessToken = new Token(TOKEN, TOKEN_SECRET);
        OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
      request.addQuerystringParameter("ll", lat + "," + lng);
        request.addQuerystringParameter("category", category);
        service.signRequest(accessToken, request);
        Response response = request.send();
        String rawData = response.getBody();
        return rawData;



        }

    }



