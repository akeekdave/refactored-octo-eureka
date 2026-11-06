package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Branch;
import jakarta.ejb.Stateless;

@Stateless
public class BranchFacade extends AbstractFacade<Branch> {

    public BranchFacade() {
        super(Branch.class);
    }
}
