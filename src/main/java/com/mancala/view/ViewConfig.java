

package com.mancala.view;

import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.mancala.controller.GameStateController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@Configuration
public class ViewConfig {

    @Bean
    @ViewScope
    public MainView mainView(GameStateController gameStateController) {
        return new MainView(gameStateController);
    }

    @Bean
    @UIScope
    public VaadinUI vaadinUI() {
        return new VaadinUI();
    }

}
