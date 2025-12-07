import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/default.env";
import { HttpClient } from "@angular/common/http";
import { Role } from "../model/role.model";
import { catchError, Observable, of, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class RoleService {
    
    private apiUrl = environment.apiUrl + '/roles/'

    constructor(private http: HttpClient) {}

    getRoles(): Observable<Role[]> {
        return this.http.get<Role[]>(this.apiUrl + 'all').pipe(
            tap(roles => console.log('roles fetched : ', roles)),
            catchError(err => {
                console.log('error fetching roles : ', err);
                return of([]);
            })
        );
    }

}