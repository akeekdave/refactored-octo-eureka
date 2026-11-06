package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.WorkOrderComponent;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class WorkOrderComponentFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(WorkOrderComponent entity) {
        entityManager.persist(entity);
    }

    public void edit(WorkOrderComponent entity) {
        entityManager.merge(entity);
    }

    public void remove(WorkOrderComponent entity) {
        WorkOrderComponent managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public WorkOrderComponent find(Object id) {
        return entityManager.find(WorkOrderComponent.class, id);
    }

    public List<WorkOrderComponent> findAll() {
        return entityManager.createQuery(
                "SELECT component FROM WorkOrderComponent component",
                WorkOrderComponent.class)
                .getResultList();
    }
}
