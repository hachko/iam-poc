import { BehaviorSubject, Observable, of, tap } from "rxjs";
import { User } from "../model/user.model";
import { UserService } from "../service/user.service";

export class UserAggregate {
    private user$ = new BehaviorSubject<User[]>([]);

    constructor(private userService: UserService) {}

    loadUsers(): void {
        this.userService.getUsers().subscribe(users => {
            this.user$.next(users);
        });
    }

    get users() : Observable<User[]> {
        return this.user$.asObservable();
    }

    addUser(user: User): void {
        this.userService.createUser(user).subscribe(() => {
            this.loadUsers();
        });
    }

    updateUser(user: User): void {
        this.userService.updateUser(user.id, user).subscribe(() => {
            this.loadUsers();
        });
    }

    deleteUser(id: number): void {
        this.userService.deleteUser(id).subscribe(() => {
            this.loadUsers();
        });
    }

    getUserById(id: number): Observable<User> {
        const existingUser = this.user$.getValue().find(u => u.id === id);
        if (existingUser) {
            return of(existingUser);
        }
        return this.userService.getUserById(id).pipe(
            tap(user => {
                const currentUsers = [...this.user$.getValue().filter(u => u.id !== id), user];
                this.user$.next(currentUsers);
            })
        );
    }
}