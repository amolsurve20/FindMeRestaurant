package amols.com.filterlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

//import java.security.acl.Group;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1=(Button) findViewById(R.id.ListButton);
        b1.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, GroupActivity.class);
            startActivity(intent);

        }
        });


        Button b2=(Button) findViewById(R.id.YelpSearch);
        b2.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, YelpSearchActivity.class);
            startActivity(intent);


        }
        });

        Button b3=(Button) findViewById(R.id.locationbtn);
        b3.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, YelpSearchByLocationActivity.class);

            startActivity(intent);


        }
        });

        Button b4=(Button) findViewById(R.id.mapbutton);
        b4.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, MapsActivity.class);
            startActivity(intent);
        }
        });

        Button b5=(Button) findViewById(R.id.databasebutton);
        b5.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
            startActivity(intent);
        }
        });

        Button b6=(Button) findViewById(R.id.maplocation);
        b6.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, MapLocationActivity.class);
            startActivity(intent);
        }
        });

        Button b7=(Button) findViewById(R.id.display);
        b7.setOnClickListener(new View.OnClickListener()
        {   public void onClick(View v)
        {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
            startActivity(intent);
        }
        });







    }
}
