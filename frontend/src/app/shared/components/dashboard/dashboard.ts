import { AfterViewInit, Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth-service';
import { Sidebar } from "../../../features/admin/sidebar/sidebar";
import { Navbar } from '../navbar/navbar';
import { Chart, registerables } from 'chart.js';
import { AdminDashboardControllerService, BookingMonthlyCountDTO, CategoriesStatsDTO, DashboardStatsDTO } from '../../../api';

// Register Chart.js components
Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  imports: [RouterLink, Navbar, Sidebar],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard implements OnInit, AfterViewInit, OnDestroy {
  authService = inject(AuthService);
  role = this.authService.getUserRoles();

  private adminChartsInitialized = false;
  private charts: Chart[] = [];

  //adminDashboardController
  private adminDashboardService = inject(AdminDashboardControllerService);
  stats = signal<DashboardStatsDTO | null>(null);
  lastBookingMonthsStats = signal<BookingMonthlyCountDTO[]>([]);
  categoriesStats = signal<CategoriesStatsDTO[]>([]);

  ngOnInit(): void {
    this.getDashboardStatsCard();
    this.getLastMonthsBookingStats();
    this.getCategoriesStats();
  }


  getDashboardStatsCard() {
    this.adminDashboardService.getDashboardStats().subscribe({
      next: (data) => {
        this.stats.set(data)
      },
      error: (err) => {
        console.error('Failed to load stats', err);
      }
    });
  }


  getLastMonthsBookingStats() {
    this.adminDashboardService.getLastMonthsStats().subscribe({
      next: (data) => {
        this.lastBookingMonthsStats.set(this.fillMissingMonths(data, 7));
        // init charts only after data is ready
        if (this.role?.includes('ROLE_ADMIN') && !this.adminChartsInitialized) {
          setTimeout(() => {
            this.initAdminCharts();
            this.adminChartsInitialized = true;
          }, 100);
        }
      },
      error: (err) => {
        console.error('Failed to load bookings stats', err);
      }
    });
  }


  getCategoriesStats() {
    this.adminDashboardService.getCategoriesStats().subscribe(
      {
        next: (data) => {
        console.log("DAAAAAAAAAAAAAAAATA", data);
        this.categoriesStats.set(data)
      },
      error: (err) => {
        console.error('Failed to load stats', err);
      }
      }
    );
  }

  ngAfterViewInit(): void {

  }

  private initAdminCharts(): void {
    this.destroyCharts();

    const monthNames = ['January','February','March','April','May','June',
                    'July','August','September','October','November','December'];
    const bookingStats = this.lastBookingMonthsStats();

    const labels = bookingStats.map(s => monthNames[(s.month ?? 1) - 1]);
    const data   = bookingStats.map(s => s.count ?? 0);


    // Monthly Bookings Trend Chart
    const bookingsTrendCtx = document.getElementById('bookingsTrendChart') as HTMLCanvasElement;
    if (bookingsTrendCtx) {
      const bookingsTrend = new Chart(bookingsTrendCtx, {
        type: 'line',
        data: {
          labels: labels,
          datasets: [
            {
              label: 'Bookings',
              data: data,
              borderColor: '#F97316',
              backgroundColor: 'rgba(249, 115, 22, 0.08)',
              fill: true,
              tension: 0.4,
              pointBackgroundColor: '#F97316',
              pointBorderColor: '#fff',
              pointBorderWidth: 2,
              pointRadius: 5,
              pointHoverRadius: 7,
              borderWidth: 3
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          responsive: true,
          plugins: {
            legend: {
              display: true,
              labels: {
                usePointStyle: true,
                boxWidth: 6,
                padding: 15,
                font: { size: 12, weight: 500 }
              }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 0, 0, 0.8)',
              padding: 12,
              titleFont: { size: 13, weight: 'bold' },
              bodyFont: { size: 12 },
              borderColor: '#F97316',
              borderWidth: 1
            }
          },
          scales: {
            y: {
              beginAtZero: true,
              grid: { color: '#E5E7EB', drawOnChartArea: true, drawTicks: false },
              ticks: { font: { size: 12 }, color: '#6B7280' }
            },
            x: {
              grid: { display: false, drawOnChartArea: false, drawTicks: false },
              ticks: { font: { size: 12 }, color: '#6B7280' }
            }
          }
        }
      });
      this.charts.push(bookingsTrend);
    }

    // User Distribution Pie Chart
    const userDistributionCtx = document.getElementById('userDistributionChart') as HTMLCanvasElement;
    if (userDistributionCtx) {
      const userDistribution = new Chart(userDistributionCtx, {
        type: 'doughnut',
        data: {
          labels: ['Clients', 'Trainers'],
          datasets: [
            {
              data: [this.stats()?.totalClients ?? 0, this.stats()?.totalInstructors ?? 0],
              backgroundColor: ['#10B981', '#3B82F6'],
              borderColor: '#fff',
              borderWidth: 2
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          responsive: true,
          plugins: {
            legend: {
              position: 'bottom',
              labels: {
                usePointStyle: true,
                boxWidth: 8,
                padding: 15,
                font: { size: 12, weight: 500 },
                color: '#374151'
              }
            },
            tooltip: {
              backgroundColor: 'rgba(0, 0, 0, 0.8)',
              padding: 12,
              titleFont: { size: 13, weight: 'bold' },
              bodyFont: { size: 12 }
            }
          }
        }
      });
      this.charts.push(userDistribution);
    }

    // Class Category Performance Bar Chart
    const classCategoryCtx = document.getElementById('classCategoryChart') as HTMLCanvasElement;
    const categoriesData = this.categoriesStats();
    const category_labels = categoriesData.map(s => s.category);
    const category_counts = categoriesData.map(s => s.count ?? 0);
    const colors = ['#3B82F6','#F97316','#8B5CF6','#10B981','#EF4444','#EC4899'];

    if (classCategoryCtx) {
      const classCategory = new Chart(classCategoryCtx, {
        type: 'bar',
        data: {
          labels: category_labels,
          datasets: [
            {
              label: 'Bookings',
              data: category_counts,
              backgroundColor: colors.slice(0, categoriesData.length),
              borderRadius: 8,
              borderSkipped: false
            }
          ]
        },
        options: {
          maintainAspectRatio: false,
          responsive: true,
          indexAxis: 'x',
          plugins: {
            legend: { display: false }
          },
          scales: {
            y: {
              beginAtZero: true,
              grid: { color: '#E5E7EB', drawOnChartArea: true, drawTicks: false },
              ticks: { font: { size: 12 }, color: '#6B7280' }
            },
            x: {
              grid: { display: false, drawOnChartArea: false, drawTicks: false },
              ticks: { font: { size: 12 }, color: '#6B7280' }
            }
          }
        }
      });
      this.charts.push(classCategory);
    }
  }

  private destroyCharts(): void {
    this.charts.forEach(chart => chart.destroy());
    this.charts = [];
  }

  ngOnDestroy(): void {
    this.destroyCharts();
  }


  //a helper for filling missing months
  fillMissingMonths(data: BookingMonthlyCountDTO[], months: number): BookingMonthlyCountDTO[] {
      const result: BookingMonthlyCountDTO[] = [];
      const now = new Date();

      for (let i = months - 1; i >= 0; i--) {
        const date = new Date(now.getFullYear(), now.getMonth() - i, 1);
        const month = date.getMonth() + 1;
        const year = date.getFullYear();

        const found = data.find(d => d.month === month && d.year === year);
        result.push({ month, year, count: found?.count ?? 0 });
      }

      return result;
  }
}