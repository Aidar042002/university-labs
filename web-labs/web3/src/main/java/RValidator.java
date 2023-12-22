import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("rValidator")
public class RValidator implements Validator {

    private static final String[] ALLOWED_VALUES = {"1", "1.5", "2", "2.5", "3"};

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String selectedValue = (String) value;

        if (selectedValue == null || !isValidValue(selectedValue)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, "Выберите ровно одно значение из списка (1, 1.5, 2, 2.5, 3).");
            throw new ValidatorException(message);
        }
    }

    private boolean isValidValue(String value) {
        for (String allowedValue : ALLOWED_VALUES) {
            if (allowedValue.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
