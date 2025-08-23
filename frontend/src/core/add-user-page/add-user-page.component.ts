import { Component, inject, signal } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { loginValidator } from '@validators/login';
import { passwordValidator } from '@validators/password';
import { UserService } from '@services/user';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from '@models/user';
import { ButtonModule } from 'primeng/button';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputNumberModule } from 'primeng/inputnumber';
import { DatePickerModule } from 'primeng/datepicker';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { DatePipe, CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-user-page',
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    DatePickerModule,
    InputNumberModule,
    MultiSelectModule,
    ButtonModule,
    CommonModule,
  ],
  providers: [DatePipe],
  templateUrl: './add-user-page.component.html',
  styleUrl: './add-user-page.component.scss',
})
export class AddUserPageComponent {
  private formBuilder = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private userService = inject(UserService);
  private router = inject(Router);
  private datePipe = inject(DatePipe);

  public editMode = signal(false);

  public roles = [
    { label: 'Администратор', value: 'Администратор' },
    { label: 'Менеджер', value: 'Менеджер' },
    { label: 'Пользователь', value: 'Пользователь' },
    { label: 'Гость', value: 'Гость' },
    { label: 'Модератор', value: 'Модератор' },
  ];

  public form = this.formBuilder.group({
    login: ['', [Validators.required, loginValidator]],
    password: ['', [Validators.required, Validators.minLength(8), passwordValidator]],
    name: ['', [Validators.required]],
    birthday: ['', [Validators.required]],
    salary: [0, [Validators.required, Validators.min(0)]],
    roles: [[''], [Validators.required]],
  });

  public get login(): FormControl {
    return this.form.get('login') as FormControl;
  }

  public get password(): FormControl {
    return this.form.get('password') as FormControl;
  }

  public get name(): FormControl {
    return this.form.get('name') as FormControl;
  }

  public get birthday(): FormControl {
    return this.form.get('birthday') as FormControl;
  }

  public get salary(): FormControl {
    return this.form.get('salary') as FormControl;
  }

  public get rolesF(): FormControl {
    return this.form.get('roles') as FormControl;
  }

  constructor() {
    const login = this.route.snapshot.paramMap.get('login');
    if (login) {
      this.editMode.set(true);
      this.userService.findUserByLogin(login).then((user) => {
        if (user) {
          this.form.patchValue({
            login: user.login,
            password: user.password,
            name: user.name,
            birthday: user.birthday ? this.datePipe.transform(user.birthday, 'yyyy-MM-dd') : null,
            salary: user.salary,
            roles: user.roles,
          });
          this.form.get('password')?.disable();
        }
      });
    }
  }

  public async save() {
    if (this.form.invalid) return;
    const user: User = {
      id: Date.now(),
      login: this.login.value,
      password: this.password.value,
      name: this.name.value,
      birthday: this.birthday.value,
      age: this.calculateAge(this.birthday.value),
      salary: this.salary.value,
      roles: this.rolesF.value,
    };

    if (this.editMode()) {
      await this.userService.updateUser(user);
    } else {
      await this.userService.addUser(user);
    }

    this.router.navigate(['/users']);
  }

  private calculateAge(date: string | Date | null): number {
    void this;

    if (!date) return 0;

    const d = typeof date === 'string' ? new Date(date) : date;
    if (isNaN(d.getTime())) return 0;

    const today = new Date();
    let age = today.getFullYear() - d.getFullYear();
    const m = today.getMonth() - d.getMonth();

    if (m < 0 || (m === 0 && today.getDate() < d.getDate())) {
      age--;
    }

    return age;
  }

  public get maxBirthdayDate(): Date {
    void this;
    const today = new Date();
    today.setFullYear(today.getFullYear() - 18);
    return today;
  }
}
