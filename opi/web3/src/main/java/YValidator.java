import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.math.BigDecimal;

@FacesValidator("yValidator")
public class YValidator implements Validator {

    private static final BigDecimal MIN_VALUE = new BigDecimal("-3");
    private static final BigDecimal MAX_VALUE = new BigDecimal("3");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null || value.toString().trim().isEmpty()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, "Поле пустое"));
        }

        try {
            BigDecimal enteredValue = new BigDecimal(value.toString().replace(',', '.'));

            // Проверка на диапазон
            if (enteredValue.compareTo(MIN_VALUE) < 0 || enteredValue.compareTo(MAX_VALUE) > 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        null, "Число должно быть в диапазоне от -3 до 3"));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid value", "Введено не число"));
        }
    }
}
