package com.P3.Model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager {
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));


    private final String character0_idle0 = "0/Idle_0.png";
    private final String character0_idle1 = "0/Idle_1.png";
    private final String character0_idle2 = "0/Idle_2.png";
    private final String character0_idle3 = "0/Idle_3.png";
    private final String character0_idle4 = "0/Idle_4.png";
    private final String character0_idle5 = "0/Idle_5.png";
    private final Texture character0_idle0_tex = new Texture(character0_idle0);
    private final Texture character0_idle1_tex = new Texture(character0_idle1);
    private final Texture character0_idle2_tex = new Texture(character0_idle2);
    private final Texture character0_idle3_tex = new Texture(character0_idle3);
    private final Texture character0_idle4_tex = new Texture(character0_idle4);
    private final Texture character0_idle5_tex = new Texture(character0_idle5);
    private final Animation<Texture> character0_idle_frames = new Animation<>(0.1f, character0_idle0_tex, character0_idle1_tex, character0_idle2_tex, character0_idle3_tex, character0_idle4_tex, character0_idle5_tex);


    private final String character1_idle0 = "1/Idle_0.png";
    private final String character1_idle1 = "1/Idle_1.png";
    private final String character1_idle2 = "1/Idle_2.png";
    private final String character1_idle3 = "1/Idle_3.png";
    private final String character1_idle4 = "1/Idle_4.png";
    private final String character1_idle5 = "1/Idle_5.png";
    private final Texture character1_idle0_tex = new Texture(character1_idle0);
    private final Texture character1_idle1_tex = new Texture(character1_idle1);
    private final Texture character1_idle2_tex = new Texture(character1_idle2);
    private final Texture character1_idle3_tex = new Texture(character1_idle3);
    private final Texture character1_idle4_tex = new Texture(character1_idle4);
    private final Texture character1_idle5_tex = new Texture(character1_idle5);
    private final Animation<Texture> character1_idle_frames = new Animation<>(0.1f, character1_idle0_tex, character1_idle1_tex, character1_idle2_tex, character1_idle3_tex, character1_idle4_tex, character1_idle5_tex);

    private final String character2_idle0 = "2/Idle_0.png";
    private final String character2_idle1 = "2/Idle_1.png";
    private final String character2_idle2 = "2/Idle_2.png";
    private final String character2_idle3 = "2/Idle_3.png";
    private final String character2_idle4 = "2/Idle_4.png";
    private final String character2_idle5 = "2/Idle_5.png";
    private final Texture character2_idle0_tex = new Texture(character2_idle0);
    private final Texture character2_idle1_tex = new Texture(character2_idle1);
    private final Texture character2_idle2_tex = new Texture(character2_idle2);
    private final Texture character2_idle3_tex = new Texture(character2_idle3);
    private final Texture character2_idle4_tex = new Texture(character2_idle4);
    private final Texture character2_idle5_tex = new Texture(character2_idle5);
    private final Animation<Texture> character2_idle_frames = new Animation<>(0.1f, character2_idle0_tex, character2_idle1_tex, character2_idle2_tex, character2_idle3_tex, character2_idle4_tex, character2_idle5_tex);

    private final String character3_idle0 = "3/Idle_0.png";
    private final String character3_idle1 = "3/Idle_1.png";
    private final String character3_idle2 = "3/Idle_2.png";
    private final String character3_idle3 = "3/Idle_3.png";
    private final String character3_idle4 = "3/Idle_4.png";
    private final String character3_idle5 = "3/Idle_5.png";
    private final Texture character3_idle0_tex = new Texture(character3_idle0);
    private final Texture character3_idle1_tex = new Texture(character3_idle1);
    private final Texture character3_idle2_tex = new Texture(character3_idle2);
    private final Texture character3_idle3_tex = new Texture(character3_idle3);
    private final Texture character3_idle4_tex = new Texture(character3_idle4);
    private final Texture character3_idle5_tex = new Texture(character3_idle5);
    private final Animation<Texture> character3_idle_frames = new Animation<>(0.1f, character3_idle0_tex, character3_idle1_tex, character3_idle2_tex, character3_idle3_tex, character3_idle4_tex, character3_idle5_tex);

    private final String character4_idle0 = "4/Idle_0.png";
    private final String character4_idle1 = "4/Idle_1.png";
    private final String character4_idle2 = "4/Idle_2.png";
    private final String character4_idle3 = "4/Idle_3.png";
    private final String character4_idle4 = "4/Idle_4.png";
    private final String character4_idle5 = "4/Idle_5.png";
    private final Texture character4_idle0_tex = new Texture(character4_idle0);
    private final Texture character4_idle1_tex = new Texture(character4_idle1);
    private final Texture character4_idle2_tex = new Texture(character4_idle2);
    private final Texture character4_idle3_tex = new Texture(character4_idle3);
    private final Texture character4_idle4_tex = new Texture(character4_idle4);
    private final Texture character4_idle5_tex = new Texture(character4_idle5);
    private final Animation<Texture> character4_idle_frames = new Animation<>(0.1f, character4_idle0_tex, character4_idle1_tex, character4_idle2_tex, character4_idle3_tex, character4_idle4_tex, character4_idle5_tex);

    private final String tree0 = "tree/0.png";
    private final String tree1 = "tree/1.png";
    private final String tree2 = "tree/2.png";
    private final String tree3 = "tree/3.png";
    private final Texture tree0_tex = new Texture(tree0);
    private final Texture tree1_tex = new Texture(tree1);
    private final Texture tree2_tex = new Texture(tree2);
    private final Texture tree3_tex = new Texture(tree3);
    private final Animation<Texture> tree_frames = new Animation<>(0.3f, tree0_tex, tree1_tex, tree2_tex, tree3_tex);

    private final String monster0 = "monster/0.png";
    private final String monster1 = "monster/1.png";
    private final String monster2 = "monster/2.png";
    private final String monster3 = "monster/3.png";
    private final Texture monster0_tex = new Texture(monster0);
    private final Texture monster1_tex = new Texture(monster1);
    private final Texture monster2_tex = new Texture(monster2);
    private final Texture monster3_tex = new Texture(monster3);
    private final Animation<Texture> monster_frames = new Animation<>(0.3f, monster0_tex, monster1_tex, monster2_tex, monster3_tex);

    private final Texture monsterSeed = new Texture("monster/seed.png");
    private final Animation<Texture> monsterSeed_frames = new Animation<>(1, monsterSeed);

    private final Texture eyeSeed = new Texture("eye/seed.png");
    private final Animation<Texture> eyeSeed_frames = new Animation<>(1, eyeSeed);

    private final Texture deathMonster = new Texture("monster/d1.png");
    private final Texture deathMonster2 = new Texture("monster/d2.png");
    private final Texture deathMonster3 = new Texture("monster/d3.png");
    private final Animation<Texture> death_frames = new Animation<>(0.9f, deathMonster, deathMonster2, deathMonster3);

    private final Texture death0 = new Texture("death/0.png");
    private final Texture death1 = new Texture("death/1.png");
    private final Texture death2 = new Texture("death/2.png");
    private final Animation<Texture> deathBullet_frames = new Animation<>(0.9f, death0, death1, death2);

    private final Texture walk10 = new Texture("1/w0.png");
    private final Texture walk11 = new Texture("1/w1.png");
    private final Texture walk12 = new Texture("1/w2.png");
    private final Texture walk13 = new Texture("1/w3.png");
    private final Texture walk14 = new Texture("1/w4.png");
    private final Texture walk15 = new Texture("1/w5.png");
    private final Texture walk16 = new Texture("1/w0.png");
    private final Texture walk17 = new Texture("1/w1.png");
    private final Animation<Texture> walk1_frames = new Animation<>(0.08f, walk10, walk11, walk12, walk13, walk14, walk15, walk16, walk17);


    private final String eye0 = "eye/0.png";
    private final String eye1 = "eye/1.png";
    private final String eye2 = "eye/2.png";
    private final String eye3 = "eye/3.png";
    private final Texture eye0_tex = new Texture(eye0);
    private final Texture eye1_tex = new Texture(eye1);
    private final Texture eye2_tex = new Texture(eye2);
    private final Texture eye3_tex = new Texture(eye3);
    private final Animation<Texture> eye_frames = new Animation<>(0.4f, eye0_tex, eye1_tex, eye2_tex, eye3_tex);

    private final Texture deathEye = new Texture("eye/d1.png");
    private final Texture deathEye2 = new Texture("eye/d2.png");
    private final Texture deathEye3 = new Texture("eye/d3.png");
    private final Animation<Texture> deathEye_frames = new Animation<>(0.9f, deathEye, deathEye2, deathEye3);

    private final Texture elder0 = new Texture("Elder/0.png");
    private final Texture elder1 = new Texture("Elder/1.png");
    private final Texture elder2 = new Texture("Elder/2.png");
    private final Texture elder3 = new Texture("Elder/3.png");
    private final Animation<Texture> elder_frames = new Animation<>(0.3f, elder0, elder1, elder2, elder3);


    private final String smg = "smg/SMGStill.png";
    private final Texture smgTexture = new Texture(smg);

    private final String gun = "smg/gun.png";
    private final Texture gunTexture = new Texture(gun);

    private final String sword = "smg/Sword.png";
    private final Texture swordTexture = new Texture(sword);

    private final String bullet = "bullet.png";

    private final String enemyBullet = "eye/bullet.png";


    private GameAssetManager() {

    }

    public static GameAssetManager getGameAssetManager() {
        if (gameAssetManager == null) {
            gameAssetManager = new GameAssetManager();
        }
        return gameAssetManager;
    }

    public Skin getSkin() {
        return skin;
    }

    public Animation<Texture> getCharacter1_idle_animation() {
        return character1_idle_frames;
    }

    public String getCharacter1_idle0() {
        return character1_idle0;
    }

    public Texture getSmgTexture() {
        return smgTexture;
    }

    public String getSmg() {
        return smg;
    }

    public String getBullet() {
        return bullet;
    }

    public static void setGameAssetManager(GameAssetManager gameAssetManager) {
        GameAssetManager.gameAssetManager = gameAssetManager;
    }

    public String getCharacter0_idle0() {
        return character0_idle0;
    }

    public String getCharacter0_idle1() {
        return character0_idle1;
    }

    public String getCharacter0_idle2() {
        return character0_idle2;
    }

    public String getCharacter0_idle3() {
        return character0_idle3;
    }

    public String getCharacter0_idle4() {
        return character0_idle4;
    }

    public String getCharacter0_idle5() {
        return character0_idle5;
    }

    public Texture getCharacter0_idle0_tex() {
        return character0_idle0_tex;
    }

    public Texture getCharacter0_idle1_tex() {
        return character0_idle1_tex;
    }

    public Texture getCharacter0_idle2_tex() {
        return character0_idle2_tex;
    }

    public Texture getCharacter0_idle3_tex() {
        return character0_idle3_tex;
    }

    public Texture getCharacter0_idle4_tex() {
        return character0_idle4_tex;
    }

    public Texture getCharacter0_idle5_tex() {
        return character0_idle5_tex;
    }

    public Animation<Texture> getCharacter0_idle_frames() {
        return character0_idle_frames;
    }

    public String getCharacter1_idle1() {
        return character1_idle1;
    }

    public String getCharacter1_idle2() {
        return character1_idle2;
    }

    public String getCharacter1_idle3() {
        return character1_idle3;
    }

    public String getCharacter1_idle4() {
        return character1_idle4;
    }

    public String getCharacter1_idle5() {
        return character1_idle5;
    }

    public Texture getCharacter1_idle0_tex() {
        return character1_idle0_tex;
    }

    public Texture getCharacter1_idle1_tex() {
        return character1_idle1_tex;
    }

    public Texture getCharacter1_idle2_tex() {
        return character1_idle2_tex;
    }

    public Texture getCharacter1_idle3_tex() {
        return character1_idle3_tex;
    }

    public Texture getCharacter1_idle4_tex() {
        return character1_idle4_tex;
    }

    public Texture getCharacter1_idle5_tex() {
        return character1_idle5_tex;
    }

    public Animation<Texture> getCharacter1_idle_frames() {
        return character1_idle_frames;
    }

    public String getCharacter2_idle0() {
        return character2_idle0;
    }

    public String getCharacter2_idle1() {
        return character2_idle1;
    }

    public String getCharacter2_idle2() {
        return character2_idle2;
    }

    public String getCharacter2_idle3() {
        return character2_idle3;
    }

    public String getCharacter2_idle4() {
        return character2_idle4;
    }

    public String getCharacter2_idle5() {
        return character2_idle5;
    }

    public Texture getCharacter2_idle0_tex() {
        return character2_idle0_tex;
    }

    public Texture getCharacter2_idle1_tex() {
        return character2_idle1_tex;
    }

    public Texture getCharacter2_idle2_tex() {
        return character2_idle2_tex;
    }

    public Texture getCharacter2_idle3_tex() {
        return character2_idle3_tex;
    }

    public Texture getCharacter2_idle4_tex() {
        return character2_idle4_tex;
    }

    public Texture getCharacter2_idle5_tex() {
        return character2_idle5_tex;
    }

    public Animation<Texture> getCharacter2_idle_frames() {
        return character2_idle_frames;
    }

    public String getCharacter3_idle0() {
        return character3_idle0;
    }

    public String getCharacter3_idle1() {
        return character3_idle1;
    }

    public String getCharacter3_idle2() {
        return character3_idle2;
    }

    public String getCharacter3_idle3() {
        return character3_idle3;
    }

    public String getCharacter3_idle4() {
        return character3_idle4;
    }

    public String getCharacter3_idle5() {
        return character3_idle5;
    }

    public Texture getCharacter3_idle0_tex() {
        return character3_idle0_tex;
    }

    public Texture getCharacter3_idle1_tex() {
        return character3_idle1_tex;
    }

    public Texture getCharacter3_idle2_tex() {
        return character3_idle2_tex;
    }

    public Texture getCharacter3_idle3_tex() {
        return character3_idle3_tex;
    }

    public Texture getCharacter3_idle4_tex() {
        return character3_idle4_tex;
    }

    public Texture getCharacter3_idle5_tex() {
        return character3_idle5_tex;
    }

    public Animation<Texture> getCharacter3_idle_frames() {
        return character3_idle_frames;
    }

    public String getCharacter4_idle0() {
        return character4_idle0;
    }

    public String getCharacter4_idle1() {
        return character4_idle1;
    }

    public String getCharacter4_idle2() {
        return character4_idle2;
    }

    public Animation<Texture> getCharacter4_idle_frames() {
        return character4_idle_frames;
    }

    public String getGun() {
        return gun;
    }

    public Texture getGunTexture() {
        return gunTexture;
    }

    public String getSword() {
        return sword;
    }

    public Texture getSwordTexture() {
        return swordTexture;
    }

    public Animation<Texture> getTree_frames() {
        return tree_frames;
    }

    public String getTree0() {
        return tree0;
    }

    public String getMonster0() {
        return monster0;
    }

    public Animation<Texture> getMonster_frames() {
        return monster_frames;
    }

    public Animation<Texture> getDeath_frames() {
        return death_frames;
    }

    public String getEye0() {
        return eye0;
    }

    public Animation<Texture> getEye_frames() {
        return eye_frames;
    }

    public String getEnemyBullet() {
        return enemyBullet;
    }

    public Animation<Texture> getDeathBullet_frames() {
        return deathBullet_frames;
    }

    public Animation<Texture> getDeathEye_frames() {
        return deathEye_frames;
    }

    public Animation<Texture> getMonsterSeed_frames() {
        return monsterSeed_frames;
    }

    public Animation<Texture> getEyeSeed_frames() {
        return eyeSeed_frames;
    }

    public String getCharacter4_idle3() {
        return character4_idle3;
    }

    public String getCharacter4_idle4() {
        return character4_idle4;
    }

    public String getCharacter4_idle5() {
        return character4_idle5;
    }

    public Texture getCharacter4_idle0_tex() {
        return character4_idle0_tex;
    }

    public Texture getCharacter4_idle1_tex() {
        return character4_idle1_tex;
    }

    public Texture getCharacter4_idle2_tex() {
        return character4_idle2_tex;
    }

    public Texture getCharacter4_idle3_tex() {
        return character4_idle3_tex;
    }

    public Texture getCharacter4_idle4_tex() {
        return character4_idle4_tex;
    }

    public Texture getCharacter4_idle5_tex() {
        return character4_idle5_tex;
    }

    public String getTree1() {
        return tree1;
    }

    public String getTree2() {
        return tree2;
    }

    public String getTree3() {
        return tree3;
    }

    public Texture getTree0_tex() {
        return tree0_tex;
    }

    public Texture getTree1_tex() {
        return tree1_tex;
    }

    public Texture getTree2_tex() {
        return tree2_tex;
    }

    public Texture getTree3_tex() {
        return tree3_tex;
    }

    public String getMonster1() {
        return monster1;
    }

    public String getMonster2() {
        return monster2;
    }

    public String getMonster3() {
        return monster3;
    }

    public Texture getMonster0_tex() {
        return monster0_tex;
    }

    public Texture getMonster1_tex() {
        return monster1_tex;
    }

    public Texture getMonster2_tex() {
        return monster2_tex;
    }

    public Texture getMonster3_tex() {
        return monster3_tex;
    }

    public Texture getMonsterSeed() {
        return monsterSeed;
    }

    public Texture getEyeSeed() {
        return eyeSeed;
    }

    public Texture getDeathMonster() {
        return deathMonster;
    }

    public Texture getDeathMonster2() {
        return deathMonster2;
    }

    public Texture getDeathMonster3() {
        return deathMonster3;
    }

    public Texture getDeath0() {
        return death0;
    }

    public Texture getDeath1() {
        return death1;
    }

    public Texture getDeath2() {
        return death2;
    }

    public String getEye1() {
        return eye1;
    }

    public String getEye2() {
        return eye2;
    }

    public String getEye3() {
        return eye3;
    }

    public Texture getEye0_tex() {
        return eye0_tex;
    }

    public Texture getEye1_tex() {
        return eye1_tex;
    }

    public Texture getEye2_tex() {
        return eye2_tex;
    }

    public Texture getEye3_tex() {
        return eye3_tex;
    }

    public Texture getDeathEye() {
        return deathEye;
    }

    public Texture getDeathEye2() {
        return deathEye2;
    }

    public Texture getDeathEye3() {
        return deathEye3;
    }

    public Texture getElder0() {
        return elder0;
    }

    public Texture getElder1() {
        return elder1;
    }

    public Texture getElder2() {
        return elder2;
    }

    public Texture getElder3() {
        return elder3;
    }

    public Animation<Texture> getElder_frames() {
        return elder_frames;
    }

    public Texture getWalk10() {
        return walk10;
    }

    public Texture getWalk11() {
        return walk11;
    }

    public Texture getWalk12() {
        return walk12;
    }

    public Texture getWalk13() {
        return walk13;
    }

    public Texture getWalk14() {
        return walk14;
    }

    public Texture getWalk15() {
        return walk15;
    }

    public Texture getWalk16() {
        return walk16;
    }

    public Texture getWalk17() {
        return walk17;
    }

    public Animation<Texture> getWalk1_frames() {
        return walk1_frames;
    }
}
