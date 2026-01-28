import { Injectable, signal } from '@angular/core';
import { EventInput } from '@fullcalendar/core';

@Injectable({ providedIn: 'root' })
export class CalendarEventsStore {
  events = signal<EventInput[]>([]);
}
