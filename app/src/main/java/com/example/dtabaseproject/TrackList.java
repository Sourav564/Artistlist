package com.example.dtabaseproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrackList extends ArrayAdapter<Track> {
    private  Activity context;
    List<Track>tracks;
    public  TrackList(Activity context,List<Track>tracks)
    {
        super(context,R.layout.layout_artist_list,tracks);
        this.context=context;
        this.tracks=tracks;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewitem=inflater.inflate(R.layout.layout_artist_list,null,true);
        TextView textViewname1=(TextView)listViewitem.findViewById(R.id.textviewname);
        TextView textViewrating1=(TextView)listViewitem.findViewById(R.id.textviewrating);
        Track track=tracks.get(position);
        textViewname1.setText(track.getTrackname());
        textViewrating1.setText(String.valueOf(track.getTrackrating()));
        return listViewitem;
    }

}
