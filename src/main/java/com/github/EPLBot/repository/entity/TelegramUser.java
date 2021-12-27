package com.github.EPLBot.repository.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "epl_users")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "active")
    private boolean active;

    @Column(name = "team_id")
    private Integer teamId;

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Integer getTeamId() { return teamId; }

    public void setTeamId(Integer teamId) { this.teamId = teamId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelegramUser that = (TelegramUser) o;
        return Objects.equals(chatId, that.chatId) && Objects.equals(teamId, that.teamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, teamId);
    }
}
