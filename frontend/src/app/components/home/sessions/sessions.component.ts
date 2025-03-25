import { Component } from '@angular/core';
import { SessionService } from '../../../service/session.service';
import { SessionDTO } from '../../../model/session.dto';
import { LoggerService } from '../../../service/logger.service';

@Component({
  selector: 'app-sessions',
  imports: [],
  templateUrl: './sessions.component.html',
  styleUrl: './sessions.component.css'
})
export class SessionsComponent {
  protected sessions:SessionDTO[] = [] as SessionDTO[]

  constructor(
    private sessionService: SessionService,
    private loggerService: LoggerService
  ) { }

  ngOnInit() {
    this.sessionService.getAllSessions().subscribe({
      next: sessionsGot => {
      this.sessions = sessionsGot;
      },
      complete: () => {
        this.loggerService.info("Sessions successfully obtained")
      }
    });
  }
}
