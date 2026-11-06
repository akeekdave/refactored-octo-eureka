package in.systemattic.smberp.controller;

import in.systemattic.smberp.ejb.BillOfMaterialsFacade;
import in.systemattic.smberp.ejb.ManufacturingWorkOrderFacade;
import in.systemattic.smberp.ejb.WarehouseFacade;
import in.systemattic.smberp.entity.BillOfMaterials;
import in.systemattic.smberp.entity.ManufacturingWorkOrder;
import in.systemattic.smberp.entity.Warehouse;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ManufacturingWorkOrderController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ManufacturingWorkOrderFacade manufacturingWorkOrderFacade;

    @Inject
    private BillOfMaterialsFacade billOfMaterialsFacade;

    @Inject
    private WarehouseFacade warehouseFacade;

    private ManufacturingWorkOrder selected;
    private Integer selectedBomId;
    private Integer selectedWarehouseId;
    private boolean editing;
    private List<ManufacturingWorkOrder> items;

    @PostConstruct
    public void init() {
        selected = new ManufacturingWorkOrder();
    }

    public List<ManufacturingWorkOrder> getItems() {
        if (items == null) {
            refreshItems();
        }
        return items;
    }

    public List<BillOfMaterials> getBillOfMaterials() {
        return billOfMaterialsFacade.findAll();
    }

    public List<Warehouse> getWarehouses() {
        return warehouseFacade.findAll();
    }

    public void save() {
        BillOfMaterials bom = billOfMaterialsFacade.find(selectedBomId);
        if (bom == null) {
            throw new IllegalArgumentException("The selected bill of materials does not exist.");
        }
        Warehouse warehouse = warehouseFacade.find(selectedWarehouseId);
        if (warehouse == null) {
            throw new IllegalArgumentException("The selected warehouse does not exist.");
        }
        selected.setBom(bom);
        selected.setWarehouse(warehouse);
        if (editing) {
            manufacturingWorkOrderFacade.edit(selected);
        } else {
            manufacturingWorkOrderFacade.create(selected);
        }
        refreshItems();
        resetSelected();
    }

    public void select(ManufacturingWorkOrder workOrder) {
        selected = workOrder;
        selectedBomId = workOrder.getBom() == null ? null : workOrder.getBom().getId();
        selectedWarehouseId = workOrder.getWarehouse() == null
                ? null : workOrder.getWarehouse().getId();
        editing = true;
    }

    public void delete(ManufacturingWorkOrder workOrder) {
        manufacturingWorkOrderFacade.remove(workOrder);
        refreshItems();
        resetSelected();
    }

    public void startProduction(ManufacturingWorkOrder workOrder) {
        try {
            manufacturingWorkOrderFacade.transitionStatus(workOrder.getId(), "IN_PROGRESS");
            refreshItems();
        } catch (Exception exception) {
            Throwable rootCause = exception;
            while (rootCause.getCause() != null) {
                rootCause = rootCause.getCause();
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Production Error: " + rootCause.getMessage(), null));
        }
    }

    public void completeProduction(ManufacturingWorkOrder workOrder) {
        manufacturingWorkOrderFacade.transitionStatus(workOrder.getId(), "COMPLETED");
        refreshItems();
    }

    private void refreshItems() {
        items = new ArrayList<>(manufacturingWorkOrderFacade.findAll());
    }

    private void resetSelected() {
        selected = new ManufacturingWorkOrder();
        selectedBomId = null;
        selectedWarehouseId = null;
        editing = false;
    }

    public ManufacturingWorkOrder getSelected() {
        return selected;
    }

    public void setSelected(ManufacturingWorkOrder selected) {
        this.selected = selected;
    }

    public Integer getSelectedBomId() {
        return selectedBomId;
    }

    public void setSelectedBomId(Integer selectedBomId) {
        this.selectedBomId = selectedBomId;
    }

    public Integer getSelectedWarehouseId() {
        return selectedWarehouseId;
    }

    public void setSelectedWarehouseId(Integer selectedWarehouseId) {
        this.selectedWarehouseId = selectedWarehouseId;
    }

    public boolean isEditing() {
        return editing;
    }
}
