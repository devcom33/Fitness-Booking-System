import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { AdminClassScheduleResponseDto, AdminInstructorControllerService, AdminScheduleControllerService, InstructorResponseDto, Pageable } from '../../../api';
import { Sidebar } from "../sidebar/sidebar";
import { FormsModule } from '@angular/forms';

type ScheduleStatus = 'SCHEDULED' | 'CANCELLED' | undefined;

@Component({
  selector: 'app-classes',
  imports: [Sidebar, FormsModule],
  templateUrl: './classes.html',
  styleUrl: './classes.css',
})
export class Classes implements OnInit{
  private classScheduleService = inject(AdminScheduleControllerService);

  ClassScheduleClasses = signal<AdminClassScheduleResponseDto[]>([]);
  errorMsg = signal<string>('');

  selectedInstructorId : string | undefined = undefined;
  selectedStatus : ScheduleStatus = undefined;

  trainersList = signal<InstructorResponseDto[]>([]);
  instructorService = inject(AdminInstructorControllerService);

  searchTerm = signal<string>('');

  currentPage = signal(0);
  pageSize = signal(5);
  totalPages = signal(0);
  totalElements = signal(0);

  filteredClasses = computed(
    () => {
      const term = this.searchTerm().toLowerCase().trim();
      if (!term) return this.ClassScheduleClasses();

      return this.ClassScheduleClasses().filter(cls => 
        // Search by Class Name, Instructor Name, or even Status
        cls.fitnessClassDto?.name?.toLowerCase().includes(term) || 
        cls.instructorName?.toLowerCase().includes(term) ||
        cls.status?.toLowerCase().includes(term)
      );
    }
  );


  ngOnInit(): void {
    this.getAllClasses();
    this.loadInstructors();
  }

  getAllClasses() : void
  {
    const pageParams: Pageable = {
        page: this.currentPage(),
        size: this.pageSize(),
        sort: ['id'],
      };
    const params = {
      instructorId: this.selectedInstructorId || undefined,
      status: this.selectedStatus || undefined
    };


    this.classScheduleService.getAllSchedules(pageParams, params.instructorId, params.status).subscribe({
      next: (data : any) => {
         console.log(data); 
          this.ClassScheduleClasses.set(data.content ?? []);
          this.totalPages.set(data.page?.totalPages ?? 0);
          this.totalElements.set(data.page?.totalElements ?? 0);
      },
      error: (err) => {
        this.errorMsg.set('Failed to load Classes. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
  });
  }

  loadInstructors() {
    this.instructorService.getInstructors().subscribe({
      next: (data) => this.trainersList.set(data),
      error: (err) => {
        this.errorMsg.set('Failed to load instructors. Please check your connection.');
        console.error('[!] Fetch error:', err);
      },
    });
  }

  onFilterChange() {
    this.getAllClasses();
  }

  clearFilters() {
    this.selectedInstructorId = undefined;
    this.selectedStatus = undefined;
    this.getAllClasses();
  }

  getCapacityPercent(cls: any): string {
  return cls.capacity ? `${(cls.bookedCount / cls.capacity) * 100}%` : '0%';
  }

  // search
  updateSearch(value: string){
    this.searchTerm.set(value);
  }

  changePage(delta: number) {
    this.currentPage.update((p) => {
      const newPage = p + delta;
      if (newPage < 0 || newPage >= this.totalPages()) {
        return p;
      }
      return newPage;
    });

    this.getAllClasses();
  }
}
