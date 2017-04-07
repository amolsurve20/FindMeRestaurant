package amols.com.filterlist;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by amolsurve on 10/21/16.
 */
public class CustomAdapter extends BaseAdapter {

    ArrayList<Restaurants> searchResults;

    ViewHolder viewHolder;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<Restaurants> results) {
        searchResults = new ArrayList<>(results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants, null);
            viewHolder = new ViewHolder();

            viewHolder.photo = (ImageView) convertView.findViewById(R.id.photo);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location);


            convertView.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) convertView.getTag();

        int photoId = (Integer) searchResults.get(position).getPicture();

        viewHolder.photo.setImageDrawable(parent.getContext().getResources().getDrawable(photoId));
        viewHolder.name.setText(searchResults.get(position).getName());
        viewHolder.location.setText(searchResults.get(position).getLocation());


        return convertView;

    }

    public void clearSearchResult() {
        searchResults.clear();
    }

    public void addSeachResult(Restaurants result) {
        this.searchResults.add(result);
    }

    private class ViewHolder {
        ImageView photo;
        TextView name, location;

    }

    @Override
    public int getCount() {
        return searchResults.size();
    }

    @Override
    public Object getItem(int position) {
        return searchResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return searchResults.get(position).getId();
    }
}
