package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.BillOfMaterials;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BillOfMaterialsFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(BillOfMaterials entity) {
        entityManager.persist(entity);
    }

    public void edit(BillOfMaterials entity) {
        entityManager.merge(entity);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(BillOfMaterials entity) {
        Integer bomId = entity.getId();
        if (bomId == null) {
            return;
        }

        entityManager.flush();
        entityManager.createQuery(
                "DELETE FROM WorkOrderComponent component "
                        + "WHERE component.workOrder.id IN "
                        + "(SELECT workOrder.id FROM ManufacturingWorkOrder workOrder "
                        + "WHERE workOrder.bom.id = :bomId)")
                .setParameter("bomId", bomId)
                .executeUpdate();
        entityManager.createQuery(
                "DELETE FROM ManufacturingWorkOrder workOrder "
                        + "WHERE workOrder.bom.id = :bomId")
                .setParameter("bomId", bomId)
                .executeUpdate();
        entityManager.createQuery(
                "DELETE FROM BomItem item WHERE item.bom.id = :bomId")
                .setParameter("bomId", bomId)
                .executeUpdate();

        entityManager.clear();
        BillOfMaterials managedEntity = entityManager.find(BillOfMaterials.class, bomId);
        if (managedEntity == null) {
            return;
        }
        entityManager.remove(managedEntity);
    }

    public BillOfMaterials find(Object id) {
        return entityManager.find(BillOfMaterials.class, id);
    }

    public List<BillOfMaterials> findAll() {
        return entityManager.createQuery(
                "SELECT DISTINCT bom FROM BillOfMaterials bom "
                        + "LEFT JOIN FETCH bom.product "
                        + "LEFT JOIN FETCH bom.items item "
                        + "LEFT JOIN FETCH item.componentProduct",
                BillOfMaterials.class)
                .getResultList();
    }

    public List<BillOfMaterials> findActiveByProduct(Integer productId) {
        return entityManager.createQuery(
                "SELECT bom FROM BillOfMaterials bom "
                        + "WHERE bom.product.id = :productId AND bom.active = true",
                BillOfMaterials.class)
                .setParameter("productId", productId)
                .getResultList();
    }
}
