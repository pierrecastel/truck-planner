import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDelivery } from 'app/shared/model/delivery.model';
import { AccountService } from 'app/core';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery',
    templateUrl: './delivery.component.html'
})
export class DeliveryComponent implements OnInit, OnDestroy {
    deliveries: IDelivery[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected deliveryService: DeliveryService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.deliveryService
            .query()
            .pipe(
                filter((res: HttpResponse<IDelivery[]>) => res.ok),
                map((res: HttpResponse<IDelivery[]>) => res.body)
            )
            .subscribe(
                (res: IDelivery[]) => {
                    this.deliveries = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDeliveries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDelivery) {
        return item.id;
    }

    registerChangeInDeliveries() {
        this.eventSubscriber = this.eventManager.subscribe('deliveryListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
