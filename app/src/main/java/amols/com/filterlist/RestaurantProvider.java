package amols.com.filterlist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by amolsurve on 11/15/16.
 */

public class RestaurantProvider extends ContentProvider {

    private static final String TAG = "TodoProvider";

    //Content Provider details
    private static final String AUTHORITY = "amols.com.filterlist.provider";
    private static final String RESTAURANT_RESOURCE = "restaurants";

    //URI details
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/"+RESTAURANT_RESOURCE);

    //integer values representing each supported resource Uri
    private static final int RESTAURANT_LIST_URI = 1; // /restaurants
    private static final int RESTAURANT_SINGLE_URI = 2;// /restaurant/:id

    private static final UriMatcher sUriMatcher; //for handling Uri requests

    static {
        //setup mapping between URIs and IDs
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, RESTAURANT_RESOURCE, RESTAURANT_LIST_URI);
        sUriMatcher.addURI(AUTHORITY, RESTAURANT_RESOURCE + "/#", RESTAURANT_SINGLE_URI);
    }

    private RestaurantDatabase.DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new RestaurantDatabase.DatabaseHelper(getContext()); //initialize the helper
        return true;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(RestaurantDatabase.RestaurantEntry.TABLE_NAME);

        switch(sUriMatcher.match(uri)){
            case RESTAURANT_LIST_URI: //all restaurants
                break; //no change
            case RESTAURANT_SINGLE_URI: //single restaurants
                builder.appendWhere(RestaurantDatabase.RestaurantEntry._ID + "=" + uri.getLastPathSegment()); //restrict to that item
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }

        Cursor c = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        if(sUriMatcher.match(uri) != RESTAURANT_LIST_URI) {
            throw new IllegalArgumentException("Unknown insert URI "+uri);
        }

        if(!values.containsKey(RestaurantDatabase.RestaurantEntry.COL_RESTAURANT)){
            values.put(RestaurantDatabase.RestaurantEntry.COL_RESTAURANT, "");
        }

        if(!values.containsKey(RestaurantDatabase.RestaurantEntry.COL_RATING)){
            values.put(RestaurantDatabase.RestaurantEntry.COL_RATING, 0);
        }

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long rowId = db.insert(RestaurantDatabase.RestaurantEntry.TABLE_NAME, null, values);
        if (rowId > 0) { //if successful
            Uri wordUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(wordUri, null);
            return wordUri; //return the URI for the entry
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        int count;
        switch (sUriMatcher.match(uri)) {
            case RESTAURANT_LIST_URI:
                count = db.update(RestaurantDatabase.RestaurantEntry.TABLE_NAME, values, selection, selectionArgs); //just pass in params
                break;
            case RESTAURANT_SINGLE_URI:
                String wordId = uri.getLastPathSegment();
                count = db.update(RestaurantDatabase.RestaurantEntry.TABLE_NAME, values, RestaurantDatabase.RestaurantEntry._ID + "=" + wordId //select by id
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs); //apply params
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new SQLException("Failed to update row " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        int count;
        switch (sUriMatcher.match(uri)) {
            case RESTAURANT_LIST_URI:
                count = db.delete(RestaurantDatabase.RestaurantEntry.TABLE_NAME, selection, selectionArgs); //just pass in params
                break;
            case RESTAURANT_SINGLE_URI:
                String wordId = uri.getLastPathSegment();
                count = db.delete(RestaurantDatabase.RestaurantEntry.TABLE_NAME, RestaurantDatabase.RestaurantEntry._ID + "=" + wordId //select by id
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs); //apply params
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        if (count > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new SQLException("Failed to update row " + uri);

    }


    @Override
    public String getType(Uri uri) {
        //return cursor types, per http://developer.android.com/guide/topics/providers/content-provider-creating.html#TableMIMETypes
        switch(sUriMatcher.match(uri)){
            case RESTAURANT_LIST_URI:
                return "vnd.android.cursor.dir/"+AUTHORITY+"."+RESTAURANT_RESOURCE;
            case RESTAURANT_SINGLE_URI:
                return "vnd.android.cursor.item/"+AUTHORITY+"."+RESTAURANT_RESOURCE;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
    }
}
