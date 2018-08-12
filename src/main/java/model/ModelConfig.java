

package model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Configuration
public class ModelConfig {

    @Bean
    public GameStateService getGameStateService(){
        return new GameStateServiceImpl();
    }

}
