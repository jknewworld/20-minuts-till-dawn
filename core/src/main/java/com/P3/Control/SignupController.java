package com.P3.Control;

import com.P3.Main;
import com.P3.Model.App;
import com.P3.Model.GameAssetManager;
import com.P3.Model.Repo.UserRepo;
import com.P3.Model.User;
import com.P3.View.SignupView;
import com.P3.View.StartView;
import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignupController {
    private SignupView view;


    public void setView(SignupView view) {
        this.view = view;
    }

    public void handleButtons() {
        if (view != null) {
            if (view.getRegisterButton().isChecked()) {
                handelRegister();
            } else if (view.getGhostButtom().isChecked()) {
                handleGhost();
            }
        }
    }

    public void handelRegister() {
        String username = view.getName().getText();
        String password = view.getPassword().getText();
        String answer = view.getAnswer().getText();

        String allowedChars = "^(?=.*[@%$#&*()_])(?=.*[A-Z])(?=.*\\d).+$";

        if (password.length() < 8) {
            if (StartView.getLanguge() == 1)
                view.showMessage("At least 8 charecters", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Au moins 8 caractères", Color.RED);

        } else if (!password.matches(allowedChars)) {
            if (StartView.getLanguge() == 1)
                view.showMessage("Regex", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Expression régulière", Color.RED);

        } else if (UserRepo.findUserByUsername(username) != null) {
            if (StartView.getLanguge() == 1)
                view.showMessage("You have account", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Tu as un compte", Color.RED);
        } else {
            Random random = new Random();
            int randomNumber = random.nextInt(11) + 1;
            User user = new User(username, password, answer, randomNumber);
            UserRepo.saveUser(user);
            UserRepo.saveUserWithJson(user);
            if (StartView.getLanguge() == 1)
                view.showMessage("Registered Successfully", Color.GREEN);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Enregistré avec succès", Color.GREEN);
        }
    }

    public void handleLogin() {
        String username = view.getLoginUsername().getText();
        String password = view.getLoginPassword().getText();

        User user = UserRepo.findUserByUsername(username);
        if (user == null) {
            if (StartView.getLanguge() == 1)
                view.showMessage("User not found", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Utilisateur non trouvé", Color.RED);

        } else if (!password.equals(user.getPassword())) {
            if (StartView.getLanguge() == 1)
                view.showMessage("Wrong password", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Mot de passe incorrect", Color.RED);

        } else {
            App.setLoggedInUser(user);
            if (StartView.getLanguge() == 1)
                view.showMessage("Login Successfully", Color.GREEN);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Connexion réussie", Color.GREEN);

        }
    }

    public void handleForgetPassword() {
        String answer = view.getForgetPasswordField().getText();
        User user = UserRepo.findUserByAnswer(answer);
        String allowedChars = "^(?=.*[@%$#&*()_])(?=.*[A-Z])(?=.*\\d).+$";


        if (user == null) {
            if (StartView.getLanguge() == 1)
                view.showMessage("User not found", Color.RED);
            else if (StartView.getLanguge() == 2)
                view.showMessage("Utilisateur non trouvé", Color.RED);
        } else {
            String password = view.getNewPass().getText();
            if (!password.matches(allowedChars) || (password.length() < 8)) {
                if (StartView.getLanguge() == 1)
                    view.showMessage("NOPE", Color.RED);
                else if (StartView.getLanguge() == 2)
                    view.showMessage("Non", Color.RED);
            } else {
                user.setPassword(password);
                UserRepo.saveUser(user);
                if (StartView.getLanguge() == 1)
                    view.showMessage("Your Password Change SuccessFully!", Color.GREEN);
                else if (StartView.getLanguge() == 2)
                    view.showMessage("Ton mot de passe a été changé avec succès!", Color.GREEN);
            }
        }
    }

    public void handleGhost() {
        User user = new User("ghost", "ghost", "ghost", 1);
        UserRepo.saveUser(user);
        App.setLoggedInUser(user);
        if (StartView.getLanguge() == 1)
            view.showMessage("welcome as Ghost", Color.GOLD);
        else if (StartView.getLanguge() == 2)
            view.showMessage("Bienvenue en tant que Fantôme", Color.GOLD);

    }
}
