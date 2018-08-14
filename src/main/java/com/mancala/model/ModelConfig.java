

package com.mancala.model;

import com.mancala.model.service.GameLogicService;
import com.mancala.model.service.GameLogicServiceImpl;
import com.mancala.model.service.GameStateService;
import com.mancala.model.service.GameStateServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Configuration
public class ModelConfig {

    @Value("${players.number}")
    private int playersNumber;

    @Value("${pits.number}")
    private int pitsNumber;

    @Value("${starting.stone.number}")
    private int startingStoneNumber;

    @Bean
    public GameLogicService getGameLogicService(){
        return new GameLogicServiceImpl();
    }

    @Bean
    @ApplicationScope
    public GameStateService getGameStateService(){ return new GameStateServiceImpl(); }

}
