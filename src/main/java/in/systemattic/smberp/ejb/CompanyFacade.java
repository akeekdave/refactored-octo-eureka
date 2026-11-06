package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Company;
import jakarta.ejb.Stateless;

@Stateless
public class CompanyFacade extends AbstractFacade<Company> {

    public CompanyFacade() {
        super(Company.class);
    }
}
