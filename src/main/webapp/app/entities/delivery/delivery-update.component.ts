import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDelivery } from 'app/shared/model/delivery.model';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-update',
    templateUrl: './delivery-update.component.html'
})
export class DeliveryUpdateComponent implements OnInit {
    delivery: IDelivery;
    isSaving: boolean;

    constructor(protected deliveryService: DeliveryService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ delivery }) => {
            this.delivery = delivery;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.delivery.id !== undefined) {
            this.subscribeToSaveResponse(this.deliveryService.update(this.delivery));
        } else {
            this.subscribeToSaveResponse(this.deliveryService.create(this.delivery));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDelivery>>) {
        result.subscribe((res: HttpResponse<IDelivery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
