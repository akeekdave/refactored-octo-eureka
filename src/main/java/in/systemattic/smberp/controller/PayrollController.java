package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.EmployeeFacade;
import in.systemattic.smberp.ejb.PayrollFacade;
import in.systemattic.smberp.entity.Payroll;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PayrollController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private PayrollFacade payrollFacade;

    @Inject
    private EmployeeFacade employeeFacade;

    private Payroll selected;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Payroll();
    }

    public List<Payroll> getItems() {
        return payrollFacade.findAll();
    }

    public void save() {
        if (selected.getEmployee() == null) {
            throw new IllegalArgumentException("An employee must be selected.");
        }
        if (editing) {
            payrollFacade.edit(selected);
        } else {
            payrollFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Payroll payroll) {
        selected = payroll;
        editing = true;
    }

    public void delete(Payroll payroll) {
        payrollFacade.remove(payroll);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Payroll();
        editing = false;
    }

    public Payroll getSelected() {
        return selected;
    }

    public void setSelected(Payroll selected) {
        this.selected = selected;
    }

    public boolean isEditing() {
        return editing;
    }
}
