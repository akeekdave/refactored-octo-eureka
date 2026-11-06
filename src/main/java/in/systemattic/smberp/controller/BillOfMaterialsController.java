package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BillOfMaterialsFacade;
import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.entity.BillOfMaterials;
import in.systemattic.smberp.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BillOfMaterialsController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BillOfMaterialsFacade billOfMaterialsFacade;

    @Inject
    private ProductFacade productFacade;

    private BillOfMaterials selected;
    private Integer selectedProductId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new BillOfMaterials();
    }

    public List<BillOfMaterials> getItems() {
        return billOfMaterialsFacade.findAll();
    }

    public List<Product> getProducts() {
        return productFacade.findAll();
    }

    public void save() {
        Product product = productFacade.find(selectedProductId);
        if (product == null) {
            throw new IllegalArgumentException("The selected product does not exist.");
        }
        selected.setProduct(product);
        if (editing) {
            billOfMaterialsFacade.edit(selected);
        } else {
            billOfMaterialsFacade.create(selected);
        }
        resetSelected();
    }

    public void select(BillOfMaterials billOfMaterials) {
        selected = billOfMaterials;
        selectedProductId = billOfMaterials.getProduct() == null
                ? null : billOfMaterials.getProduct().getId();
        editing = true;
    }

    public void delete(BillOfMaterials billOfMaterials) {
        billOfMaterialsFacade.remove(billOfMaterials);
        resetSelected();
    }

    private void resetSelected() {
        selected = new BillOfMaterials();
        selectedProductId = null;
        editing = false;
    }

    public BillOfMaterials getSelected() {
        return selected;
    }

    public void setSelected(BillOfMaterials selected) {
        this.selected = selected;
    }

    public Integer getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Integer selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public boolean isEditing() {
        return editing;
    }
}
