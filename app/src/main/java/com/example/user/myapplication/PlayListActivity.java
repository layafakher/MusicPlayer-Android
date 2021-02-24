package com.example.user.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 10/3/2020.
 */

public class PlayListActivity extends ListActivity{

    ArrayList<HashMap<String,String>> songsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);

        SongManager songManager = new SongManager();
        this.songsList = songManager.getSongsList();


        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),songsList,R.layout.playlist_item,new String[]{"title"},new int[]{R.id.songTitle});
        setListAdapter(adapter);
        ListView listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent goToItem = new Intent(getApplicationContext(),MainActivity.class);
                goToItem.putExtra("songIndex",position);
                setResult(100,goToItem);
                finish();
            }
        });

    }
}
