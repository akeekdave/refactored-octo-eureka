package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.EmployeeFacade;
import in.systemattic.smberp.entity.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EmployeeController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EmployeeFacade employeeFacade;

    private Employee selected;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Employee();
    }

    public List<Employee> getItems() {
        return employeeFacade.findAll();
    }

    public void save() {
        if (editing) {
            employeeFacade.edit(selected);
        } else {
            employeeFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Employee employee) {
        selected = employee;
        editing = true;
    }

    public void delete(Employee employee) {
        employeeFacade.remove(employee);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Employee();
        editing = false;
    }

    public Employee getSelected() {
        return selected;
    }

    public boolean isEditing() {
        return editing;
    }
}
