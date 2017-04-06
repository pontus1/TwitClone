package com.clone.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by pontusellboj on 2017-01-25.
 */
@Entity
public class Tweet {
    private int messageId;
    private String content;
    private Timestamp pubDate;
    private int authorId;
    private User userByAuthorId;

    @Id
    @Column(name = "message_id")
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "pub_date")
    public Timestamp getPubDate() {
        return pubDate;
    }

    public void setPubDate(Timestamp pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (messageId != tweet.messageId) return false;
        if (content != null ? !content.equals(tweet.content) : tweet.content != null) return false;
        if (pubDate != null ? !pubDate.equals(tweet.pubDate) : tweet.pubDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "author_id")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @JsonProperty("author")
    @JsonIgnoreProperties({"userId", "password", "enabled"}) // ignore everything but username and email
    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "user_id", nullable = false, insertable =  false, updatable = false)
    public User getUserByAuthorId() {
        return userByAuthorId;
    }

    public void setUserByAuthorId(User userByAuthorId) {
        this.userByAuthorId = userByAuthorId;
    }
}
