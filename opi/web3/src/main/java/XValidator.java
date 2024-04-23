import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.math.BigDecimal;

@FacesValidator("xValidator")
public class XValidator implements Validator {

    private static final BigDecimal MIN_VALUE = new BigDecimal("-4");
    private static final BigDecimal MAX_VALUE = new BigDecimal("4");
    private static final BigDecimal STEP = new BigDecimal("0.5");

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, "Поле пустое"));
        }

        try {
            BigDecimal enteredValue = new BigDecimal(value.toString().replace(',', '.'));

            // Проверка на диапазон и шаг
            if (enteredValue.compareTo(MIN_VALUE) < 0 || enteredValue.compareTo(MAX_VALUE) > 0 ||
                    enteredValue.remainder(STEP).compareTo(BigDecimal.ZERO) != 0) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        null, " Число должно быть в диапозоне [-4; 4] с шагом 0.5"));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, "Введено не число"));
        }
    }
}
