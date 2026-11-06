package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.ProductCategoryFacade;
import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.entity.Product;
import in.systemattic.smberp.entity.ProductCategory;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProductController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductFacade productFacade;

    @Inject
    private ProductCategoryFacade productCategoryFacade;

    private Product selected;
    private Integer selectedCategoryId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new Product();
    }

    public List<Product> getItems() {
        return productFacade.findAll();
    }

    public List<ProductCategory> getCategories() {
        return productCategoryFacade.findAll();
    }

    public void save() {
        ProductCategory category = productCategoryFacade.find(selectedCategoryId);
        if (category == null) {
            throw new IllegalArgumentException("The selected product category does not exist.");
        }
        selected.setCategory(category);
        if (editing) {
            productFacade.edit(selected);
        } else {
            productFacade.create(selected);
        }
        resetSelected();
    }

    public void select(Product product) {
        selected = product;
        selectedCategoryId = product.getCategory() == null
                ? null : product.getCategory().getId();
        editing = true;
    }

    public void delete(Product product) {
        productFacade.remove(product);
        resetSelected();
    }

    private void resetSelected() {
        selected = new Product();
        selectedCategoryId = null;
        editing = false;
    }

    public Product getSelected() {
        return selected;
    }

    public void setSelected(Product selected) {
        this.selected = selected;
    }

    public Integer getSelectedCategoryId() {
        return selectedCategoryId;
    }

    public void setSelectedCategoryId(Integer selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
    }

    public boolean isEditing() {
        return editing;
    }
}
