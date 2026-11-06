package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.ManufacturingWorkOrderFacade;
import in.systemattic.smberp.ejb.ProductFacade;
import in.systemattic.smberp.ejb.WorkOrderComponentFacade;
import in.systemattic.smberp.entity.ManufacturingWorkOrder;
import in.systemattic.smberp.entity.Product;
import in.systemattic.smberp.entity.WorkOrderComponent;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class WorkOrderComponentController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private WorkOrderComponentFacade workOrderComponentFacade;

    @Inject
    private ManufacturingWorkOrderFacade manufacturingWorkOrderFacade;

    @Inject
    private ProductFacade productFacade;

    private WorkOrderComponent selected;
    private Integer selectedWorkOrderId;
    private Integer selectedComponentProductId;
    private boolean editing;

    @PostConstruct
    public void init() {
        selected = new WorkOrderComponent();
    }

    public List<WorkOrderComponent> getItems() {
        return workOrderComponentFacade.findAll();
    }

    public List<ManufacturingWorkOrder> getWorkOrders() {
        return manufacturingWorkOrderFacade.findAll();
    }

    public List<Product> getProducts() {
        return productFacade.findAll();
    }

    public void save() {
        ManufacturingWorkOrder workOrder =
                manufacturingWorkOrderFacade.find(selectedWorkOrderId);
        if (workOrder == null) {
            throw new IllegalArgumentException("The selected work order does not exist.");
        }
        Product componentProduct = productFacade.find(selectedComponentProductId);
        if (componentProduct == null) {
            throw new IllegalArgumentException("The selected component product does not exist.");
        }
        selected.setWorkOrder(workOrder);
        selected.setComponentProduct(componentProduct);
        if (editing) {
            workOrderComponentFacade.edit(selected);
        } else {
            workOrderComponentFacade.create(selected);
        }
        resetSelected();
    }

    public void select(WorkOrderComponent component) {
        selected = component;
        selectedWorkOrderId = component.getWorkOrder() == null
                ? null : component.getWorkOrder().getId();
        selectedComponentProductId = component.getComponentProduct() == null
                ? null : component.getComponentProduct().getId();
        editing = true;
    }

    public void delete(WorkOrderComponent component) {
        workOrderComponentFacade.remove(component);
        resetSelected();
    }

    private void resetSelected() {
        selected = new WorkOrderComponent();
        selectedWorkOrderId = null;
        selectedComponentProductId = null;
        editing = false;
    }

    public WorkOrderComponent getSelected() {
        return selected;
    }

    public void setSelected(WorkOrderComponent selected) {
        this.selected = selected;
    }

    public Integer getSelectedWorkOrderId() {
        return selectedWorkOrderId;
    }

    public void setSelectedWorkOrderId(Integer selectedWorkOrderId) {
        this.selectedWorkOrderId = selectedWorkOrderId;
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
