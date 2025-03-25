import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  // Debug settings
  public debugLogsEnabled = false;

  constructor() { }

  // General log
  public log(message: string, optionalParams?: any[]): void
  {
    console.log('[LOG]: ', message, ...(optionalParams || []));
  }

  // Info log
  public info(message: string, optionalParams?: any[]): void
  {
    console.info('[INFO]: ', message, ...optionalParams || []);
  }

  // Warning log
  public warn(message: string, optionalParams?: any[]): void
  {
    console.warn('[WARN]: ', message, ...optionalParams || []);
  }

  // Error log
  public error(message: string, optionalParams?: any[]): void
  {
    console.error('[ERROR]: ', message, ...optionalParams || []);
  }

  // Debug log
  public debug(message: string, optionalParams?: any[]): void
  {
    if(!this.debugLogsEnabled) {
      return;
    }
    console.debug('[DEBUG]: ', ...optionalParams || []);
  }
}