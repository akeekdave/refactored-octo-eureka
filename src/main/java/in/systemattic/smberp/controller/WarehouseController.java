package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BranchFacade;
import in.systemattic.smberp.ejb.WarehouseFacade;
import in.systemattic.smberp.entity.Branch;
import in.systemattic.smberp.entity.Warehouse;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class WarehouseController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private WarehouseFacade warehouseFacade;

    @Inject
    private BranchFacade branchFacade;

    private Warehouse selected;
    private Integer selectedBranchId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Warehouse();
    }

    public List<Warehouse> getItems() {
        return warehouseFacade.findAll();
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
            warehouseFacade.edit(selected);
        } else {
            warehouseFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Warehouse warehouse) {
        selected = warehouse;
        selectedBranchId = warehouse.getBranch() == null
                ? null : warehouse.getBranch().getId();
        editing = true;
    }

    public void delete(Warehouse warehouse) {
        warehouseFacade.remove(warehouse);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Warehouse();
        selectedBranchId = null;
        editing = false;
    }

    public Warehouse getSelected() {
        return selected;
    }

    public void setSelected(Warehouse selected) {
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
