import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { User } from '../model/user.model';
import { environment } from '../../../../environments/default.env';

@Injectable({
  providedIn: 'root'
})
export class UserService {
    private apiUrl = environment.apiUrl + '/users';
    constructor(private http: HttpClient) {}

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.apiUrl + '/all').pipe(
            tap(users => console.log('users fetched : ', users)),
            catchError(err => {
                console.log('Error fetching users : ', err);
                return of([]);
            })
        );
    }

    getUserById(id: number): Observable<User> {
        return this.http.get<User>(`${this.apiUrl}/${id}`);
    }

    createUser(user: User): Observable<User> {
        return this.http.post<User>(this.apiUrl + '/create', user).pipe(
            tap(user => console.log('user created successfully : ', user)),
            catchError(err => {
                console.log('failed to create user : ', err);
                return of();
            })
        );
    }

    updateUser(id: number, user: User): Observable<User> {
        return this.http.put<User>(`${this.apiUrl + '/update'}/${id}`, user);
    }

    deleteUser(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl + '/delete'}/${id}`);
    }
}