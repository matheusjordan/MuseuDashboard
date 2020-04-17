import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBeacon } from 'app/shared/model/beacon.model';
import { BeaconService } from './beacon.service';
import { BeaconDeleteDialogComponent } from './beacon-delete-dialog.component';

@Component({
  selector: 'jhi-beacon',
  templateUrl: './beacon.component.html'
})
export class BeaconComponent implements OnInit, OnDestroy {
  beacons?: IBeacon[];
  eventSubscriber?: Subscription;

  constructor(protected beaconService: BeaconService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.beaconService.query().subscribe((res: HttpResponse<IBeacon[]>) => (this.beacons = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBeacons();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBeacon): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBeacons(): void {
    this.eventSubscriber = this.eventManager.subscribe('beaconListModification', () => this.loadAll());
  }

  delete(beacon: IBeacon): void {
    const modalRef = this.modalService.open(BeaconDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.beacon = beacon;
  }
}
