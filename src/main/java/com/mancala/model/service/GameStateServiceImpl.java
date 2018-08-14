package com.mancala.model.service;

import com.mancala.model.dto.GameStateDto;
import com.mancala.model.dto.PlayerStateDto;
import com.mancala.model.dto.TurnActionDto;
import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.entity.PlayerStateEntity;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/14/2018.
 */
public class GameStateServiceImpl implements GameStateService {


    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;


    @Value("${starting.stone.number}")
    private int startingStoneNumber;

    private GameStateEntity gameStateEntity;

    @PostConstruct
    public void initGame() {
        gameStateEntity = GameStateEntity.builder()
                .activePlayer(0)
                .playerStateEntityList(initPlayers())
                .build();
    }


    private List<PlayerStateEntity> initPlayers() {
        List<PlayerStateEntity> playerStateEntityList = new ArrayList<>();
        PlayerStateEntity playerStateEntity;
        for (int i = 0; i < playersNumber; i++) {

            List<Integer> pitsList = new ArrayList<>();
            for (int j = 0; j < pitsNumber; j++) {
                pitsList.add(startingStoneNumber);
            }

            playerStateEntity = PlayerStateEntity.builder()
                    .playerId(i)
                    .pitList(pitsList)
                    .scorePit(0)
                    .build();
            playerStateEntityList.add(playerStateEntity);
        }
        return playerStateEntityList;
    }

    public GameStateDto getGameState() {
        List<PlayerStateDto> playerStateDtoList = new ArrayList<>();
        for(PlayerStateEntity playerStateEntity : gameStateEntity.getPlayerStateEntityList()){
            PlayerStateDto playerStateDto = PlayerStateDto.builder()
                    .playerId(playerStateEntity.getPlayerId())
                    .pitList(playerStateEntity.getPitList())
                    .scorePit(playerStateEntity.getScorePit())
                    .build();
            playerStateDtoList.add(playerStateDto);
        }
        return GameStateDto.builder()
                .activePlayer(gameStateEntity.getActivePlayer())
                .gameOver(isGameOver())
                .playerStateDtoList(playerStateDtoList)
                .build();
    }


    public void changeActivePlayer() {
        gameStateEntity.setActivePlayer((gameStateEntity.getActivePlayer() + 1) % playersNumber);
    }

    public boolean isGameOver(){
        for (int i = 0; i < gameStateEntity.getPlayerStateEntityList().size(); i++) {

            List<Integer> pitList = gameStateEntity.getPlayerStateEntityList().get(i).getPitList();
            if(pitList.stream().mapToInt(Integer::intValue).sum() == 0){
                return true;
            }
        }
        return false;
    }

    public void setGameOver(){
        gameStateEntity.setGameOver(isGameOver());
    }


    public int getSmallPitValue(int playerId, int pitNumber) {
        return gameStateEntity.getPlayerStateEntityList().get(playerId).getPitList().get(pitNumber);
    }

    public void updateSmallPitValue(int playerId, int pitNumber, int value) {
        int prevValue = gameStateEntity.getPlayerStateEntityList().get(playerId).getPitList().get(pitNumber);
        gameStateEntity.getPlayerStateEntityList().get(playerId).getPitList().set(pitNumber, prevValue + value);
    }

    public void updateScorePitValue(int playerId, int value) {
        int prevValue = gameStateEntity.getPlayerStateEntityList().get(playerId).getScorePit();
        gameStateEntity.getPlayerStateEntityList().get(playerId).setScorePit(prevValue + value);
    }

    @Override
    public int getActivePlayer() {
        return gameStateEntity.getActivePlayer();
    }
}
