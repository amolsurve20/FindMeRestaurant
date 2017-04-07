package amols.com.filterlist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class GroupActivity extends Activity {

    ArrayList<Restaurants> originalValues;
    LayoutInflater inflater;
    int nRestautants = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        final EditText searchBox = (EditText) findViewById(R.id.searchBox);
        ListView restaurantsListView = (ListView) findViewById(android.R.id.list);

        //inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        originalValues = new ArrayList<Restaurants>();

        for (int i = 0; i < nRestautants; i++) {
            // here you initialise the class with your own data
            Restaurants restaurants = new Restaurants(i, "name" + i, "location", R.drawable.ic_action_name);

            originalValues.add(restaurants);
        }

        final CustomAdapter adapter = new CustomAdapter(this, R.layout.restaurants, originalValues);

        restaurantsListView.setAdapter(adapter);
        searchBox.addTextChangedListener(new TextWatcher() {


            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString = searchBox.getText().toString();
                int textLength = searchString.length();

                adapter.clearSearchResult();

                for (int i = 0; i < originalValues.size(); i++) {
                    String restaurantName = originalValues.get(i).getName();
                    if (textLength <= restaurantName.length()) {
                        // compare the String in EditText with Names in the ArrayList

                        if (searchString.equalsIgnoreCase(restaurantName.substring(0, textLength)))
                            adapter.addSeachResult(originalValues.get(i));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });


        restaurantsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch ((int) adapter.getItemId(position)) {
                    case 0:
                        Intent r1 = new    Intent(GroupActivity.this, Restaurant1.class);
                        startActivity(r1);
                        break;
                    case 1:
                        Intent r2 = new    Intent(GroupActivity.this, Restaurant2.class);
                        startActivity(r2);
                        break;
                    default:
                        Intent r3 = new    Intent(GroupActivity.this, NoRestaurant.class);
                        startActivity(r3);
                        break;
                }
            }
        });
    }

}