import { Component, inject, OnInit, signal } from '@angular/core';
import { Sidebar } from "../sidebar/sidebar";;
import { activeClients } from "./active-clients/active-clients";
import { deactiveClients } from "./deactivated-clients/deactive-clients";

@Component({
  selector: 'app-clients',
  imports: [Sidebar, activeClients, deactiveClients],
  templateUrl: './clients.html',
  styleUrl: './clients.css',
})
export class Clients implements OnInit{
  activeTab = signal<'active' | 'deactivated'>('active');

  showActive() { this.activeTab.set('active'); }
  showDeactivated() {this.activeTab.set('deactivated');}
  

  ngOnInit(): void {
  }

}
