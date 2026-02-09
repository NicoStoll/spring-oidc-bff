import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ContentDisplay} from './content-display/content-display';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ContentDisplay],
  templateUrl: './app.html',
  standalone: true,
  styleUrl: './app.scss'
})
export class App {
  protected title = 'frontend-bff';
}
