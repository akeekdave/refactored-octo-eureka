package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.UserFacade;
import in.systemattic.smberp.entity.User;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UserFacade userFacade;

    public List<User> getItems() {
        return userFacade.findAll();
    }
}
