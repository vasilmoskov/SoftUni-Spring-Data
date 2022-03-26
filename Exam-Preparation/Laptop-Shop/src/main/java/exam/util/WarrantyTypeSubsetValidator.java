package exam.util;

import exam.model.entity.WarrantyTypeEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class WarrantyTypeSubsetValidator implements ConstraintValidator<WarrantyTypeSubset, WarrantyTypeEnum> {
    private WarrantyTypeEnum[] subset;

    @Override
    public void initialize(WarrantyTypeSubset constraintAnnotation) {
       this.subset = constraintAnnotation.anyOf();
    }

    @Override
    public boolean isValid(WarrantyTypeEnum value, ConstraintValidatorContext context) {
        return Arrays.asList(subset).contains(value);
    }
}
