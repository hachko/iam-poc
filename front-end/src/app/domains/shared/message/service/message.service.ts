import { BehaviorSubject, Observable } from "rxjs";
import { Message } from "../model/message.model";
import { Injectable } from "@angular/core";

@Injectable({providedIn: 'root'})
export class MessageService {
    private messages$$ = new BehaviorSubject<Message[]>([]);
    
    getMessages(): Observable<Message[]> {
        return this.messages$$.asObservable()
    }
    
    show(msg: Message): void {
        const next = [...this.messages$$.value, msg]
        this.messages$$.next(next);
    }

    clear(target?: Message): void {
        if(!target) {
            this.messages$$.next([]);
            return;            
        }
        this.messages$$.next(this.messages$$.value.filter(m => m !== target));
    }

    clearAll(): void {
        this.messages$$.next([]);
    }

}