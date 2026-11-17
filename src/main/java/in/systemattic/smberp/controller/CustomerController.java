package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.CustomerFacade;
import in.systemattic.smberp.entity.Customer;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CustomerController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private CustomerFacade customerFacade;

    private Customer selected;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Customer();
    }

    public List<Customer> getItems() {
        return customerFacade.findAll();
    }

    public void save() {
        if (editing) {
            customerFacade.edit(selected);
        } else {
            customerFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Customer customer) {
        selected = customer;
        editing = true;
    }

    public void delete(Customer customer) {
        customerFacade.remove(customer);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Customer();
        editing = false;
    }

    public Customer getSelected() {
        return selected;
    }

    public void setSelected(Customer selected) {
        this.selected = selected;
    }

    public boolean isEditing() {
        return editing;
    }
}
