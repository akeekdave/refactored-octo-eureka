package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BranchFacade;
import in.systemattic.smberp.ejb.CustomerFacade;
import in.systemattic.smberp.ejb.SalesOrderFacade;
import in.systemattic.smberp.entity.Branch;
import in.systemattic.smberp.entity.Customer;
import in.systemattic.smberp.entity.SalesOrder;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SalesOrderController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SalesOrderFacade salesOrderFacade;

    @Inject
    private CustomerFacade customerFacade;

    @Inject
    private BranchFacade branchFacade;

    private SalesOrder selected;
    private Integer selectedCustomerId;
    private Integer selectedBranchId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new SalesOrder();
    }

    public List<SalesOrder> getItems() {
        return salesOrderFacade.findAll();
    }

    public List<Customer> getCustomers() {
        return customerFacade.findAll();
    }

    public List<Branch> getBranches() {
        return branchFacade.findAll();
    }

    public void save() {
        Customer customer = customerFacade.find(selectedCustomerId);
        if (customer == null) {
            throw new IllegalArgumentException("The selected customer does not exist.");
        }
        Branch branch = branchFacade.find(selectedBranchId);
        if (branch == null) {
            throw new IllegalArgumentException("The selected branch does not exist.");
        }
        selected.setCustomer(customer);
        selected.setBranch(branch);
        if (editing) {
            salesOrderFacade.edit(selected);
        } else {
            salesOrderFacade.create(selected);
        }
        resetSelected();
    }

    public void select(SalesOrder salesOrder) {
        selected = salesOrder;
        selectedCustomerId = salesOrder.getCustomer() == null
                ? null : salesOrder.getCustomer().getId();
        selectedBranchId = salesOrder.getBranch() == null
                ? null : salesOrder.getBranch().getId();
        editing = true;
    }

    public void delete(SalesOrder salesOrder) {
        salesOrderFacade.remove(salesOrder);
        resetSelected();
    }

    private void resetSelected() {
        selected = new SalesOrder();
        selectedCustomerId = null;
        selectedBranchId = null;
        editing = false;
    }

    public SalesOrder getSelected() {
        return selected;
    }

    public void setSelected(SalesOrder selected) {
        this.selected = selected;
    }

    public Integer getSelectedCustomerId() {
        return selectedCustomerId;
    }

    public void setSelectedCustomerId(Integer selectedCustomerId) {
        this.selectedCustomerId = selectedCustomerId;
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
