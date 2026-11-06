package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.ProductCategoryFacade;
import in.systemattic.smberp.entity.ProductCategory;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProductCategoryController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ProductCategoryFacade productCategoryFacade;

    private ProductCategory selected;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new ProductCategory();
    }

    public List<ProductCategory> getItems() {
        return productCategoryFacade.findAll();
    }

    public void save() {
        if (editing) {
            productCategoryFacade.edit(selected);
        } else {
            productCategoryFacade.create(selected);
        }
        resetSelected();
    }

    public void select(ProductCategory productCategory) {
        selected = productCategory;
        editing = true;
    }

    public void delete(ProductCategory productCategory) {
        productCategoryFacade.remove(productCategory);
        resetSelected();
    }

    private void resetSelected() {
        selected = new ProductCategory();
        editing = false;
    }

    public ProductCategory getSelected() {
        return selected;
    }

    public void setSelected(ProductCategory selected) {
        this.selected = selected;
    }

    public boolean isEditing() {
        return editing;
    }
}
