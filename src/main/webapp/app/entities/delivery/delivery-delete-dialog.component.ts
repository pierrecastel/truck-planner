import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDelivery } from 'app/shared/model/delivery.model';
import { DeliveryService } from './delivery.service';

@Component({
    selector: 'jhi-delivery-delete-dialog',
    templateUrl: './delivery-delete-dialog.component.html'
})
export class DeliveryDeleteDialogComponent {
    delivery: IDelivery;

    constructor(protected deliveryService: DeliveryService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deliveryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'deliveryListModification',
                content: 'Deleted an delivery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-delivery-delete-popup',
    template: ''
})
export class DeliveryDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ delivery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DeliveryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.delivery = delivery;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/delivery', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/delivery', { outlets: { popup: null } }]);
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
