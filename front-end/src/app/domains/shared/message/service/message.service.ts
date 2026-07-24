import { BehaviorSubject, Observable } from "rxjs";
import { Message } from "../model/message.model";
import { Injectable } from "@angular/core";

@Injectable({providedIn: 'root'})
export class MessageService {
    private messages$$ = new BehaviorSubject<Message[]>([]);
    private nextId = 0;
    private timeouts = new Map<number, ReturnType<typeof setTimeout>>();

    getMessages(): Observable<Message[]> {
        return this.messages$$.asObservable()
    }
    
    /**
     * Add a message and schedule its removal after 5 seconds.
     */
    show(msg: Message): void {
        const message: Message = { ...msg, id: this.nextId++ };
        const next = [...this.messages$$.value, message];
        this.messages$$.next(next);

        const handle = setTimeout(() => {
            // If the message still exists, clear it automatically
            this.clear(message);
        }, 5000);
        this.timeouts.set(message.id!, handle);
    }

    clear(target?: Message): void {
        if (!target) {
            // clear all messages and cancel any pending timers
            this.timeouts.forEach(h => clearTimeout(h));
            this.timeouts.clear();
            this.messages$$.next([]);
            return;
        }

        // cancel the timeout for this message if it exists
        if (target.id !== undefined) {
            const h = this.timeouts.get(target.id);
            if (h) {
                clearTimeout(h);
                this.timeouts.delete(target.id);
            }
        }

        this.messages$$.next(
            this.messages$$.value.filter(m => m !== target)
        );
    }

    clearAll(): void {
        this.clear();
    }

}