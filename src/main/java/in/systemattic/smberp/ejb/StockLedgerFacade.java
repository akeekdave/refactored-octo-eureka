package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.StockLedger;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;

@Stateless
public class StockLedgerFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(StockLedger entity) {
        entityManager.persist(entity);
    }

    public void edit(StockLedger entity) {
        entityManager.merge(entity);
    }

    public void remove(StockLedger entity) {
        StockLedger managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public StockLedger find(Object id) {
        return entityManager.find(StockLedger.class, id);
    }

    public List<StockLedger> findAll() {
        return entityManager.createQuery(
                "SELECT ledger FROM StockLedger ledger "
                        + "JOIN FETCH ledger.product "
                        + "JOIN FETCH ledger.warehouse",
                StockLedger.class)
                .getResultList();
    }

    public BigDecimal getStockBalance(Integer productId, Integer warehouseId) {
        BigDecimal balance = entityManager.createQuery(
                "SELECT SUM(ledger.quantity) FROM StockLedger ledger "
                        + "WHERE ledger.product.id = :productId "
                        + "AND ledger.warehouse.id = :warehouseId",
                BigDecimal.class)
                .setParameter("productId", productId)
                .setParameter("warehouseId", warehouseId)
                .getSingleResult();
        return balance == null ? BigDecimal.ZERO : balance;
    }
}
