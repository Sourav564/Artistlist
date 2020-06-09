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

public class ArtistList extends ArrayAdapter<Artist> {
    private Activity context;
    List<Artist> artistList;
    public ArtistList(Activity context,List<Artist> artistList)
    {
        super(context,R.layout.list_layout,artistList);
        this.context=context;
        this.artistList=artistList;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewitem=inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewname=(TextView)listViewitem.findViewById(R.id.textname);
        TextView textViewGenre=(TextView)listViewitem.findViewById(R.id.textgenre);
        Artist artist=artistList.get(position);
        textViewname.setText(artist.getArtistname());
        textViewGenre.setText(artist.getArtistGenre());
        return listViewitem;

    }
    }
