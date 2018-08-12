

package view;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

/**
 * Created by Oleksandr Husiev on 8/12/2018.
 */
@SpringUI
@SpringViewDisplay
@Theme("stonegame")
public class VaadinUI extends UI implements ViewDisplay {

    @Autowired
    private SpringViewProvider viewProvider;

    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        final CssLayout navigationBar = new CssLayout();

        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        root.addComponent(navigationBar);

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

        navigateBookmark(getPage().getUriFragment());
    }

    /**
     * Navigate based on uriFragment in URL when application is refreshed
     *
     * @param fragment - uri fragment in url identified by #!
     */
    private void navigateBookmark(String fragment) {
        if (StringUtils.isEmpty(fragment)) {
            getUI().getNavigator().navigateTo(MainView.VIEW_NAME);
        } else {
            String escapedFragment =  HtmlUtils.htmlEscape(fragment).replaceFirst("!", "");
            getUI().getNavigator().navigateTo(escapedFragment);
        }
    }

    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }
}
