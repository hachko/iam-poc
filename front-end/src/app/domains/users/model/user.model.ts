import { Role } from "../../roles/model/role.model";

export interface User {
  id: number;
  username: string;
  email: string;
  password: string;
  roles: Role[];
}