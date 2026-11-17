package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Branch;
import in.systemattic.smberp.entity.Customer;
import in.systemattic.smberp.entity.SalesOrder;
import in.systemattic.smberp.entity.SalesOrderItem;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Stateless
public class SalesOrderFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(SalesOrder entity) {
        if (entity.getCustomer() == null || entity.getCustomer().getId() == null) {
            throw new IllegalArgumentException("A customer is required for a sales order.");
        }
        if (entity.getBranch() == null || entity.getBranch().getId() == null) {
            throw new IllegalArgumentException("A branch is required for a sales order.");
        }
        entity.setCustomer(entityManager.getReference(
                Customer.class, entity.getCustomer().getId()));
        entity.setBranch(entityManager.getReference(
                Branch.class, entity.getBranch().getId()));
        entityManager.persist(entity);
    }

    public void edit(SalesOrder entity) {
        if (entity.getCustomer() == null || entity.getCustomer().getId() == null) {
            throw new IllegalArgumentException("A customer is required for a sales order.");
        }
        if (entity.getBranch() == null || entity.getBranch().getId() == null) {
            throw new IllegalArgumentException("A branch is required for a sales order.");
        }
        entity.setCustomer(entityManager.getReference(
                Customer.class, entity.getCustomer().getId()));
        entity.setBranch(entityManager.getReference(
                Branch.class, entity.getBranch().getId()));
        entityManager.merge(entity);
    }

    public void remove(SalesOrder entity) {
        SalesOrder managedEntity = entityManager.merge(entity);
        managedEntity.getItems().clear();
        entityManager.remove(managedEntity);
    }

    public SalesOrder find(Object id) {
        return entityManager.find(SalesOrder.class, id);
    }

    public List<SalesOrder> findAll() {
        return entityManager.createQuery(
                "SELECT salesOrder FROM SalesOrder salesOrder "
                        + "JOIN FETCH salesOrder.customer "
                        + "JOIN FETCH salesOrder.branch",
                SalesOrder.class)
                .getResultList();
    }

    public void calculateTotal(Integer salesOrderId) {
        SalesOrder salesOrder = entityManager.find(SalesOrder.class, salesOrderId);
        if (salesOrder == null) {
            throw new IllegalArgumentException(
                    "Sales order not found: " + salesOrderId);
        }

        BigDecimal total = BigDecimal.ZERO;
        for (SalesOrderItem item : salesOrder.getItems()) {
            BigDecimal lineAmount = item.getQuantity().multiply(item.getUnitPrice());
            if (item.getDiscount() != null
                    && item.getDiscount().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal discountFactor = BigDecimal.ONE.subtract(
                        item.getDiscount().divide(
                                new BigDecimal("100"), 4, RoundingMode.HALF_UP));
                lineAmount = lineAmount.multiply(discountFactor);
            }
            total = total.add(lineAmount);
        }
        salesOrder.setTotalAmount(total.setScale(2, RoundingMode.HALF_UP));
        entityManager.merge(salesOrder);
    }
}
