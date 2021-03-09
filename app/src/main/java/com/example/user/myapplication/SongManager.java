package com.example.user.myapplication;
import android.os.Environment;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 10/3/2020.
// */


public class SongManager {
    String path = Environment.getExternalStorageDirectory().getPath();
    ArrayList<HashMap<String, String>> songsList = new ArrayList();
    ArrayList<File> musics = new ArrayList();

    public SongManager() {
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ArrayList<HashMap<String, String>> getSongsList() {
        File home = new File(this.path);
        int i;
        try {
            if (home.listFiles().length > 0) {
                File[] files = home.listFiles();

                for(i = 0; i < files.length; ++i) {
                    File folder = files[i];
                    File[] files1 = folder.listFiles();
                    if (files1 != null && folder.listFiles(new SongManager.FileExtentionFilter()) != null) {
                        files1 = folder.listFiles(new SongManager.FileExtentionFilter());
                        for(File file: files1) {
                            if (file.isDirectory()) {
                                this.searchInFiles(file);
                            } else if (file.getName().endsWith(".mp3") || file.getName().endsWith(".MP3")) {
                                this.musics.add(file);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int n = this.musics.size();
        File[] sortedMusics = new File[n];

        for(i = 0; i < n; ++i) {
            sortedMusics[i] = (File)this.musics.get(i);
        }

        Arrays.sort(sortedMusics, new Comparator<File>() {
            public int compare(File o1, File o2) {
                if (o1.lastModified() > o2.lastModified()) {
                    return -1;
                } else {
                    return o1.lastModified() < o2.lastModified() ? 1 : 0;
                }
            }
        });

        for(File file:sortedMusics) {
            HashMap<String, String> songs = new HashMap();
            songs.put("title", file.getName().substring(0, file.getName().length() - 4));
            songs.put("path", file.getPath());
            this.songsList.add(songs);
        }

        return this.songsList;
    }

    public void setSongsList(ArrayList<HashMap<String, String>> songsList) {
        this.songsList = songsList;
    }

    void searchInFiles(File file) {
        File[] files = file.listFiles(new SongManager.FileExtentionFilter());
        if (files != null) {
            for(File file1:files) {
                if (file1.isDirectory()) {
                }
                else if (file1.getName().endsWith(".mp3") || file1.getName().endsWith(".MP3")) {
                    this.musics.add(file1);
                }
            }
        }

    }


//
//public class SongManager {
//    String path = Environment.getExternalStorageDirectory().getPath()+ "/Download/";
//    ArrayList<HashMap<String,String>> songsList= new ArrayList<>();
//
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public ArrayList<HashMap<String, String>> getSongsList() {
//        File home = new File(path);
//        if (home.listFiles((new FileExtentionFilter())).length>0){
//            for (File file:home.listFiles(new FileExtentionFilter())){
//                HashMap<String,String> songs = new HashMap<>();
//                songs.put("title",file.getName().substring(0,(file.getName().length()-4)));
//                songs.put("path",file.getPath());
//                songsList.add(songs);
//            }
//        }
//        return songsList;
//    }
//
//    public void setSongsList(ArrayList<HashMap<String, String>> songsList) {
//        this.songsList = songsList;
//    }
//
    class FileExtentionFilter implements FilenameFilter{

        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3") || dir.isDirectory());
        }
    }
}
