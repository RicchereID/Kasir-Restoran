package controller;

import model.UserModel;

public class UserController {
    private UserModel userModel;

    public UserController(UserModel userModel) {
        this.userModel = userModel;
    }

    public boolean login(String username, String password) {
        return userModel.authenticate(username, password);
    }
}
