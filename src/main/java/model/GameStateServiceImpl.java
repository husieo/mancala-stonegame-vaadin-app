

package model;

import controller.dto.GameStateDto;
import controller.dto.PlayerStateDto;
import controller.dto.TurnActionDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Service
public class GameStateServiceImpl implements GameStateService {

    private GameStateDto gameStateDto;
    private static int PLAYERS_NUMBER = 2;
    private static int PITS_NUMBER = 6;
    private static int STARTING_STONE_NUMBER = 6;

    public GameStateServiceImpl() {
        initGame();
    }

    public GameStateDto getGameState() {
        return gameStateDto;
    }

    private void initGame() {
        gameStateDto = GameStateDto.builder()
                .activePlayer(0)
                .playerStateDtoList(initPlayers())
                .build();
    }

    private List<PlayerStateDto> initPlayers() {
        List<PlayerStateDto> playerStateDtoList = new ArrayList<>();
        PlayerStateDto playerStateDto;
        for (int i = 0; i < PLAYERS_NUMBER; i++) {

            List<Integer> pitsList = new ArrayList<>();
            for(int j=0;j<PITS_NUMBER;j++){
                pitsList.add(STARTING_STONE_NUMBER);
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
        int playerID = turnActionDto.getPlayerId();
        int currentPit = turnActionDto.getPitId();

        int pitValue = gameStateDto.getSmallPitValue(playerID, currentPit);
        gameStateDto.updateSmallPitValue(playerID, currentPit, -pitValue);
        distributeStones(playerID, currentPit+1, pitValue);
    }

    private void captureStones(int playerId, int currentPit){
        int otherPlayerId = (playerId + 1) % PLAYERS_NUMBER;
        int otherPlayerPitValue = gameStateDto.getSmallPitValue(otherPlayerId, currentPit);
        gameStateDto.updateScorePitValue(playerId, otherPlayerPitValue + 1);
        gameStateDto.updateSmallPitValue(otherPlayerId, currentPit, -otherPlayerPitValue);
    }

    private void distributeStones(int playerId, int currentPit, int stoneNumber) {
        if (stoneNumber > 0) {
            if(currentPit < PITS_NUMBER){
                if(stoneNumber == 1 && gameStateDto.getSmallPitValue(playerId, currentPit) == 0){
                    captureStones(playerId, currentPit);
                } else{
                    gameStateDto.updateSmallPitValue(playerId, currentPit, 1);
                }
            } else{
                gameStateDto.updateScorePitValue(playerId, 1);
                playerId = (playerId + 1) % PLAYERS_NUMBER;
                currentPit = -1;
            }
            distributeStones(playerId, currentPit + 1, stoneNumber - 1);
        } else if(stoneNumber == 0 && currentPit != 0){ // if current pit is zero, last stone was put on score pit and player gets extra turn
            changeActivePlayer();
        }
    }

    private void changeActivePlayer(){
        gameStateDto.setActivePlayer((gameStateDto.getActivePlayer() + 1) % PLAYERS_NUMBER);
    }
}
