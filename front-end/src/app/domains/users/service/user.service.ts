import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, of, tap } from 'rxjs';
import { User } from '../model/user.model';
import { environment } from '../../../../environments/default.env';
import { MessageService } from '../../shared/message/service/message.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
    private apiUrl = environment.apiUrl + '/users';
    constructor(private http: HttpClient, private ms: MessageService) {}

    getUsers(): Observable<User[]> {
        return this.http.get<User[]>(this.apiUrl + '/all').pipe(
            tap(users => {
                console.log('users fetched : ', users);
                const text = 'users sucessfully fetched ! ';
                this.ms.show({type: 'success', text})
            }),
            catchError(err => {
                const text = 'Failed to fetch users';
                this.ms.show({ type: 'error', text });
                console.log('Error fetching users : ', err);
                return of([]);
            })
        );
    }

    getUserById(id: number): Observable<User> {
        return this.http.get<User>(`${this.apiUrl}/${id}`).pipe(
            tap(user => {
                this.ms.show({type: 'success', text: 'user ' + user.username + 'fetched'})
            }),
            catchError(err => {
                const text = 'failed to fetch user id : ' + id;
                this.ms.show({type: 'error', text});
                console.log('error fetching user id : ' + id + 'error : ', err);
                return of();
            })
        );
    }

    createUser(user: User): Observable<User> {
        return this.http.post<User>(this.apiUrl + '/create', user).pipe(
            tap(user => {
                console.log('user created successfully : ', user);
                const text = 'user' +  user.username + 'created successfully';
                this.ms.show({type: 'success', text })
            }),
            catchError(err => {
                const text = 'Failed to fetch users';
                this.ms.show({ type: 'error', text });
                console.log('failed to create user : ', err);
                return of();
            })
        );
    }

    updateUser(id: number, user: User): Observable<User> {
        return this.http.put<User>(`${this.apiUrl + '/update'}/${id}`, user).pipe(
            tap(usr => {
                this.ms.show({type: 'success', text: 'user ' + usr.username + ' updated successfully'})
            }),
            catchError(err => {
                const text = 'Failed to update user : ' + user.username;
                this.ms.show({ type: 'error', text });
                console.log('user update failed, error : ', err);
                return of();
            })
        );
    }

    deleteUser(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl + '/delete'}/${id}`).pipe(
            tap(() => {
                const text = 'user id : ' + id + ' deleted successfully';
                this.ms.show({ type: 'success', text });
            }),
            catchError(err => {
                const text = 'Failed to delete user id : ' + id;
                this.ms.show({ type: 'error', text });
                console.log(text + ' error : ', err);
                return of();
            })
        );
    }
}