export interface Message {
    /** identifier used internally to track and remove the message */
    id?: number;
    type: 'success' | 'error' | 'warning' | 'info';
    text: string;
}
