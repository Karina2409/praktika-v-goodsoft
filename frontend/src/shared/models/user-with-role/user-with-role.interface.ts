import { User } from '@models/user';

export type UserWithRole = {
  user: User;
  roles: string[];
};
