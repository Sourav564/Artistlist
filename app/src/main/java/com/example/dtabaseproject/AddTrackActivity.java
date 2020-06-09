package com.example.dtabaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {
    TextView textViewartist;
    EditText editTextartist;
    SeekBar seekBarratiing;
    Button button;
    ListView listView;
    DatabaseReference databaseReference;
    List<Track>tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
        textViewartist=(TextView)findViewById(R.id.textviewartist);
        editTextartist=(EditText)findViewById(R.id.edittexttrackname);
        seekBarratiing=(SeekBar)findViewById(R.id.seekbarrating);
        button=(Button)findViewById(R.id.button);
        listView=(ListView)findViewById(R.id.listviewtracks);
        tracks=new ArrayList<>();
        Intent intent=getIntent();
        String id=intent.getStringExtra(MainActivity.Artist_ID);
        String name=intent.getStringExtra(MainActivity.Artist_name);
        textViewartist.setText(name);
        databaseReference= FirebaseDatabase.getInstance().getReference("Tracks").child(id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tracksnapshot:dataSnapshot.getChildren())
                {
                    Track track=tracksnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList adapter=new TrackList(AddTrackActivity.this,tracks);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack() {
        String trackname=editTextartist.getText().toString().trim();
        int rating=seekBarratiing.getProgress();
        if(!TextUtils.isEmpty(trackname))
        {
            String id=databaseReference.push().getKey();
            Track track=new Track(id,trackname,rating);
            databaseReference.child(id).setValue(track);
            Toast.makeText(AddTrackActivity.this,"Track Save successfully",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(AddTrackActivity.this,"Please enter a track",Toast.LENGTH_LONG).show();
        }
    }
}
