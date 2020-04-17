import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'beacon',
        loadChildren: () => import('./beacon/beacon.module').then(m => m.MuseuDashboardBeaconModule)
      },
      {
        path: 'guest',
        loadChildren: () => import('./guest/guest.module').then(m => m.MuseuDashboardGuestModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MuseuDashboardEntityModule {}
