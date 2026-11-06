package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BranchFacade;
import in.systemattic.smberp.ejb.CompanyFacade;
import in.systemattic.smberp.entity.Branch;
import in.systemattic.smberp.entity.Company;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BranchController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BranchFacade branchFacade;

    @Inject
    private CompanyFacade companyFacade;

    private Branch selected;
    private Integer selectedCompanyId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Branch();
    }

    public List<Branch> getItems() {
        return branchFacade.findAll();
    }

    public List<Company> getCompanies() {
        return companyFacade.findAll();
    }

    public void save() {
        Company company = companyFacade.find(selectedCompanyId);
        if (company == null) {
            throw new IllegalArgumentException("The selected company does not exist.");
        }
        selected.setCompany(company);
        if (editing) {
            branchFacade.edit(selected);
        } else {
            branchFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Branch branch) {
        selected = branch;
        selectedCompanyId = branch.getCompany() == null ? null : branch.getCompany().getId();
        editing = true;
    }

    public void delete(Branch branch) {
        branchFacade.remove(branch);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Branch();
        selectedCompanyId = null;
        editing = false;
    }

    public Branch getSelected() {
        return selected;
    }

    public void setSelected(Branch selected) {
        this.selected = selected;
    }

    public Integer getSelectedCompanyId() {
        return selectedCompanyId;
    }

    public void setSelectedCompanyId(Integer selectedCompanyId) {
        this.selectedCompanyId = selectedCompanyId;
    }

    public boolean isEditing() {
        return editing;
    }
}
