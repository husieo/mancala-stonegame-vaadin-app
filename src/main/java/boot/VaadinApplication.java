

package boot;

import controller.ControllerConfig;
import model.ModelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import view.ViewConfig;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@SpringBootApplication
@Import({ViewConfig.class, ControllerConfig.class, ModelConfig.class})
public class VaadinApplication {

    public static void main(String[] args){
        SpringApplication.run(VaadinApplication.class, args);
    }
}
