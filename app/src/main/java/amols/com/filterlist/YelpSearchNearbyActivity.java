package amols.com.filterlist;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import java.util.ArrayList;

public class YelpSearchNearbyActivity extends AppCompatActivity
{

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout_loc);

        final EditText searchField = (EditText)findViewById(R.id.txtSearchloc);
        final Button searchButton = (Button)findViewById(R.id.btnSearch);

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String searchTerm = searchField.getText().toString();
                Log.v(TAG, "Searching for: " + searchTerm);

                RestaurantSearchTask task = new RestaurantSearchTask();
                task.execute(searchTerm);
            }
        });

        ArrayList<String> data = new ArrayList<String>();

        //controller
        adapter = new ArrayAdapter<String>(
                this, R.layout.list_item, R.id.txtItem, data);

        //supports ListView or GridView
        AdapterView listView = (AdapterView)findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String business = (String) parent.getItemAtPosition(position);
                Log.d(TAG, "You clicked on " + business);
                if(business.contains("https:"))
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(business));
                    startActivity(browserIntent);

                }


            }
        });
    }

    public class RestaurantSearchTask extends AsyncTask<String, Void, String[]>
    {

        protected String [] doInBackground(String... params)
        {

            String term=params[0];

            StringBuffer buffer = new StringBuffer();

            Bundle extras = getIntent().getExtras();

                String l1 = Double.toString(extras.getDouble("latitude"));
                String l2 = Double.toString(extras.getDouble("longitude"));

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

            for(Business biz : places.getBusinesses())
            {
                buffer.append(biz.getName()+ "\n");
                buffer.append(biz.getUrl()+ "\n");
            }
            String busarr[] = null;
            String res=buffer.toString();
            busarr = res.split("\n");


            return busarr;
        }

        protected void onPostExecute(String[] res)
        {
            if(res != null) {
                adapter.clear();
                for (String movie : res) {
                    adapter.add(movie);
                }
            }
        }
    }

}
