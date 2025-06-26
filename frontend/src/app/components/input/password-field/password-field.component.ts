import { Component, Input, signal, WritableSignal } from '@angular/core';
import { FormControl, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { PasswordModule } from 'primeng/password';
import { DividerModule } from 'primeng/divider';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddon } from 'primeng/inputgroupaddon';

@Component({
  selector: 'app-password-field',
  imports: [
    ReactiveFormsModule,
    FormsModule,
    PasswordModule,
    DividerModule,
    FloatLabelModule,
    InputGroupModule,
    InputGroupAddon,
  ],
  templateUrl: './password-field.component.html',
  styleUrl: '../input-field.scss',
})
export class PasswordFieldComponent {
  @Input({ required: true }) public control!: FormControl<string | null>;

  public isPasswordShown: WritableSignal<boolean> = signal(false);

  public togglePasswordVisibility(): void {
    this.isPasswordShown.update((value) => !value);
  }
}
