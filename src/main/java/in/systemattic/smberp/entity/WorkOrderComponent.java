package in.systemattic.smberp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "work_order_components")
public class WorkOrderComponent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private ManufacturingWorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_product_id", nullable = false)
    private Product componentProduct;

    @Column(name = "quantity_required", nullable = false, precision = 12, scale = 4)
    private BigDecimal quantityRequired;

    @Column(name = "quantity_issued", nullable = false, precision = 12, scale = 4)
    private BigDecimal quantityIssued = BigDecimal.ZERO;

    public WorkOrderComponent() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ManufacturingWorkOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(ManufacturingWorkOrder workOrder) {
        this.workOrder = workOrder;
    }

    public Product getComponentProduct() {
        return componentProduct;
    }

    public void setComponentProduct(Product componentProduct) {
        this.componentProduct = componentProduct;
    }

    public BigDecimal getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(BigDecimal quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public BigDecimal getQuantityIssued() {
        return quantityIssued;
    }

    public void setQuantityIssued(BigDecimal quantityIssued) {
        this.quantityIssued = quantityIssued;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        WorkOrderComponent that = (WorkOrderComponent) object;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "WorkOrderComponent{id=" + id
                + ", workOrderId=" + (workOrder == null ? null : workOrder.getId())
                + ", componentProductId=" + (componentProduct == null ? null : componentProduct.getId())
                + ", quantityRequired=" + quantityRequired
                + ", quantityIssued=" + quantityIssued
                + '}';
    }
}
