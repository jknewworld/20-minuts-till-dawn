package com.P3.Model.Repo;

import com.P3.Model.User;
import com.google.gson.Gson;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import org.bson.types.ObjectId;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepo {
    private static final Datastore db = Connection.getDatabase();

    public static User findUserByUsername(String username) {
        User user = db.find(User.class).filter(Filters.eq("username", username)).first();
        return user;
    }

    public static User findUserByAnswer(String answer) {
        User user = db.find(User.class).filter(Filters.eq("answer", answer)).first();
        return user;
    }

    public static User saveUser(User user) {
        return db.save(user);
    }

    public static ArrayList<User> findAllUsers() {
        ArrayList<User> users = new ArrayList<>(db.find(User.class).iterator().toList());
        return users;
    }

    public static void saveUserWithJson(User user) {
        Gson gson = new Gson();
        String fileName = "users/" + user.getUsername() + ".json";

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    public static void removeUser(User user) {
        db.delete(user);
    }

}
