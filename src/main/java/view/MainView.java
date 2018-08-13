

package view;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import controller.GameStateController;
import controller.dto.GameStateDto;
import controller.dto.PlayerStateDto;
import controller.dto.TurnActionDto;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@UIScope
@SpringView(name = MainView.VIEW_NAME)
public class MainView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "mainView";

    private GameStateController gameStateController;

    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;

    private Button firstPlayerPit;
    private Button secondPlayerPit;
    private Button restartButton;
    private GridLayout smallPitsLayout;
    private List<List<Button>> pitButtons;
    private Label descriptionLabel;


    public MainView(GameStateController gameStateController) {
        this.gameStateController = gameStateController;
    }

    @PostConstruct
    private void init() {
        GameStateDto gameStateDto = gameStateController.getGameState();
        descriptionLabel = new Label();
        restartButton = new Button("RESTART");
        restartButton.setEnabled(false);
        restartButton.setDisableOnClick(true);
        restartButton.addClickListener(action -> {
            gameStateController.restartGame();
            refreshView();
        });
        restartButton.setStyleName("restart-button");

        addComponent(descriptionLabel);
        addComponent(restartButton);

        addComponent(initPlayerGrid());
        updatePlayerGrid(gameStateDto);
    }

    /**
     * initialize view components for the pits
     *
     * @return
     */
    private HorizontalLayout initPlayerGrid() {
        HorizontalLayout boardLayout = new HorizontalLayout();

        smallPitsLayout = new GridLayout(pitsNumber, playersNumber);
        firstPlayerPit = new Button();
        firstPlayerPit.setStyleName("big-pit-button");
        boardLayout.addComponent(firstPlayerPit);

        pitButtons = new ArrayList<>();
        for (int i = 0; i < playersNumber; i++) {
            pitButtons.add(new ArrayList<>());
            for (int j = 0; j < pitsNumber; j++) {
                Button pitButton = new Button();
                final int playerId = i;
                final int pitId = (playerId % 2 == 1) ? j : (pitsNumber - j - 1);
                pitButton.addClickListener(action -> sendTurn(playerId, pitId));
                smallPitsLayout.addComponent(pitButton, j, i);
                pitButtons.get(i).add(pitButton);
            }
        }
        boardLayout.addComponent(smallPitsLayout);

        secondPlayerPit = new Button();
        secondPlayerPit.setStyleName("big-pit-button");
        boardLayout.addComponent(secondPlayerPit);

        return boardLayout;
    }

    /**
     * update the view
     */
    private void refreshView() {
        GameStateDto gameStateDto = gameStateController.getGameState();
        updatePlayerGrid(gameStateDto);
    }

    /**
     * process the turn after click on the pit
     *
     * @param playerId
     * @param pitId
     */
    private void sendTurn(int playerId, int pitId) {
        TurnActionDto turnActionDto = TurnActionDto.builder().playerId(playerId).pitId(pitId).build();
        gameStateController.processGameTurn(turnActionDto);
        refreshView();
    }

    /**
     * Update player grid according to the game state
     *
     * @param gameStateDto
     */
    private void updatePlayerGrid(GameStateDto gameStateDto) {
        List<PlayerStateDto> playerStateDtoList = gameStateDto.getPlayerStateDtoList();
        int activePlayerId = gameStateDto.getActivePlayer();
        firstPlayerPit.setCaption(playerStateDtoList.get(0).getScorePit().toString());
        secondPlayerPit.setCaption(playerStateDtoList.get(1).getScorePit().toString());
        if (gameStateDto.isGameOver()) {
            descriptionLabel.setValue("GAME OVER");
            restartButton.setEnabled(true);
        }
        for (int i = 0; i < playersNumber; i++) {
            List<Integer> pitList = playerStateDtoList.get(i).getPitList();
            for (int j = 0; j < pitsNumber; j++) {
                final int playerId = i;
                final int pitId = (playerId % 2 == 1) ? j : (pitsNumber - j - 1);
                Button pitButton = pitButtons.get(i).get(j);
                if (i == activePlayerId) {
                    pitButton.setStyleName("active-button");
                    pitButton.setEnabled(true);
                } else {
                    pitButton.setStyleName("enemy-button");
                    pitButton.setEnabled(false);
                }
                Integer pitValue = pitList.get(pitId);
                if (pitValue == 0) {
                    pitButton.setEnabled(false);
                }
                pitButton.setCaption(pitValue.toString());
            }
        }

    }


}
