package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(Customer entity) {
        entityManager.persist(entity);
    }

    public void edit(Customer entity) {
        entityManager.merge(entity);
    }

    public void remove(Customer entity) {
        Customer managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public Customer find(Object id) {
        return entityManager.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return entityManager.createQuery(
                "SELECT customer FROM Customer customer", Customer.class)
                .getResultList();
    }
}
