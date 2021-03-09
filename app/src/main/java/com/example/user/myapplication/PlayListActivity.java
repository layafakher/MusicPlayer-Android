package com.example.user.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 10/3/2020.
 */

public class PlayListActivity extends ListActivity{

    ArrayList<HashMap<String,String>> songsList = new ArrayList<>();

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.playlist);
//
//        SongManager songManager = new SongManager();
//        this.songsList = songManager.getSongsList();
//
//
//        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),songsList,R.layout.playlist_item,new String[]{"title"},new int[]{R.id.songTitle});
//        setListAdapter(adapter);
//        ListView listView = getListView();
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent goToItem = new Intent(getApplicationContext(),MainActivity.class);
//                goToItem.putExtra("songIndex",position);
//                setResult(100,goToItem);
//                finish();
//            }
//        });
//
//    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.playlist);
        SearchView searchView = (SearchView)this.findViewById(R.id.searchView);
        SongManager songManager = new SongManager();
        this.songsList = songManager.getSongsList();
        final SimpleAdapter adapter = new SimpleAdapter(this.getApplicationContext(), this.songsList, 2130968620, new String[]{"title"}, new int[]{2131492952});
        this.setListAdapter(adapter);
        ListView listView = this.getListView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                boolean isFound = false;

                for(int i = 0; i < PlayListActivity.this.songsList.size(); ++i) {
                    HashMap<String, String> music = (HashMap)PlayListActivity.this.songsList.get(i);
                    if (((String)music.get("title")).equals(query)) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound) {
                    Toast.makeText(PlayListActivity.this, "Music Not Found", Toast.LENGTH_SHORT).show();
                }

                return false;
            }

            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int newPosition = PlayListActivity.this.getIndexByValue((HashMap)adapter.getItem(position));
                Intent goToItem = new Intent(PlayListActivity.this.getApplicationContext(), MainActivity.class);
                goToItem.putExtra("songIndex", newPosition);
                PlayListActivity.this.setResult(100, goToItem);
                PlayListActivity.this.finish();
            }
        });
    }

    private int getIndexByValue(HashMap<String, String> stringStringHashMap) {

        int i = 0;
        for(i = 0; i < this.songsList.size(); i++) {

            HashMap<String, String> music = (HashMap)this.songsList.get(i);
            if (music.equals(stringStringHashMap)) {
                break;
            }
        }

        return i;
    }
}
