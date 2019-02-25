import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDelivery } from 'app/shared/model/delivery.model';

@Component({
    selector: 'jhi-delivery-detail',
    templateUrl: './delivery-detail.component.html'
})
export class DeliveryDetailComponent implements OnInit {
    delivery: IDelivery;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ delivery }) => {
            this.delivery = delivery;
        });
    }

    previousState() {
        window.history.back();
    }
}
