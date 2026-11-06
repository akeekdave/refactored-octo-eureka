package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BranchFacade;
import in.systemattic.smberp.ejb.DepartmentFacade;
import in.systemattic.smberp.entity.Branch;
import in.systemattic.smberp.entity.Department;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class DepartmentController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DepartmentFacade departmentFacade;

    @Inject
    private BranchFacade branchFacade;

    private Department selected;
    private Integer selectedBranchId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Department();
    }

    public List<Department> getItems() {
        return departmentFacade.findAll();
    }

    public List<Branch> getBranches() {
        return branchFacade.findAll();
    }

    public void save() {
        Branch branch = branchFacade.find(selectedBranchId);
        if (branch == null) {
            throw new IllegalArgumentException("The selected branch does not exist.");
        }
        selected.setBranch(branch);
        if (editing) {
            departmentFacade.edit(selected);
        } else {
            departmentFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Department department) {
        selected = department;
        selectedBranchId = department.getBranch() == null ? null : department.getBranch().getId();
        editing = true;
    }

    public void delete(Department department) {
        departmentFacade.remove(department);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Department();
        selectedBranchId = null;
        editing = false;
    }

    public Department getSelected() {
        return selected;
    }

    public void setSelected(Department selected) {
        this.selected = selected;
    }

    public Integer getSelectedBranchId() {
        return selectedBranchId;
    }

    public void setSelectedBranchId(Integer selectedBranchId) {
        this.selectedBranchId = selectedBranchId;
    }

    public boolean isEditing() {
        return editing;
    }
}
