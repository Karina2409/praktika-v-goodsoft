import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { loginValidator } from '@validators/login';
import { passwordValidator } from '@validators/password';
import { LoginFieldComponent, PasswordFieldComponent } from '@components/input';
import { ButtonModule } from 'primeng/button';
import { AuthService } from '@services/auth';
import { Router } from '@angular/router';

@Component({
  selector: 'app-auth-page',
  imports: [LoginFieldComponent, PasswordFieldComponent, ReactiveFormsModule, ButtonModule],
  templateUrl: './login-page.component.html',
  styleUrl: './login-page.component.scss',
})
export class LoginPageComponent {
  public loginService = inject(AuthService);
  public router = inject(Router);

  public form: FormGroup = new FormGroup({
    login: new FormControl<string>('', {
      nonNullable: true,
      validators: [Validators.required, loginValidator],
    }),
    password: new FormControl<string>('', {
      nonNullable: true,
      validators: [Validators.required, passwordValidator],
    }),
  });

  public get login(): FormControl {
    return this.form.get('login') as FormControl;
  }

  public get password(): FormControl {
    return this.form.get('password') as FormControl;
  }

  public async onSubmitAction(): Promise<void> {
    const isLogin = this.loginService.login(this.login.value, this.password.value);
    if (isLogin) {
      await this.router.navigate(['main']);
    }
  }
}
