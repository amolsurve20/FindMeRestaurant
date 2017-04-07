package amols.com.filterlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by amolsurve on 11/15/16.
 */

public class RestaurantDatabase {

    private static final String TAG = "RestaurantDB";

    //database details
    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 1;

    //class cannot be instantiated
    private RestaurantDatabase(){}

    /**
     * The schema and contract for the underlying database.
     */
    static class RestaurantEntry implements BaseColumns {
        //class cannot be instantiated
        private RestaurantEntry(){}

        public static final String TABLE_NAME = "restaurants";
        public static final String COL_RESTAURANT = "restaurant";
        public static final String COL_RATING = "rating";
    }

    /**
     * A class to help open, create, and update the database
     */
    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String CREATE_TASKS_TABLE =
                "CREATE TABLE " + RestaurantEntry.TABLE_NAME + "(" +
                        RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ", "+
                        RestaurantEntry.COL_RESTAURANT + " TEXT" + ","+
                        RestaurantEntry.COL_RATING + " INTEGER" +
                        ")";

        private static final String DROP_TASKS_TABLE = "DROP TABLE IF EXISTS "+ RestaurantEntry.TABLE_NAME;

        public DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v(TAG, "Creating tasks table");
            db.execSQL(CREATE_TASKS_TABLE); //create table if needed

            //add sample words
            ContentValues sample1 = new ContentValues();
            sample1.put(RestaurantEntry.COL_RESTAURANT, "Taste of India");
            sample1.put(RestaurantEntry.COL_RATING, 0);
            ContentValues sample2 = new ContentValues();
            sample2.put(RestaurantEntry.COL_RESTAURANT, "Thai Tom");
            sample2.put(RestaurantEntry.COL_RATING, 0);

            db.insert(RestaurantEntry.TABLE_NAME, null, sample1);
            db.insert(RestaurantEntry.TABLE_NAME, null, sample2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TASKS_TABLE); //just drop and recreate table
            onCreate(db);
        }
    }
}
