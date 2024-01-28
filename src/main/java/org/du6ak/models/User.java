package org.du6ak.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class User{
    private String username;// Имя пользователя
    private String password;// Пароль пользователя
    private boolean isAdmin;// Роль пользователя

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
}
