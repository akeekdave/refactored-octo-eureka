package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BillOfMaterialsFacade;
import in.systemattic.smberp.ejb.BomItemFacade;
import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.entity.BillOfMaterials;
import in.systemattic.smberp.entity.BomItem;
import in.systemattic.smberp.entity.Product;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class BomItemController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private BomItemFacade bomItemFacade;

    @Inject
    private BillOfMaterialsFacade billOfMaterialsFacade;

    @Inject
    private ProductFacade productFacade;

    private BomItem selected;
    private Integer selectedBomId;
    private Integer selectedComponentProductId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new BomItem();
    }

    public List<BomItem> getItems() {
        return bomItemFacade.findAll();
    }

    public List<BillOfMaterials> getBillOfMaterials() {
        return billOfMaterialsFacade.findAll();
    }

    public List<Product> getProducts() {
        return productFacade.findAll();
    }

    public void save() {
        BillOfMaterials billOfMaterials = billOfMaterialsFacade.find(selectedBomId);
        if (billOfMaterials == null) {
            throw new IllegalArgumentException("The selected bill of materials does not exist.");
        }
        Product componentProduct = productFacade.find(selectedComponentProductId);
        if (componentProduct == null) {
            throw new IllegalArgumentException("The selected component product does not exist.");
        }
        selected.setBom(billOfMaterials);
        selected.setComponentProduct(componentProduct);
        if (editing) {
            bomItemFacade.edit(selected);
        } else {
            bomItemFacade.create(selected);
        }
        resetSelected();
    }

    public void select(BomItem bomItem) {
        selected = bomItem;
        selectedBomId = bomItem.getBom() == null ? null : bomItem.getBom().getId();
        selectedComponentProductId = bomItem.getComponentProduct() == null
                ? null : bomItem.getComponentProduct().getId();
        editing = true;
    }

    public void delete(BomItem bomItem) {
        bomItemFacade.remove(bomItem);
        resetSelected();
    }

    private void resetSelected() {
        selected = new BomItem();
        selectedBomId = null;
        selectedComponentProductId = null;
        editing = false;
    }

    public BomItem getSelected() {
        return selected;
    }

    public void setSelected(BomItem selected) {
        this.selected = selected;
    }

    public Integer getSelectedBomId() {
        return selectedBomId;
    }

    public void setSelectedBomId(Integer selectedBomId) {
        this.selectedBomId = selectedBomId;
    }

    public Integer getSelectedComponentProductId() {
        return selectedComponentProductId;
    }

    public void setSelectedComponentProductId(Integer selectedComponentProductId) {
        this.selectedComponentProductId = selectedComponentProductId;
    }

    public boolean isEditing() {
        return editing;
    }
}
