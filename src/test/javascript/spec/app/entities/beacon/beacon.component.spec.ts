import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MuseuDashboardTestModule } from '../../../test.module';
import { BeaconComponent } from 'app/entities/beacon/beacon.component';
import { BeaconService } from 'app/entities/beacon/beacon.service';
import { Beacon } from 'app/shared/model/beacon.model';

describe('Component Tests', () => {
  describe('Beacon Management Component', () => {
    let comp: BeaconComponent;
    let fixture: ComponentFixture<BeaconComponent>;
    let service: BeaconService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MuseuDashboardTestModule],
        declarations: [BeaconComponent]
      })
        .overrideTemplate(BeaconComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BeaconComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BeaconService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Beacon(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.beacons && comp.beacons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
