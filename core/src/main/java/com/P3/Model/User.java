package com.P3.Model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("users")
public class User {
    @Id
    private String username;
    private String password;
    private String answer;
    private int avatar;
    private int score;
    private String avatarPath;
    private boolean keyboard;
    private int hero;
    private int time;
    private int weapon;
    private int ability;
    private float health;
    private int kill;
    private int level;
    private boolean heavy;
    private boolean playSFX;
    private int ammo;
    private int maxTime;
    private boolean reloadR;


    public User(String username, String password, String answer, int avatar) {
        this.username = username;
        this.password = password;
        this.answer = answer;
        this.avatar = avatar;
        this.score = 0;
        this.avatarPath = "";
        this.keyboard = true;
        this.hero = 0;
        this.time = 0;
        this.weapon = 0;
        this.ability = 5;
        this.kill = 0;
        this.level = 1;
        this.heavy = false;
        this.playSFX = false;
        this.ammo = 0;
        this.reloadR = false;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getAvatar() {
        return avatar;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public boolean isKeyboard() {
        return keyboard;
    }

    public void setKeyboard(boolean keyboard) {
        this.keyboard = keyboard;
    }

    public int getHero() {
        return hero;
    }

    public void setHero(int hero) {
        this.hero = hero;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWeapon() {
        return weapon;
    }

    public void setWeapon(int weapon) {
        this.weapon = weapon;
    }

    public int getAbility() {
        return ability;
    }

    public void setAbility(int ability) {
        this.ability = ability;
        if (ability == 0) {
            this.health += 1;
        }
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isHeavy() {
        return heavy;
    }

    public void setHeavy(boolean heavy) {
        this.heavy = heavy;
    }

    public boolean isPlaySFX() {
        return playSFX;
    }

    public void setPlaySFX(boolean playSFX) {
        this.playSFX = playSFX;
    }

    public int getAmmo() {
        return ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public boolean isReloadR() {
        return reloadR;
    }

    public void setReloadR(boolean reloadR) {
        this.reloadR = reloadR;
    }
}


