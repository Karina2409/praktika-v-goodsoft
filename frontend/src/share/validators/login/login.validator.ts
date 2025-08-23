import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

const latinNumberPattern = /^[A-Za-z0-9]+$/;

export const loginValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
  const value = control.value;
  if (!value) return null;

  const errors: ValidationErrors = {};

  if (!latinNumberPattern.test(value)) {
    errors['notOnlyLatinAndNumbers'] = true;
  }

  return Object.keys(errors).length ? errors : null;
};
