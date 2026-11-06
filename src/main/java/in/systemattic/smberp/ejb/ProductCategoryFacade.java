package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.ProductCategory;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductCategoryFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(ProductCategory entity) {
        entityManager.persist(entity);
    }

    public void edit(ProductCategory entity) {
        entityManager.merge(entity);
    }

    public void remove(ProductCategory entity) {
        ProductCategory managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public ProductCategory find(Object id) {
        return entityManager.find(ProductCategory.class, id);
    }

    public List<ProductCategory> findAll() {
        return entityManager.createQuery(
                "SELECT category FROM ProductCategory category", ProductCategory.class)
                .getResultList();
    }
}
