import { User } from '@models/user';
import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { toSignal } from '@angular/core/rxjs-interop';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  public httpClient = inject(HttpClient);

  public users = toSignal(this.httpClient.get<User[]>('assets/inMemoryUsers.json'), {
    initialValue: [],
  });
}
