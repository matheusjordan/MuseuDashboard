import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBeacon } from 'app/shared/model/beacon.model';
import { BeaconService } from './beacon.service';

@Component({
  templateUrl: './beacon-delete-dialog.component.html'
})
export class BeaconDeleteDialogComponent {
  beacon?: IBeacon;

  constructor(protected beaconService: BeaconService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.beaconService.delete(id).subscribe(() => {
      this.eventManager.broadcast('beaconListModification');
      this.activeModal.close();
    });
  }
}
