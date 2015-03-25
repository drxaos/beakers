package beakers.system.errors.domain

import beakers.system.errors.ServiceException
import org.springframework.validation.BeanPropertyBindingResult

class ValidationErrors extends ServiceException {
    BeanPropertyBindingResult errors

    ValidationErrors(BeanPropertyBindingResult errors) {
        super("illegal-arguments")
        this.errors = errors
        //errors.allErrors.codes.flatten()
    }
}
