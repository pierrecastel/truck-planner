/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TruckplannerTestModule } from '../../../test.module';
import { DeliveryDetailComponent } from 'app/entities/delivery/delivery-detail.component';
import { Delivery } from 'app/shared/model/delivery.model';

describe('Component Tests', () => {
    describe('Delivery Management Detail Component', () => {
        let comp: DeliveryDetailComponent;
        let fixture: ComponentFixture<DeliveryDetailComponent>;
        const route = ({ data: of({ delivery: new Delivery(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TruckplannerTestModule],
                declarations: [DeliveryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DeliveryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DeliveryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.delivery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
