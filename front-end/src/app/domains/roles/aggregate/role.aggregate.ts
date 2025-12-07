import { BehaviorSubject } from "rxjs";
import { Role } from "../model/role.model";
import { RoleService } from "../service/role.service";

export class RoleAggregate {
    
    private role$ = new BehaviorSubject<Role[]>([]);

    constructor(private roleService: RoleService) {}

    loadRoles() {
        this.roleService.getRoles().subscribe(roles => this.role$.next(roles));
    }
}