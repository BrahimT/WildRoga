package entities;

import java.util.HashSet;
import java.util.Set;

public class User {

    private int id;
    private String name;

    public User(int id, String name){
        this.setId(id);
        this.setName(name);
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Set<String> getUserData(){

        Set<String> userData = new HashSet<>();

        userData.add(Integer.toString(this.getId()));
        userData.add(this.getName());

        return userData;
    }
}
