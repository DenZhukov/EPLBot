package com.github.EPLBot.repository;

import com.github.EPLBot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelegramUserRepository extends JpaRepository<TelegramUser, Integer> {
    List<TelegramUser> findAllByActiveTrue();
}
