package com.example.dtabaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String Artist_name="artistname";
    public static final String Artist_ID="artistId";
    EditText ed;
    Button b;
    Spinner sp;
    DatabaseReference reff;
    ListView listView;
    List<Artist>artistList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = findViewById(R.id.edittext);
        b = findViewById(R.id.button);
        sp = findViewById(R.id.spin);
        listView = findViewById(R.id.listview);
        reff = FirebaseDatabase.getInstance().getReference("artist");
        artistList=new ArrayList<>();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Artist artist=artistList.get(i);
                Intent intent=new Intent(MainActivity.this,AddTrackActivity.class);
                intent.putExtra(Artist_ID,artist.getArtistid());
                intent.putExtra(Artist_name,artist.getArtistname());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long l) {
                Artist artist=artistList.get(i);
                showupdateDialog(artist.getArtistid(),artist.getArtistname());
                return false;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot artistSnapshot:dataSnapshot.getChildren())
                {
                    artistList.clear();
                    Artist artist=artistSnapshot.getValue(Artist.class);
                    artistList.add(artist);
                }
                ArtistList adapter=new ArtistList(MainActivity.this,artistList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showupdateDialog(final String artist_ID, String artist_name)
    {
        AlertDialog.Builder dialogbuilder=new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater=getLayoutInflater();
        final  View dialogview=inflater.inflate(R.layout.updatedialog,null);
        dialogbuilder.setView(dialogview);
        final  EditText editTextname=(EditText)dialogview.findViewById(R.id.updatevalue);
        final Button buttonupdate=(Button)dialogview.findViewById(R.id.Addupdatevalue);
        final Spinner spinnergenre=(Spinner)dialogview.findViewById(R.id.spinnergenre);
        final Button delete=(Button)dialogview.findViewById(R.id.buttondelete);
        dialogbuilder.setTitle("Updating Artist:" +artist_name);
        final AlertDialog alertDialog=dialogbuilder.create();
        alertDialog.show();
        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editTextname.getText().toString().trim();
                String genre=spinnergenre.getSelectedItem().toString();
                if(TextUtils.isEmpty(name))
                {
                    editTextname.setError("Name Required");
                    return;

                }
                else
                {
                    updateArtist(artist_ID,name,genre);
                    alertDialog.dismiss();

                }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(artist_ID);
            }
        });
    }

    private void deleteArtist(String artist_id) {
        DatabaseReference deleteArtist=FirebaseDatabase.getInstance().getReference("artist").child(artist_id);
        DatabaseReference deletetracks=FirebaseDatabase.getInstance().getReference("tracks").child(artist_id);
        deleteArtist.removeValue();
        deletetracks.removeValue();
        Toast.makeText(MainActivity.this,"Artist and track deleted",Toast.LENGTH_LONG).show();
    }

    private Boolean updateArtist(String id,String name,String genre)
    {
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("artist").child(id);
        Artist artist=new Artist(id,name,genre);
        databaseReference.setValue(artist);
        Toast.makeText(MainActivity.this,"Artist Updated successfull",Toast.LENGTH_LONG).show();
        return true;
    }


    public void addArtist()
    {
        String name=ed.getText().toString().trim();
        String genre=sp.getSelectedItem().toString();
        if(!TextUtils.isEmpty(name)) {
            String id=reff.push().getKey();
            Artist artist=new Artist(id,name,genre);
            reff.child(id).setValue(artist);
            Toast.makeText(getApplicationContext(),"Artist added",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"You should enter a name",Toast.LENGTH_LONG).show();
        }
    }

}
