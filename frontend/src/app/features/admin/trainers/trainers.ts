import { Component, inject, OnInit, signal } from '@angular/core';
import { Sidebar } from '../sidebar/sidebar';
import { CommonModule } from '@angular/common';
import { ToastService } from '../../../shared/services/toast-service';
import { activeTrainers } from "./active-trainers/active-trainers";
import { pendingTrainers } from "./pending-trainers/pending-trainers";
import { AdminClientControllerService, InstructorResponseDto } from '../../../api';
import { deactiveTrainers } from "./deactivated-trainers/deactive-trainers";

@Component({
  selector: 'app-trainers',
  imports: [Sidebar, CommonModule, activeTrainers, pendingTrainers, deactiveTrainers],
  templateUrl: './trainers.html',
  styleUrl: './trainers.css',
})
export class Trainers implements OnInit{
  errorMsg = signal<string>('');
  activeTab = signal<'active' | 'pending' | 'deactivated'>('active');

  showActive() { this.activeTab.set('active'); }
  showPending() { this.activeTab.set('pending'); }
  showDeactivated() {this.activeTab.set('deactivated');}
  

  ngOnInit(): void {
  }


}