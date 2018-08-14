package com.mancala.model.service;

import com.mancala.model.dto.GameStateDto;

/**
 * Created by Oleksandr Husiev on 8/14/2018.
 */
public interface GameStateService {


    int getSmallPitValue(int playerId, int pitNumber);

    void updateSmallPitValue(int playerId, int pitNumber, int value);

    void updateScorePitValue(int playerId, int value);

    void initGame();

    void changeActivePlayer();

    boolean isGameOver();

    void setGameOver();

    int getActivePlayer();

    GameStateDto getGameState();
}
