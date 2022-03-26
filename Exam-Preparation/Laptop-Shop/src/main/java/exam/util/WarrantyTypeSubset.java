package exam.util;

import exam.model.entity.WarrantyTypeEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WarrantyTypeSubsetValidator.class)
public @interface WarrantyTypeSubset {
    WarrantyTypeEnum[] anyOf();
    String message() default "Invalid warranty type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
