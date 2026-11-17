package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.SalesOrderItem;
import in.systemattic.smberp.entity.Product;
import in.systemattic.smberp.entity.SalesOrder;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SalesOrderItemFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(SalesOrderItem entity) {
        setManagedRelationships(entity);
        entityManager.persist(entity);
    }

    public void edit(SalesOrderItem entity) {
        setManagedRelationships(entity);
        entityManager.merge(entity);
    }

    public void remove(SalesOrderItem entity) {
        SalesOrderItem managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public SalesOrderItem find(Object id) {
        return entityManager.find(SalesOrderItem.class, id);
    }

    public List<SalesOrderItem> findAll() {
        return entityManager.createQuery(
                "SELECT item FROM SalesOrderItem item "
                        + "JOIN FETCH item.salesOrder "
                        + "JOIN FETCH item.product",
                SalesOrderItem.class)
                .getResultList();
    }

    private void setManagedRelationships(SalesOrderItem entity) {
        if (entity.getSalesOrder() == null || entity.getSalesOrder().getId() == null) {
            throw new IllegalArgumentException("A sales order is required for a sales item.");
        }
        if (entity.getProduct() == null || entity.getProduct().getId() == null) {
            throw new IllegalArgumentException("A product is required for a sales item.");
        }
        entity.setSalesOrder(entityManager.getReference(
                SalesOrder.class, entity.getSalesOrder().getId()));
        entity.setProduct(entityManager.getReference(
                Product.class, entity.getProduct().getId()));
    }
}
