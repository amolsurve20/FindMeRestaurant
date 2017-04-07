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

import java.util.ArrayList;

/**
 * Created by amolsurve on 11/2/16.
 */

public class YelpSearchActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);

        final EditText searchField = (EditText)findViewById(R.id.txtSearch);
        final Button searchButton = (Button)findViewById(R.id.btnSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchField.getText().toString();
                Log.v(TAG, "Searching for: " + searchTerm);

                RestaurantSearchTask task = new RestaurantSearchTask();
                task.execute(searchTerm);
            }
        });

        ArrayList<String> data = new ArrayList<String>(); //empty data; ArrayList so modifiable

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

    public class RestaurantSearchTask extends AsyncTask<String, Void, String[]> {

        protected String [] doInBackground(String... params) {




            String data = YelpSearch.start();

            YelpSearchResult places = new Gson().fromJson(data, YelpSearchResult.class);

            StringBuffer buffer = new StringBuffer();

            for (Business biz : places.getBusinesses())
            {
                buffer.append(biz.getName()+ "\n");
                buffer.append(biz.getUrl()+ "\n");



            }
            String busarr[] = null;
            String res=buffer.toString();
            busarr = res.split("\n");

          /*  for (Location iz : places.getLocations()) {
                for (String address : iz.getAddress()) {
                    System.out.println("  " + address);
                }
                System.out.print("  " + iz.getCity());

                System.out.println();
            }*/
            return busarr;
        }



        protected void onPostExecute(String[] restaurants){
            if(restaurants != null) {
                adapter.clear();
                for (String res : restaurants) {
                    adapter.add(res);
                }
            }
        }
    }
}
