import { User } from '@models/user';

export type LoginResponse = {
  token: string;
  user: User;
};
