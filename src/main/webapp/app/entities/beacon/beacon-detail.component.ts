import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBeacon } from 'app/shared/model/beacon.model';

@Component({
  selector: 'jhi-beacon-detail',
  templateUrl: './beacon-detail.component.html'
})
export class BeaconDetailComponent implements OnInit {
  beacon: IBeacon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beacon }) => (this.beacon = beacon));
  }

  previousState(): void {
    window.history.back();
  }
}
