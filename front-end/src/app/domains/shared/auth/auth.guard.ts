import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from "@angular/router";
import { AuthService } from "./auth.service"
import { Injectable } from "@angular/core";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
    constructor(private authService: AuthService, private router: Router) {}
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): MaybeAsync<GuardResult> {        
        if (this.authService.isLoggedIn()) {
            const requiredRoles = route.data['roles'] as string[] | undefined;
            if (requiredRoles && requiredRoles.length > 0) {
                const hasRole = requiredRoles.some(role => this.authService.hasRole(role));
                if (!hasRole) {
                    return false;
                }
            }
            return true;
        }
        this.router.navigate(['/login']);
        return false;
    }
}