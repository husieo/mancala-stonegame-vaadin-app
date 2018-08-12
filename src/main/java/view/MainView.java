

package view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import controller.GameStateController;
import controller.dto.GameStateDto;
import controller.dto.PlayerStateDto;
import controller.dto.TurnActionDto;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@UIScope
@SpringView(name = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "MAIN_VIEW";

    private GameStateController gameStateController;

    private static int PLAYERS_NUMBER = 2;
    private static int PITS_NUMBER = 6;

    Button firstPlayerPit;
    Button secondPlayerPit;
    GridLayout smallPitsLayout;
    List<List<Button>> pitButtons;


    public MainView(GameStateController gameStateController) {
        this.gameStateController = gameStateController;
    }

    @PostConstruct
    private void init() {
        GameStateDto gameStateDto = gameStateController.getGameState();
        addComponent(initPlayerGrid());
        updatePlayerGrid(gameStateDto);
    }

    private void updatePlayerGrid(GameStateDto gameStateDto){
        List<PlayerStateDto> playerStateDtoList = gameStateDto.getPlayerStateDtoList();
        Integer activePlayerId = gameStateDto.getActivePlayer();
        firstPlayerPit.setCaption(playerStateDtoList.get(0).getScorePit().toString());
        secondPlayerPit.setCaption(playerStateDtoList.get(1).getScorePit().toString());
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            List<Integer> pitList = playerStateDtoList.get(i).getPitList();
            for (int j = 0; j < PITS_NUMBER; j++) {
                Button pitButton = pitButtons.get(i).get(j);
                if(i == activePlayerId){
                    pitButton.setStyleName("active-button");
                } else{
                    pitButton.setStyleName("enemy-button");
                }
                pitButton.setCaption(pitList.get(j).toString());
            }
        }

    }

    private HorizontalLayout initPlayerGrid() {
        HorizontalLayout boardLayout = new HorizontalLayout();

        smallPitsLayout = new GridLayout(PITS_NUMBER, PLAYERS_NUMBER);
        firstPlayerPit = new Button();
        boardLayout.addComponent(firstPlayerPit);

        pitButtons = new ArrayList<>();
        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            pitButtons.add(new ArrayList<>());
            for (int j = 0; j < PITS_NUMBER; j++) {
                Button pitButton = new Button();
                final int playerId = i;
                final int pitId = j;
                pitButton.addClickListener(action -> sendTurn(playerId, pitId));
                smallPitsLayout.addComponent(pitButton, j, i);
                pitButtons.get(i).add(pitButton);
            }
        }
        boardLayout.addComponent(smallPitsLayout);

        secondPlayerPit = new Button();
        boardLayout.addComponent(secondPlayerPit);

        return boardLayout;
    }

    private void refreshView(){
        GameStateDto gameStateDto = gameStateController.getGameState();
        updatePlayerGrid(gameStateDto);
    }

    private void sendTurn(int playerId, int pitId){
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateController.processGameTurn(turnActionDto);
        refreshView();
    }


}
