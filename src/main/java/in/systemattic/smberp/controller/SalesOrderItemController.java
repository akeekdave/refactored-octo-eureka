package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.ejb.SalesOrderFacade;
import in.systemattic.smberp.ejb.SalesOrderItemFacade;
import in.systemattic.smberp.entity.Product;
import in.systemattic.smberp.entity.SalesOrder;
import in.systemattic.smberp.entity.SalesOrderItem;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class SalesOrderItemController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SalesOrderItemFacade salesOrderItemFacade;

    @Inject
    private SalesOrderFacade salesOrderFacade;

    @Inject
    private ProductFacade productFacade;

    private SalesOrderItem selected;
    private Integer selectedSalesOrderId;
    private Integer selectedProductId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new SalesOrderItem();
    }

    public List<SalesOrderItem> getItems() {
        return salesOrderItemFacade.findAll();
    }

    public void save() {
        Integer previousSalesOrderId = editing && selected.getSalesOrder() != null
                ? selected.getSalesOrder().getId() : null;
        SalesOrder salesOrder = salesOrderFacade.find(selectedSalesOrderId);
        if (salesOrder == null) {
            throw new IllegalArgumentException("The selected sales order does not exist.");
        }
        Product product = productFacade.find(selectedProductId);
        if (product == null) {
            throw new IllegalArgumentException("The selected product does not exist.");
        }

        selected.setSalesOrder(salesOrder);
        selected.setProduct(product);
        if (editing) {
            salesOrderItemFacade.edit(selected);
        } else {
            salesOrderItemFacade.create(selected);
        }
        salesOrderFacade.calculateTotal(salesOrder.getId());
        if (previousSalesOrderId != null
                && !previousSalesOrderId.equals(salesOrder.getId())) {
            salesOrderFacade.calculateTotal(previousSalesOrderId);
        }
        resetSelected();
    }

    public void select(SalesOrderItem item) {
        selected = item;
        selectedSalesOrderId = item.getSalesOrder() == null
                ? null : item.getSalesOrder().getId();
        selectedProductId = item.getProduct() == null
                ? null : item.getProduct().getId();
        editing = true;
    }

    public void delete(SalesOrderItem item) {
        Integer salesOrderId = item.getSalesOrder().getId();
        salesOrderItemFacade.remove(item);
        salesOrderFacade.calculateTotal(salesOrderId);
        resetSelected();
    }

    private void resetSelected() {
        selected = new SalesOrderItem();
        selectedSalesOrderId = null;
        selectedProductId = null;
        editing = false;
    }

    public SalesOrderItem getSelected() {
        return selected;
    }

    public void setSelected(SalesOrderItem selected) {
        this.selected = selected;
    }

    public Integer getSelectedSalesOrderId() {
        return selectedSalesOrderId;
    }

    public void setSelectedSalesOrderId(Integer selectedSalesOrderId) {
        this.selectedSalesOrderId = selectedSalesOrderId;
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
