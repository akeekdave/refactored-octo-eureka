package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Department;
import jakarta.ejb.Stateless;

@Stateless
public class DepartmentFacade extends AbstractFacade<Department> {

    public DepartmentFacade() {
        super(Department.class);
    }
}
