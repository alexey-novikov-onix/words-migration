package com.onix.wordsmigration.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public final class CreatedConstraintValidator implements ConstraintValidator<Created, Date> {

    @Override
    public boolean isValid(final Date date, final ConstraintValidatorContext context) {
        if (Objects.nonNull(date)) {
            final Date startDate = new Date(new Calendar.Builder()
                    .setDate(2015, 0, 1)
                    .build()
                    .getTimeInMillis()
            );

            return date.after(startDate) || date.equals(startDate);
        }

        return false;
    }

}
