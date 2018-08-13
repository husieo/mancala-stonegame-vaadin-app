

package com.tests.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class GameStateDto {
    private int activePlayer;
    List<PlayerStateDto> playerStateDtoList;
    private boolean gameOver;

    /**
     * Copy constructor
     * @param other
     */
    public GameStateDto(GameStateDto other){
        this.setActivePlayer(other.getActivePlayer());
        this.setGameOver(other.isGameOver());
        playerStateDtoList = new ArrayList<>();
        for(PlayerStateDto playerStateDto : other.getPlayerStateDtoList()){
            playerStateDtoList.add(new PlayerStateDto(playerStateDto));
        }
    }


    public int getSmallPitValue(int playerId, int pitNumber) {
        return playerStateDtoList.get(playerId).getPitList().get(pitNumber);
    }

    public void updateSmallPitValue(int playerId, int pitNumber, int value) {
        int prevValue = playerStateDtoList.get(playerId).getPitList().get(pitNumber);
        playerStateDtoList.get(playerId).getPitList().set(pitNumber, prevValue + value);
    }

    public void updateScorePitValue(int playerId, int value) {
        int prevValue = playerStateDtoList.get(playerId).getScorePit();
        playerStateDtoList.get(playerId).setScorePit(prevValue + value);
    }
}
