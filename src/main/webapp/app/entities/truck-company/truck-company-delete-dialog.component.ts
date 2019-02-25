import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITruckCompany } from 'app/shared/model/truck-company.model';
import { TruckCompanyService } from './truck-company.service';

@Component({
    selector: 'jhi-truck-company-delete-dialog',
    templateUrl: './truck-company-delete-dialog.component.html'
})
export class TruckCompanyDeleteDialogComponent {
    truckCompany: ITruckCompany;

    constructor(
        protected truckCompanyService: TruckCompanyService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.truckCompanyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'truckCompanyListModification',
                content: 'Deleted an truckCompany'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-truck-company-delete-popup',
    template: ''
})
export class TruckCompanyDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ truckCompany }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TruckCompanyDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.truckCompany = truckCompany;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/truck-company', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/truck-company', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
