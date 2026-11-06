package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.BomItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BomItemFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(BomItem entity) {
        entityManager.persist(entity);
    }

    public void edit(BomItem entity) {
        entityManager.merge(entity);
    }

    public void remove(BomItem entity) {
        BomItem managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public BomItem find(Object id) {
        return entityManager.find(BomItem.class, id);
    }

    public List<BomItem> findAll() {
        return entityManager.createQuery(
                "SELECT bomItem FROM BomItem bomItem", BomItem.class)
                .getResultList();
    }
}
