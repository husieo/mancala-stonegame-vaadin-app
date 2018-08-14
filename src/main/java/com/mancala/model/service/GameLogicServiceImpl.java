

package com.mancala.model.service;

import com.mancala.model.dto.GameStateDto;
import com.mancala.model.dto.PlayerStateDto;
import com.mancala.model.entity.GameStateEntity;
import com.mancala.model.entity.PlayerStateEntity;
import com.mancala.model.dto.TurnActionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Service
public class GameLogicServiceImpl implements GameLogicService {


    @Autowired
    private GameStateService gameStateService;

    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;

    @Value("${starting.stone.number}")
    private int startingStoneNumber;





    public void processTurn(TurnActionDto turnActionDto) {
        if(gameStateService.isGameOver()){
            throw new IllegalArgumentException();
        }
        int playerID = turnActionDto.getPlayerId();
        int currentPit = turnActionDto.getPitId();

        int pitValue = gameStateService.getSmallPitValue(playerID, currentPit);
        gameStateService.updateSmallPitValue(playerID, currentPit, -pitValue);
        distributeStones(playerID, currentPit + 1, pitValue);
        gameStateService.setGameOver();
    }

    private void captureStones(int playerId, int currentPit) {
        if(playerId == gameStateService.getActivePlayer()) {
            int otherPlayerId = (playerId + 1) % playersNumber;
            int otherPitNumber = pitsNumber - currentPit - 1;
            int otherPlayerPitValue = gameStateService.getSmallPitValue(otherPlayerId, otherPitNumber);
            gameStateService.updateScorePitValue(playerId, otherPlayerPitValue + 1);
            gameStateService.updateSmallPitValue(otherPlayerId, otherPitNumber, -otherPlayerPitValue);
        }
    }

    private void distributeStones(int playerId, int currentPit, int stoneNumber) {
        if (stoneNumber > 0) {
            if (currentPit < pitsNumber) {
                if (stoneNumber == 1 && gameStateService.getSmallPitValue(playerId, currentPit) == 0) {
                    captureStones(playerId, currentPit);
                } else {
                    gameStateService.updateSmallPitValue(playerId, currentPit, 1);
                }
            } else {
                gameStateService.updateScorePitValue(playerId, 1);
                playerId = (playerId + 1) % playersNumber;
                currentPit = -1;
            }
            distributeStones(playerId, currentPit + 1, stoneNumber - 1);
        } else if (stoneNumber == 0 && currentPit != 0) { // if current pit is zero, last stone was put on score pit and player gets extra turn
            gameStateService.changeActivePlayer();
        }
    }





}
