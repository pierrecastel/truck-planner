import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { AccountService } from 'app/core';
import { TruckCompanyService } from './truck-company.service';

@Component({
    selector: 'jhi-truck-company',
    templateUrl: './truck-company.component.html'
})
export class TruckCompanyComponent implements OnInit, OnDestroy {
    truckCompanies: ITruckCompany[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected truckCompanyService: TruckCompanyService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.truckCompanyService
            .query()
            .pipe(
                filter((res: HttpResponse<ITruckCompany[]>) => res.ok),
                map((res: HttpResponse<ITruckCompany[]>) => res.body)
            )
            .subscribe(
                (res: ITruckCompany[]) => {
                    this.truckCompanies = res;
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTruckCompanies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITruckCompany) {
        return item.id;
    }

    registerChangeInTruckCompanies() {
        this.eventSubscriber = this.eventManager.subscribe('truckCompanyListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
