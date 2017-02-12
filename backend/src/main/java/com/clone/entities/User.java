package com.clone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Entity
public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private byte enabled;
    private UserRole userRole;
    private Collection<Follow> followers;
    private Collection<Follow> followees;
    private Collection<Tweet> tweets;


    @Id
    @JsonIgnore
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enabled")
    public byte getEnabled() {
        return enabled;
    }

    public void setEnabled(byte enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (enabled != user.enabled) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (int) enabled;
        return result;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "userByUserId")
    public UserRole getUserRole() { return userRole; }

    public void setUserRole(UserRole userByUserId) { this.userRole = userByUserId; }

    @JsonIgnore
    @OneToMany(mappedBy = "userByFollowerId")
    public Collection<Follow> getFollowers() {
        return followers;
    }

    public void setFollowers(Collection<Follow> followsByUserId) {
        this.followers = followsByUserId;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userByFolloweeId")
    public Collection<Follow> getFollowees() {
        return followees;
    }

    public void setFollowees(Collection<Follow> followsByUserId_0) {
        this.followees = followsByUserId_0;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "userByAuthorId")
    public Collection<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(Collection<Tweet> tweetsByUserId) {
        this.tweets = tweetsByUserId;
    }
}
