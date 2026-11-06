package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.CompanyFacade;
import in.systemattic.smberp.entity.Company;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CompanyController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CompanyFacade companyFacade;

    private Company selected;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Company();
    }

    public List<Company> getItems() {
        return companyFacade.findAll();
    }

    public void save() {
        if (editing) {
            companyFacade.edit(selected);
        } else {
            companyFacade.create(selected);
        }
        selected = new Company();
        editing = false;
    }

    public void select(Company company) {
        selected = company;
        editing = true;
    }

    public void delete(Company company) {
        companyFacade.remove(company);
        selected = new Company();
        editing = false;
    }

    public Company getSelected() {
        return selected;
    }

    public void setSelected(Company selected) {
        this.selected = selected;
    }

    public boolean isEditing() {
        return editing;
    }
}
