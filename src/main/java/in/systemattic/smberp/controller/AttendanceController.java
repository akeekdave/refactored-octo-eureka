package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.AttendanceFacade;
import in.systemattic.smberp.ejb.EmployeeFacade;
import in.systemattic.smberp.entity.Attendance;
import in.systemattic.smberp.entity.Employee;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AttendanceController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AttendanceFacade attendanceFacade;

    @Inject
    private EmployeeFacade employeeFacade;

    private Attendance selected;
    private Integer selectedEmployeeId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Attendance();
    }

    public List<Attendance> getItems() {
        return attendanceFacade.findAll();
    }

    public void save() {
        Employee employee = employeeFacade.find(selectedEmployeeId);
        if (employee == null) {
            throw new IllegalArgumentException("The selected employee does not exist.");
        }
        selected.setEmployee(employee);
        if (editing) {
            attendanceFacade.edit(selected);
        } else {
            attendanceFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Attendance attendance) {
        selected = attendance;
        selectedEmployeeId = attendance.getEmployee() == null
                ? null : attendance.getEmployee().getId();
        editing = true;
    }

    public void delete(Attendance attendance) {
        attendanceFacade.remove(attendance);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Attendance();
        selectedEmployeeId = null;
        editing = false;
    }

    public Attendance getSelected() {
        return selected;
    }

    public Integer getSelectedEmployeeId() {
        return selectedEmployeeId;
    }

    public void setSelectedEmployeeId(Integer selectedEmployeeId) {
        this.selectedEmployeeId = selectedEmployeeId;
    }

    public boolean isEditing() {
        return editing;
    }
}
