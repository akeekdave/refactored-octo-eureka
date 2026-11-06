package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Employee;
import jakarta.ejb.Stateless;

@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> {

    public EmployeeFacade() {
        super(Employee.class);
    }
}
