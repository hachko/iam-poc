import { Role } from "../../roles/model/role.model";

export interface User {
  id: number;
  username: string;
  email: string;
  roles: Role
}