package accountabilibuddy.model;

import java.util.ArrayList;

/**
 * Created by overt on 5/13/2017.
 */
public class User {

    private String username;
    private String password;
    private ArrayList<String> favorites;

    public User(String username, String password, ArrayList<String> favorites){
        this.username = username;
        this.password = password;
        this.favorites = favorites;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setFavorites(ArrayList<String> favorites){
        this.favorites = favorites;
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

}
