import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITruck } from 'app/shared/model/truck.model';
import { TruckService } from './truck.service';
import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { TruckCompanyService } from 'app/entities/truck-company';
import { IDriver } from 'app/shared/model/driver.model';
import { DriverService } from 'app/entities/driver';

@Component({
    selector: 'jhi-truck-update',
    templateUrl: './truck-update.component.html'
})
export class TruckUpdateComponent implements OnInit {
    truck: ITruck;
    isSaving: boolean;

    truckcompanies: ITruckCompany[];

    drivers: IDriver[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected truckService: TruckService,
        protected truckCompanyService: TruckCompanyService,
        protected driverService: DriverService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ truck }) => {
            this.truck = truck;
        });
        this.truckCompanyService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITruckCompany[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITruckCompany[]>) => response.body)
            )
            .subscribe((res: ITruckCompany[]) => (this.truckcompanies = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.driverService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDriver[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDriver[]>) => response.body)
            )
            .subscribe((res: IDriver[]) => (this.drivers = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.truck.id !== undefined) {
            this.subscribeToSaveResponse(this.truckService.update(this.truck));
        } else {
            this.subscribeToSaveResponse(this.truckService.create(this.truck));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITruck>>) {
        result.subscribe((res: HttpResponse<ITruck>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTruckCompanyById(index: number, item: ITruckCompany) {
        return item.id;
    }

    trackDriverById(index: number, item: IDriver) {
        return item.id;
    }
}
