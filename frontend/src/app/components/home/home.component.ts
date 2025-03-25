import { Component } from '@angular/core';
import { HeaderComponent } from "../partials/header/header.component";
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [
    RouterModule,
    HeaderComponent
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
}
