package Control.Model;

import java.io.Serializable;

public class HighscoreSave implements Serializable {
    private long highscore;
    private String username;
    public HighscoreSave(long highscore, String username){
        this.highscore = highscore;
        this.username = username;
   }

    public long getHighscore() {
        return highscore;
    }

    public String getUsername() {
        return username;
    }
}
