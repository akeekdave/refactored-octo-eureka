package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Warehouse;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class WarehouseFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(Warehouse entity) {
        entityManager.persist(entity);
    }

    public void edit(Warehouse entity) {
        entityManager.merge(entity);
    }

    public void remove(Warehouse entity) {
        Warehouse managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public Warehouse find(Object id) {
        return entityManager.find(Warehouse.class, id);
    }

    public List<Warehouse> findAll() {
        return entityManager.createQuery(
                "SELECT warehouse FROM Warehouse warehouse", Warehouse.class)
                .getResultList();
    }

    public List<Warehouse> findByBranch(Integer branchId) {
        return entityManager.createQuery(
                "SELECT warehouse FROM Warehouse warehouse WHERE warehouse.branch.id = :branchId",
                Warehouse.class)
                .setParameter("branchId", branchId)
                .getResultList();
    }
}
