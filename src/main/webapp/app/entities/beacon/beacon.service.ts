import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBeacon } from 'app/shared/model/beacon.model';

type EntityResponseType = HttpResponse<IBeacon>;
type EntityArrayResponseType = HttpResponse<IBeacon[]>;

@Injectable({ providedIn: 'root' })
export class BeaconService {
  public resourceUrl = SERVER_API_URL + 'api/beacons';

  constructor(protected http: HttpClient) {}

  create(beacon: IBeacon): Observable<EntityResponseType> {
    return this.http.post<IBeacon>(this.resourceUrl, beacon, { observe: 'response' });
  }

  update(beacon: IBeacon): Observable<EntityResponseType> {
    return this.http.put<IBeacon>(this.resourceUrl, beacon, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBeacon>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBeacon[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
