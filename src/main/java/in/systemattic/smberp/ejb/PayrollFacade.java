package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Payroll;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class PayrollFacade extends AbstractFacade<Payroll> {

    public PayrollFacade() {
        super(Payroll.class);
    }

    public List<Payroll> findByEmployee(Integer employeeId) {
        return entityManager.createQuery(
                "SELECT p FROM Payroll p WHERE p.employee.id = :employeeId",
                Payroll.class)
                .setParameter("employeeId", employeeId)
                .getResultList();
    }
}
