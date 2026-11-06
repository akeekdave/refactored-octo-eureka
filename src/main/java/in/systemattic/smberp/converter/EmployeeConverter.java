package in.systemattic.smberp.converter;

import in.systemattic.smberp.ejb.EmployeeFacade;
import in.systemattic.smberp.entity.Employee;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("employeeConverterBean")
@RequestScoped
@FacesConverter(value = "employeeConverter", managed = true)
public class EmployeeConverter implements Converter<Employee> {

    @Inject
    private EmployeeFacade employeeFacade;

    @Override
    public Employee getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        final Integer employeeId;
        try {
            employeeId = Integer.valueOf(value);
        } catch (NumberFormatException exception) {
            throw new ConverterException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Invalid employee selection.", null), exception);
        }

        Employee employee = employeeFacade.find(employeeId);
        if (employee == null) {
            throw new ConverterException(new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "The selected employee does not exist.", null));
        }
        return employee;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Employee employee) {
        return employee == null || employee.getId() == null ? "" : employee.getId().toString();
    }
}
