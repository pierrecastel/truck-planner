import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITruckCompany } from 'app/shared/model/truck-company.model';

type EntityResponseType = HttpResponse<ITruckCompany>;
type EntityArrayResponseType = HttpResponse<ITruckCompany[]>;

@Injectable({ providedIn: 'root' })
export class TruckCompanyService {
    public resourceUrl = SERVER_API_URL + 'api/truck-companies';

    constructor(protected http: HttpClient) {}

    create(truckCompany: ITruckCompany): Observable<EntityResponseType> {
        return this.http.post<ITruckCompany>(this.resourceUrl, truckCompany, { observe: 'response' });
    }

    update(truckCompany: ITruckCompany): Observable<EntityResponseType> {
        return this.http.put<ITruckCompany>(this.resourceUrl, truckCompany, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITruckCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITruckCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
