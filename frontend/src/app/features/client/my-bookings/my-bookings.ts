import { Component, inject, OnInit, signal } from '@angular/core';
import { BookingControllerService, BookingResponseDto, Pageable } from '../../../api';
import { DatePipe } from '@angular/common';
import { ToastService } from '../../../services/toast-service';
import { Navbar } from '../../../components/navbar/navbar';

@Component({
  selector: 'app-my-bookings',
  imports: [DatePipe, Navbar],
  templateUrl: './my-bookings.html',
  styleUrl: './my-bookings.css',
})
export class MyBookings implements OnInit {
  private myBookingService = inject(BookingControllerService);
  errorMsg = signal<string>('');
  myBookingsList = signal<BookingResponseDto[]>([]);
  private readonly toast = inject(ToastService);
  currentPage = signal(0);
  pageSize = signal(5);

  ngOnInit(): void {
    this.loadMyBookings();
  }

  loadMyBookings() {
    const pageParams: Pageable = {
      page: this.currentPage() - 1,
      size: this.pageSize(),
      sort: ['id'],
    };

    this.myBookingService.getMyBookings(pageParams).subscribe({
      next: (data) => this.myBookingsList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load bookings. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }
  updateBookingStatus(bookingId: string | undefined): void {
    if (!bookingId) {
      console.error('Booking ID is missing');
      return;
    }
    this.myBookingService.updateBookingStatus(bookingId, { status: 'CANCELLED' } as any).subscribe({
      next: () => {
        this.toast.show('Booking Cancelled Succcessfully!', 'success');
        this.loadMyBookings();
      },
    });
    {
    }
  }
  changePage(delta: number) {
    this.currentPage.update((p) => Math.max(0, p + delta));
    this.loadMyBookings();
  }
}
