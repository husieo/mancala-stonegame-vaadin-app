

package com.tests.model;

import com.tests.controller.dto.GameStateDto;
import com.tests.controller.dto.PlayerStateDto;
import com.tests.controller.dto.TurnActionDto;
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

    private GameStateDto gameStateDto;

    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;

    @Value("${starting.stone.number}")
    private int startingStoneNumber;


    public GameStateDto getGameState() {
        return new GameStateDto(gameStateDto);
    }

    @Override
    @PostConstruct
    public void initGame() {
        gameStateDto = GameStateDto.builder()
                .activePlayer(0)
                .playerStateDtoList(initPlayers())
                .build();
    }

    private List<PlayerStateDto> initPlayers() {
        List<PlayerStateDto> playerStateDtoList = new ArrayList<>();
        PlayerStateDto playerStateDto;
        for (int i = 0; i < playersNumber; i++) {

            List<Integer> pitsList = new ArrayList<>();
            for (int j = 0; j < pitsNumber; j++) {
                pitsList.add(startingStoneNumber);
            }

            playerStateDto = PlayerStateDto.builder()
                    .playerId(i)
                    .pitList(pitsList)
                    .scorePit(0)
                    .build();
            playerStateDtoList.add(playerStateDto);
        }
        return playerStateDtoList;
    }

    public void processTurn(TurnActionDto turnActionDto) {
        if(gameStateDto.isGameOver()){
            throw new IllegalArgumentException();
        }
        int playerID = turnActionDto.getPlayerId();
        int currentPit = turnActionDto.getPitId();

        int pitValue = gameStateDto.getSmallPitValue(playerID, currentPit);
        gameStateDto.updateSmallPitValue(playerID, currentPit, -pitValue);
        distributeStones(playerID, currentPit + 1, pitValue);
        gameStateDto.setGameOver(isGameOver());
    }

    private void captureStones(int playerId, int currentPit) {
        if(playerId == gameStateDto.getActivePlayer()) {
            int otherPlayerId = (playerId + 1) % playersNumber;
            int otherPitNumber = pitsNumber - currentPit - 1;
            int otherPlayerPitValue = gameStateDto.getSmallPitValue(otherPlayerId, otherPitNumber);
            gameStateDto.updateScorePitValue(playerId, otherPlayerPitValue + 1);
            gameStateDto.updateSmallPitValue(otherPlayerId, otherPitNumber, -otherPlayerPitValue);
        }
    }

    private void distributeStones(int playerId, int currentPit, int stoneNumber) {
        if (stoneNumber > 0) {
            if (currentPit < pitsNumber) {
                if (stoneNumber == 1 && gameStateDto.getSmallPitValue(playerId, currentPit) == 0) {
                    captureStones(playerId, currentPit);
                } else {
                    gameStateDto.updateSmallPitValue(playerId, currentPit, 1);
                }
            } else {
                gameStateDto.updateScorePitValue(playerId, 1);
                playerId = (playerId + 1) % playersNumber;
                currentPit = -1;
            }
            distributeStones(playerId, currentPit + 1, stoneNumber - 1);
        } else if (stoneNumber == 0 && currentPit != 0) { // if current pit is zero, last stone was put on score pit and player gets extra turn
            changeActivePlayer();
        }
    }

    private void changeActivePlayer() {
        gameStateDto.setActivePlayer((gameStateDto.getActivePlayer() + 1) % playersNumber);
    }

    private boolean isGameOver(){
        for (int i = 0; i < gameStateDto.getPlayerStateDtoList().size(); i++) {

            List<Integer> pitList = gameStateDto.getPlayerStateDtoList().get(i).getPitList();
            if(pitList.stream().mapToInt(Integer::intValue).sum() == 0){
                return true;
            }
        }
        return false;
    }
}
