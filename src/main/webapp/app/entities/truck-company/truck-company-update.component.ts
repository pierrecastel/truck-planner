import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { TruckCompanyService } from './truck-company.service';

@Component({
    selector: 'jhi-truck-company-update',
    templateUrl: './truck-company-update.component.html'
})
export class TruckCompanyUpdateComponent implements OnInit {
    truckCompany: ITruckCompany;
    isSaving: boolean;

    constructor(protected truckCompanyService: TruckCompanyService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ truckCompany }) => {
            this.truckCompany = truckCompany;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.truckCompany.id !== undefined) {
            this.subscribeToSaveResponse(this.truckCompanyService.update(this.truckCompany));
        } else {
            this.subscribeToSaveResponse(this.truckCompanyService.create(this.truckCompany));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITruckCompany>>) {
        result.subscribe((res: HttpResponse<ITruckCompany>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
