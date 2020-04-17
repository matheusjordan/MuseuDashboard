import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBeacon, Beacon } from 'app/shared/model/beacon.model';
import { BeaconService } from './beacon.service';
import { BeaconComponent } from './beacon.component';
import { BeaconDetailComponent } from './beacon-detail.component';
import { BeaconUpdateComponent } from './beacon-update.component';

@Injectable({ providedIn: 'root' })
export class BeaconResolve implements Resolve<IBeacon> {
  constructor(private service: BeaconService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBeacon> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((beacon: HttpResponse<Beacon>) => {
          if (beacon.body) {
            return of(beacon.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Beacon());
  }
}

export const beaconRoute: Routes = [
  {
    path: '',
    component: BeaconComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'museuDashboardApp.beacon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BeaconDetailComponent,
    resolve: {
      beacon: BeaconResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'museuDashboardApp.beacon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BeaconUpdateComponent,
    resolve: {
      beacon: BeaconResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'museuDashboardApp.beacon.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BeaconUpdateComponent,
    resolve: {
      beacon: BeaconResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'museuDashboardApp.beacon.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
