import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOrder } from 'app/shared/model/order.model';
import { OrderService } from './order.service';
import { ITruck } from 'app/shared/model/truck.model';
import { TruckService } from 'app/entities/truck';
import { IDelivery } from 'app/shared/model/delivery.model';
import { DeliveryService } from 'app/entities/delivery';
import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { TruckCompanyService } from 'app/entities/truck-company';

@Component({
    selector: 'jhi-order-update',
    templateUrl: './order-update.component.html'
})
export class OrderUpdateComponent implements OnInit {
    order: IOrder;
    isSaving: boolean;

    trucks: ITruck[];

    deliveries: IDelivery[];

    truckcompanies: ITruckCompany[];
    requestTimestamp: string;
    departureTimeLocal: string;
    arrivalTimeLocal: string;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected orderService: OrderService,
        protected truckService: TruckService,
        protected deliveryService: DeliveryService,
        protected truckCompanyService: TruckCompanyService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ order }) => {
            this.order = order;
            this.requestTimestamp = this.order.requestTimestamp != null ? this.order.requestTimestamp.format(DATE_TIME_FORMAT) : null;
            this.departureTimeLocal = this.order.departureTimeLocal != null ? this.order.departureTimeLocal.format(DATE_TIME_FORMAT) : null;
            this.arrivalTimeLocal = this.order.arrivalTimeLocal != null ? this.order.arrivalTimeLocal.format(DATE_TIME_FORMAT) : null;
        });
        this.truckService
            .query({ filter: 'order-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ITruck[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITruck[]>) => response.body)
            )
            .subscribe(
                (res: ITruck[]) => {
                    if (!this.order.truck || !this.order.truck.id) {
                        this.trucks = res;
                    } else {
                        this.truckService
                            .find(this.order.truck.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ITruck>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ITruck>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ITruck) => (this.trucks = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.deliveryService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDelivery[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDelivery[]>) => response.body)
            )
            .subscribe((res: IDelivery[]) => (this.deliveries = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.truckCompanyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITruckCompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITruckCompany[]>) => response.body)
            )
            .subscribe((res: ITruckCompany[]) => (this.truckcompanies = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.order.requestTimestamp = this.requestTimestamp != null ? moment(this.requestTimestamp, DATE_TIME_FORMAT) : null;
        this.order.departureTimeLocal = this.departureTimeLocal != null ? moment(this.departureTimeLocal, DATE_TIME_FORMAT) : null;
        this.order.arrivalTimeLocal = this.arrivalTimeLocal != null ? moment(this.arrivalTimeLocal, DATE_TIME_FORMAT) : null;
        if (this.order.id !== undefined) {
            this.subscribeToSaveResponse(this.orderService.update(this.order));
        } else {
            this.subscribeToSaveResponse(this.orderService.create(this.order));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IOrder>>) {
        result.subscribe((res: HttpResponse<IOrder>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTruckById(index: number, item: ITruck) {
        return item.id;
    }

    trackDeliveryById(index: number, item: IDelivery) {
        return item.id;
    }

    trackTruckCompanyById(index: number, item: ITruckCompany) {
        return item.id;
    }
}
