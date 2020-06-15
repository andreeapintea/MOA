package org.example.model;

public class User {
    private String username;
    private String password;
    private String name;
    private String role;

    public User() {
    }

    public User(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name=name;
        this.role = role;

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            User user = (User)o;
            if (!this.username.equals(user.username)) {
                return false;
            } else {
                return !this.password.equals(user.password) ? false : this.role.equals(user.role);
            }
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.username.hashCode();
        result = 31 * result + this.password.hashCode();
        result = 31 * result + this.role.hashCode();
        return result;
    }

    public String toString() {
        return  this.name;
    }
}
