import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDelivery } from 'app/shared/model/delivery.model';

type EntityResponseType = HttpResponse<IDelivery>;
type EntityArrayResponseType = HttpResponse<IDelivery[]>;

@Injectable({ providedIn: 'root' })
export class DeliveryService {
    public resourceUrl = SERVER_API_URL + 'api/deliveries';

    constructor(protected http: HttpClient) {}

    create(delivery: IDelivery): Observable<EntityResponseType> {
        return this.http.post<IDelivery>(this.resourceUrl, delivery, { observe: 'response' });
    }

    update(delivery: IDelivery): Observable<EntityResponseType> {
        return this.http.put<IDelivery>(this.resourceUrl, delivery, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDelivery>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDelivery[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
