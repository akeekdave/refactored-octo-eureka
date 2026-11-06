package in.systemattic.smberp.ejb;

import in.systemattic.smberp.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductFacade {

    @PersistenceContext(unitName = "smberpPU")
    private EntityManager entityManager;

    public void create(Product entity) {
        entityManager.persist(entity);
    }

    public void edit(Product entity) {
        entityManager.merge(entity);
    }

    public void remove(Product entity) {
        Product managedEntity = entityManager.merge(entity);
        entityManager.remove(managedEntity);
    }

    public Product find(Object id) {
        return entityManager.find(Product.class, id);
    }

    public List<Product> findAll() {
        return entityManager.createQuery(
                "SELECT product FROM Product product", Product.class)
                .getResultList();
    }
}
