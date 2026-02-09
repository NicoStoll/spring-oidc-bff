import {Component, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

interface Message {
  message: string
}

@Component({
  selector: 'app-content-display',
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './content-display.html',
  styleUrl: './content-display.scss',
  standalone: true
})
export class ContentDisplay {

  messageInput: string = '';
  responseMessages = signal<Message[]>([]);
  statusMessage: string = '';

  constructor(private http: HttpClient) {}

  // GET /api/messages
  getMessages() {
    this.http.get<Message[]>('/api/messages', { withCredentials: true}).subscribe({
      next: (data) => {
        this.responseMessages.set(data);
        this.statusMessage = 'Nachrichten erfolgreich geladen.';
      },
      error: (err) => this.statusMessage = 'Fehler beim Laden: ' + err.status
    });
  }

  // POST /api/messages
  postMessage() {
    if (!this.messageInput) return;

    this.http.post('/api/messages', { message: this.messageInput }, { withCredentials: true}).subscribe({
      next: () => {
        this.statusMessage = 'Nachricht gesendet!';
        this.messageInput = ''; // Input leeren
        this.getMessages();    // Liste aktualisieren
      },
      error: (err) => this.statusMessage = 'Fehler beim Senden: ' + err.status
    });
  }
}
