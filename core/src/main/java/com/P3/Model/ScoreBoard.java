package com.P3.Model;

import com.P3.Model.Repo.UserRepo;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoard {
    private ArrayList<User> users;
    private String loggedInUsername;

    public enum SortType {
        SCORE, USERNAME, KILLS, SURVIVE_TIME
    }

    private SortType currentSort = SortType.SCORE;

    public ScoreBoard(String loggedInUsername) {
        this.loggedInUsername = loggedInUsername;
        loadUsers();
    }

    public void loadUsers() {
        users = UserRepo.findAllUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }


    public void sortUsers(String sortType) {
        //currentSort = sortType;
        switch (sortType) {
            case "score":
                users.sort(Comparator.comparingInt(User::getScore).reversed());
                break;
            case "username":
                users.sort(Comparator.comparing(User::getUsername));
                break;
            case "kills":
                users.sort(Comparator.comparingInt(User::getKill).reversed());
                break;
            case "survive":
                users.sort(Comparator.comparingInt(User::getTime).reversed());
                break;
        }
    }

    public void render(SpriteBatch batch, BitmapFont font, float startX, float startY, float lineHeight) {
        int maxDisplay = Math.min(users.size(), 10);

        for (int i = 0; i < maxDisplay; i++) {
            User user = users.get(i);

            if (i == 0) font.setColor(Color.GOLD);
            else if (i == 1) font.setColor(Color.GRAY);
            else if (i == 2) font.setColor(Color.BROWN);
            else font.setColor(Color.WHITE);


            if (user.getUsername().equals(loggedInUsername)) {
                font.setColor(Color.CYAN);
            }

            String line = String.format("%d. %s - Score: %d, Kills: %d, Survive: %d s",
                i + 1,
                user.getUsername(),
                user.getScore(),
                user.getKill(),
                user.getTime());

            font.draw(batch, line, startX, startY - i * lineHeight);
        }
    }

    public int getTotalKills() {
        int total = 0;
        for (User user : users) {
            total += user.getKill();
        }
        return total;
    }

    public int getMaxSurviveTime() {
        int max = 0;
        for (User user : users) {
            if (user.getTime() > max) {
                max = user.getTime();
            }
        }
        return max;
    }
}
