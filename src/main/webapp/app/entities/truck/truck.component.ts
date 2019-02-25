import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITruck } from 'app/shared/model/truck.model';
import { AccountService } from 'app/core';
import { TruckService } from './truck.service';

@Component({
    selector: 'jhi-truck',
    templateUrl: './truck.component.html'
})
export class TruckComponent implements OnInit, OnDestroy {
    trucks: ITruck[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected truckService: TruckService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.truckService
            .query()
            .pipe(
                filter((res: HttpResponse<ITruck[]>) => res.ok),
                map((res: HttpResponse<ITruck[]>) => res.body)
            )
            .subscribe(
                (res: ITruck[]) => {
                    this.trucks = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTrucks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITruck) {
        return item.id;
    }

    registerChangeInTrucks() {
        this.eventSubscriber = this.eventManager.subscribe('truckListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
