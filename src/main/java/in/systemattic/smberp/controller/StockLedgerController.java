package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.ejb.StockLedgerFacade;
import in.systemattic.smberp.ejb.WarehouseFacade;
import in.systemattic.smberp.entity.Product;
import in.systemattic.smberp.entity.StockLedger;
import in.systemattic.smberp.entity.Warehouse;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Named
@ViewScoped
public class StockLedgerController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private StockLedgerFacade stockLedgerFacade;

    @Inject
    private ProductFacade productFacade;

    @Inject
    private WarehouseFacade warehouseFacade;

    private StockLedger selected;
    private Integer selectedProductId;
    private Integer selectedWarehouseId;

    @PostConstruct
    public void init() {
        selected = new StockLedger();
    }

    public List<StockLedger> getItems() {
        return stockLedgerFacade.findAll();
    }

    public void save() {
        Product product = productFacade.find(selectedProductId);
        if (product == null) {
            throw new IllegalArgumentException("The selected product does not exist.");
        }
        Warehouse warehouse = warehouseFacade.find(selectedWarehouseId);
        if (warehouse == null) {
            throw new IllegalArgumentException("The selected warehouse does not exist.");
        }

        selected.setProduct(product);
        selected.setWarehouse(warehouse);
        selected.setTransactionDate(LocalDateTime.now());
        stockLedgerFacade.create(selected);
        resetSelected();
    }

    public void delete(StockLedger item) {
        stockLedgerFacade.remove(item);
        resetSelected();
    }

    public BigDecimal checkBalance(Integer productId, Integer warehouseId) {
        return stockLedgerFacade.getStockBalance(productId, warehouseId);
    }

    private void resetSelected() {
        selected = new StockLedger();
        selectedProductId = null;
        selectedWarehouseId = null;
    }

    public StockLedger getSelected() {
        return selected;
    }

    public void setSelected(StockLedger selected) {
        this.selected = selected;
    }

    public Integer getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(Integer selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public Integer getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(Integer selectedWarehouseId) {
        this.selectedWarehouseId = selectedWarehouseId;
    }
}
