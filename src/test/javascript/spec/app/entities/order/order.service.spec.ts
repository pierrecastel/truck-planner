/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OrderService } from 'app/entities/order/order.service';
import { IOrder, Order, OrderType, OrderStatus, Mode } from 'app/shared/model/order.model';

describe('Service Tests', () => {
    describe('Order Service', () => {
        let injector: TestBed;
        let service: OrderService;
        let httpMock: HttpTestingController;
        let elemDefault: IOrder;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(OrderService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Order(
                0,
                0,
                OrderType.NEW,
                OrderStatus.PRE_ADVICE,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                0,
                0,
                currentDate,
                currentDate,
                currentDate,
                Mode.FTL,
                0
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        requestTimestamp: currentDate.format(DATE_FORMAT),
                        truckingDate: currentDate.format(DATE_TIME_FORMAT),
                        departureTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTimeLocal: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Order', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        requestTimestamp: currentDate.format(DATE_FORMAT),
                        truckingDate: currentDate.format(DATE_TIME_FORMAT),
                        departureTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTimeLocal: currentDate.format(DATE_TIME_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requestTimestamp: currentDate,
                        truckingDate: currentDate,
                        departureTimeLocal: currentDate,
                        arrivalTimeLocal: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Order(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Order', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 1,
                        orderType: 'BBBBBB',
                        orderStatus: 'BBBBBB',
                        requestTimestamp: currentDate.format(DATE_FORMAT),
                        origin: 'BBBBBB',
                        destination: 'BBBBBB',
                        weight: 1,
                        volume: 1,
                        truckingDate: currentDate.format(DATE_TIME_FORMAT),
                        departureTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        mode: 'BBBBBB',
                        requestedPositions: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        requestTimestamp: currentDate,
                        truckingDate: currentDate,
                        departureTimeLocal: currentDate,
                        arrivalTimeLocal: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Order', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 1,
                        orderType: 'BBBBBB',
                        orderStatus: 'BBBBBB',
                        requestTimestamp: currentDate.format(DATE_FORMAT),
                        origin: 'BBBBBB',
                        destination: 'BBBBBB',
                        weight: 1,
                        volume: 1,
                        truckingDate: currentDate.format(DATE_TIME_FORMAT),
                        departureTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        arrivalTimeLocal: currentDate.format(DATE_TIME_FORMAT),
                        mode: 'BBBBBB',
                        requestedPositions: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        requestTimestamp: currentDate,
                        truckingDate: currentDate,
                        departureTimeLocal: currentDate,
                        arrivalTimeLocal: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Order', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
