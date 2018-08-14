

package com.mancala.model.service;

import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.entity.PlayerStateEntity;
import com.mancala.model.dto.TurnActionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Service
public class GameStateServiceImpl implements GameStateService {

    private GameStateEntity gameStateEntity;

    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;

    @Value("${starting.stone.number}")
    private int startingStoneNumber;


    public GameStateEntity getGameState() {
        return new GameStateEntity(gameStateEntity);
    }

    @Override
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

    public void processTurn(TurnActionDto turnActionDto) {
        if(gameStateEntity.isGameOver()){
            throw new IllegalArgumentException();
        }
        int playerID = turnActionDto.getPlayerId();
        int currentPit = turnActionDto.getPitId();

        int pitValue = getSmallPitValue(playerID, currentPit);
        updateSmallPitValue(playerID, currentPit, -pitValue);
        distributeStones(playerID, currentPit + 1, pitValue);
        gameStateEntity.setGameOver(isGameOver());
    }

    private void captureStones(int playerId, int currentPit) {
        if(playerId == gameStateEntity.getActivePlayer()) {
            int otherPlayerId = (playerId + 1) % playersNumber;
            int otherPitNumber = pitsNumber - currentPit - 1;
            int otherPlayerPitValue = getSmallPitValue(otherPlayerId, otherPitNumber);
            updateScorePitValue(playerId, otherPlayerPitValue + 1);
            updateSmallPitValue(otherPlayerId, otherPitNumber, -otherPlayerPitValue);
        }
    }

    private void distributeStones(int playerId, int currentPit, int stoneNumber) {
        if (stoneNumber > 0) {
            if (currentPit < pitsNumber) {
                if (stoneNumber == 1 && getSmallPitValue(playerId, currentPit) == 0) {
                    captureStones(playerId, currentPit);
                } else {
                    updateSmallPitValue(playerId, currentPit, 1);
                }
            } else {
                updateScorePitValue(playerId, 1);
                playerId = (playerId + 1) % playersNumber;
                currentPit = -1;
            }
            distributeStones(playerId, currentPit + 1, stoneNumber - 1);
        } else if (stoneNumber == 0 && currentPit != 0) { // if current pit is zero, last stone was put on score pit and player gets extra turn
            changeActivePlayer();
        }
    }

    private void changeActivePlayer() {
        gameStateEntity.setActivePlayer((gameStateEntity.getActivePlayer() + 1) % playersNumber);
    }

    private boolean isGameOver(){
        for (int i = 0; i < gameStateEntity.getPlayerStateEntityList().size(); i++) {

            List<Integer> pitList = gameStateEntity.getPlayerStateEntityList().get(i).getPitList();
            if(pitList.stream().mapToInt(Integer::intValue).sum() == 0){
                return true;
            }
        }
        return false;
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
}
