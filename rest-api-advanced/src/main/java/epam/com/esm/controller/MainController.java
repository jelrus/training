package epam.com.esm.controller;

import epam.com.esm.utils.hateoas.annotations.ControllerLink;
import epam.com.esm.utils.hateoas.builder.LinkBuilder;
import epam.com.esm.utils.hateoas.wrappers.components.MenuDto;
import epam.com.esm.view.resources.data.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MainController is the REST Controller class, which contains method to retrieve main page of application
 */
@RestController
@RequestMapping("")
@ControllerLink
public class MainController extends AbstractController {

    /**
     * Constructs MainController object
     */
    public MainController() {}

    /**
     * Produces HttpResponse entity, which contains links to other resources of application
     *
     * @return {@code HttpEntity<MenuDto>} produced HttpResource entity
     */
    @GetMapping("/")
    public HttpEntity<MenuDto> mainMenu() {
        MenuDto menuDto = new MenuDto();
        LinkBuilder lb = new LinkBuilder();
        lb.loadClass(MainController.class)
          .addModel(menuDto)
          .addLinks(Resources.USERS_ALL, Resources.ORDERS_ALL, Resources.GIFT_CERTIFICATES_ALL, Resources.TAGS_ALL)
          .build();
        return ResponseEntity.ok().body(menuDto);
    }
}