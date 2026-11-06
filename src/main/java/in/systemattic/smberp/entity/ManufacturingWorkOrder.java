package in.systemattic.smberp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manufacturing_work_orders")
public class ManufacturingWorkOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_id", nullable = false)
    private BillOfMaterials bom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(name = "work_order_number", unique = true, nullable = true, length = 50)
    private String workOrderNumber;

    @Column(name = "quantity_target", nullable = false, precision = 12, scale = 4)
    private BigDecimal quantityTarget;

    @Column(name = "quantity_produced", nullable = false, precision = 12, scale = 4)
    private BigDecimal quantityProduced = BigDecimal.ZERO;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "PLANNED";

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkOrderComponent> components = new ArrayList<>();

    public ManufacturingWorkOrder() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BillOfMaterials getBom() {
        return bom;
    }

    public void setBom(BillOfMaterials bom) {
        this.bom = bom;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public BigDecimal getQuantityTarget() {
        return quantityTarget;
    }

    public void setQuantityTarget(BigDecimal quantityTarget) {
        this.quantityTarget = quantityTarget;
    }

    public BigDecimal getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(BigDecimal quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<WorkOrderComponent> getComponents() {
        return components;
    }

    public void setComponents(List<WorkOrderComponent> components) {
        this.components = components;
    }

    public void addComponent(WorkOrderComponent component) {
        components.add(component);
        component.setWorkOrder(this);
    }

    public void removeComponent(WorkOrderComponent component) {
        if (components.remove(component)) {
            component.setWorkOrder(null);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ManufacturingWorkOrder that = (ManufacturingWorkOrder) object;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "ManufacturingWorkOrder{id=" + id
                + ", bomId=" + (bom == null ? null : bom.getId())
                + ", warehouseId=" + (warehouse == null ? null : warehouse.getId())
                + ", workOrderNumber='" + workOrderNumber + '\''
                + ", quantityTarget=" + quantityTarget
                + ", quantityProduced=" + quantityProduced
                + ", status='" + status + '\''
                + ", startDate=" + startDate
                + ", endDate=" + endDate
                + '}';
    }
}
