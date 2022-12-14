package com.niit.jdp.Repository;

import com.niit.jdp.Model.Songs;
import com.niit.jdp.Repository.Repository;
import com.niit.jdp.Service.DatabaseService;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SongRepository implements Repository {
    // Creating Database Service object for calling connect method
    DatabaseService databaseService = new DatabaseService();


    public List<Songs> displayAllSong() {

        //creating object of genericList
        List<Songs> songList = new ArrayList<>();
        Connection connection = databaseService.connect();
        String sqlQuery = "SELECT * FROM `jukebox`.`songs`;";
        try {
            //PreparedStatement interface
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            //using next method for control the loop
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String duration = resultSet.getString(3);
                String albumName = resultSet.getString(5);
                String artistName = resultSet.getString(4);
                String genre = resultSet.getString(6);
                String path = resultSet.getString(7);
                //we are adding the object of the song list
                songList.add(new Songs(id, name, duration, artistName,albumName, genre, path));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return songList;
    }

    public List<Songs> sortSongs(List<Songs> songList) {
        Comparator<Songs> nameComparator = (((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getGenre(), o2.getGenre())));
        songList.sort(nameComparator);
        for (Songs songs : songList)
            songs.getSongName();
        return songList;
    }

    // search song by name


    public List<Songs> songSearchBySongName(List<Songs> songList, String name) {
        Connection connection = databaseService.connect();
        List<Songs> songList1 = new ArrayList<>();
        for (Songs song : songList) {
            if (song.getSongName().equalsIgnoreCase(name)) {
                songList1.add(song);
            }
        }
        return songList1;
    }

    public List<Songs> songSearchByGenre(List<Songs> songList, String genre) {
        Connection connection = databaseService.connect();
        List<Songs> songList1 = new ArrayList<>();
        for (Songs song : songList) {
            if (song.getGenre().equals(genre)) {
                songList1.add(song);
            }
        }
        return songList1;
    }

    public void displayFormat(List<Songs> songList) {
        System.out.format("%-10s %-30s %-20s %-30s %-20s %-30s\n", "Id", "Name", "Duration","ArtistName", "AlbumName",  "Genre");
        for (Songs song : songList) {
            System.out.format("%-10d %-30s %-20s %-30s %-20s %-30s\n", song.getSongId(), song.getSongName(), song.getArtistName(), song.getAlbumName(), song.getDuration(), song.getGenre());
        }
    }
}