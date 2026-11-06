package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.User;
import jakarta.ejb.Stateless;

@Stateless
public class UserFacade extends AbstractFacade<User> {

    public UserFacade() {
        super(User.class);
    }
}
