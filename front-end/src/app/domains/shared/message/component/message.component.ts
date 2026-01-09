import { Component, OnInit } from "@angular/core";
import { CommonModule, NgClass } from "@angular/common";
import { Observable } from "rxjs";
import { Message } from "../model/message.model";
import { MessageService } from "../service/message.service";

@Component({
    selector: 'app-message',
    templateUrl: './message.component.html',
    styleUrl: './message.component.css',
    standalone: true,
    imports: [NgClass, CommonModule ]
})
export class MessageComponent implements OnInit {
    messages$!: Observable<Message[]>;
    constructor(private messageService: MessageService) {}

    ngOnInit(): void {
        this.messages$ = this.messageService.getMessages();
    }

    dismiss(message: Message) {
        console.log('dimissing message : ', message.text);
        this.messageService.clear(message);        
    }

    clearAll() {
        this.messageService.clearAll();
    }

}