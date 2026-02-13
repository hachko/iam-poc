import { Injectable } from "@angular/core";
import { User } from "../../users/model/user.model";
import { BehaviorSubject, catchError, EMPTY, Observable, of, tap, throwError } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../../environments/default.env";
import { MessageService } from "../message/service/message.service";

@Injectable({providedIn: 'root'})
export class AuthService {
    private apiUrl= environment.apiUrl + '/auth';

    private currentUser$$ = new BehaviorSubject<User | null>(
        this.loadFromSessionStorage()
    )

    public currentUser$ = this.currentUser$$.asObservable();

    constructor(private httpClient: HttpClient, private msgService: MessageService) {}

    login(username: string, password:string): Observable<User> {
        console.log('service login call with user/pwd : ', username, password);
        return this.httpClient.post<User>(
            this.apiUrl + '/login', {username, password}, { withCredentials: true }
        ).pipe(
            tap(user => {
                this.currentUser$$.next(user);
                console.log('authenticated user : ', user);
                sessionStorage.setItem('currentUser', JSON.stringify(user))
            }), catchError(error => {                
                this.msgService.show({type: 'error', text: 'login failed'});
                return throwError(() => error);
            })
        );
    }

    logout(): Observable<any> {
        return this.httpClient.post(this.apiUrl + '/logout', {}, {withCredentials: true}).pipe(
            tap(() => {
                this.currentUser$$.next(null);
                sessionStorage.removeItem('currentUser');
            }), catchError(error => {
                console.log('logout error : ', error);
                this.msgService.show({type: 'error', text: 'logout failed'})
                return EMPTY;
            })            
        );
    }

    isLoggedIn(): boolean {
        return this.currentUser$$.value !== null;
    }

    hasRole(rolename: string): boolean {
        const rolenames = this.currentUser$$.value?.roles.map(role => role.name) || [];        
        return rolenames.includes(rolename);
    }

    getCurrentUser(): User | null {
        return this.currentUser$$.value;
    }

    private loadFromSessionStorage(): User | null {
        const stored = sessionStorage.getItem('CurrentUser');
        return stored ? JSON.parse(stored) : null;
    }    
}