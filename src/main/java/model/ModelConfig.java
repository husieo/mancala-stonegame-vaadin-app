

package model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public GameStateService getGameStateService(){
        return new GameStateServiceImpl();
    }

}
