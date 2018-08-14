

package com.mancala.model.entity;

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
public class GameStateEntity {
    private int activePlayer;
    List<PlayerStateEntity> playerStateEntityList;
    private boolean gameOver;

    /**
     * Copy constructor
     * @param other
     */
    public GameStateEntity(GameStateEntity other){
        this.setActivePlayer(other.getActivePlayer());
        this.setGameOver(other.isGameOver());
        playerStateEntityList = new ArrayList<>();
        for(PlayerStateEntity playerStateEntity : this.getPlayerStateEntityList()){
            playerStateEntityList.add(new PlayerStateEntity(playerStateEntity));
        }
    }



}
