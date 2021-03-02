package com.example.pages;

public class data {
    private String video;
    private String uri;

  private data(){

}
    public data(String name , String videoUri){
     if (name.trim().equals("")){
      name="sorry video name not avalaible" ;

     }
     video=name;
     uri=videoUri;

    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
