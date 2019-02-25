import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDriver } from 'app/shared/model/driver.model';
import { AccountService } from 'app/core';
import { DriverService } from './driver.service';

@Component({
    selector: 'jhi-driver',
    templateUrl: './driver.component.html'
})
export class DriverComponent implements OnInit, OnDestroy {
    drivers: IDriver[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected driverService: DriverService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.driverService
            .query()
            .pipe(
                filter((res: HttpResponse<IDriver[]>) => res.ok),
                map((res: HttpResponse<IDriver[]>) => res.body)
            )
            .subscribe(
                (res: IDriver[]) => {
                    this.drivers = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDrivers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDriver) {
        return item.id;
    }

    registerChangeInDrivers() {
        this.eventSubscriber = this.eventManager.subscribe('driverListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
