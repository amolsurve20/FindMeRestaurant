package amols.com.filterlist;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;

public class DisplayIndianActivity extends AppCompatActivity
{
    private ArrayAdapter<String> adapter;
    private static final String TAG = "MainActivity";

    String l1, l2, term;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_indian);
        Bundle extras = getIntent().getExtras();
        l1 = Double.toString(extras.getDouble("latitude"));
        l2 = Double.toString(extras.getDouble("longitude"));
        term = "Indian";

        RestaurantSearchTask task = new RestaurantSearchTask();
        task.execute(term);

        ArrayList<String> data = new ArrayList<String>(); //empty data; ArrayList so modifiable

        //controller
        adapter = new ArrayAdapter<String>(
                this, R.layout.list_item, R.id.txtItem, data);

        //supports ListView or GridView
        AdapterView listView = (AdapterView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String business = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "You clicked on " + business);
                if (business.contains("https:")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(business));
                    startActivity(browserIntent);

                }


            }
        });
    }


    public class RestaurantSearchTask extends AsyncTask<String, Void, String[]> {

        protected String[] doInBackground(String... params) {

            String term = params[0];

            StringBuffer buffer = new StringBuffer();


            // Define your keys, tokens and secrets.  These are available from the Yelp website.
            String CONSUMER_KEY = "qEPDtFbHvv24_11bsrGoSw";
            String CONSUMER_SECRET = "5nL6GwljMnF29SiMV74eGmx8GIc";
            String TOKEN = "skB1cDm1Ntoy4JzB155BtGwQoWFXXfNW";
            String TOKEN_SECRET = "wfkBz2a1Mj2EFsu3RpnLhydPYmo";

            // Execute a signed call to the Yelp service.
            OAuthService service = new ServiceBuilder().provider(YelpV2API.class).apiKey(CONSUMER_KEY).apiSecret(CONSUMER_SECRET).build();
            Token accessToken = new Token(TOKEN, TOKEN_SECRET);
            OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
            request.addQuerystringParameter("ll", l1 + "," + l2);
            request.addQuerystringParameter("term", term);
            service.signRequest(accessToken, request);
            Response response = request.send();
            String rawData = response.getBody();

            YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);

            for (Business biz : places.getBusinesses()) {
                buffer.append(biz.getName() + "\n");
                String url = biz.getUrl();

                // String someVariable = "testUrl";

                // Html entryLink=new Html("<a target=\"_blank\" href=\"" + url + "\">" + someVariable + "</a>");
                buffer.append(url + "\n");
            }

            String busarr[] = null;
            String res = buffer.toString();
            busarr = res.split("\n");

            return busarr;
        }

        protected void onPostExecute(String[] movies) {
            if (movies != null) {
                adapter.clear();
                for (String movie : movies) {
                    adapter.add(movie);
                }
            }
        }
    }
}










