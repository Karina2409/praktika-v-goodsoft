import { Component, Input } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { KeyFilterModule } from 'primeng/keyfilter';

@Component({
  selector: 'app-login-field',
  imports: [FloatLabelModule, FormsModule, InputTextModule, ReactiveFormsModule, KeyFilterModule],
  templateUrl: './login-field.component.html',
  styleUrl: '../input-field.scss',
})
export class LoginFieldComponent {
  @Input({ required: true }) public control!: FormControl<string>;
}
