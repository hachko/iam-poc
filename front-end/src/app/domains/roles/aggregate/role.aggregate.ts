import { BehaviorSubject, map, Observable } from "rxjs";
import { Role } from "../model/role.model";
import { RoleService } from "../service/role.service";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class RoleAggregate {
    
    private role$ = new BehaviorSubject<Role[]>([]);

    constructor(private roleService: RoleService) {}

    loadRoles() {
        this.roleService.getRoles().subscribe(roles => this.role$.next(roles));
    }

    get allRoles(): Observable<Role[]> {
        return this.role$.asObservable();
    }

    getAvailableRoles(excludeRoles: Role[]): Observable<Role[]> {
        return this.role$.asObservable().pipe(
            map(allRoles => 
                allRoles.filter(role => 
                    !excludeRoles.find(r => r.id === role.id)
                )
            )
        );
    }
}