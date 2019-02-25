/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { TruckService } from 'app/entities/truck/truck.service';
import { ITruck, Truck, Cool, TopType } from 'app/shared/model/truck.model';

describe('Service Tests', () => {
    describe('Truck Service', () => {
        let injector: TestBed;
        let service: TruckService;
        let httpMock: HttpTestingController;
        let elemDefault: ITruck;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(TruckService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Truck(
                0,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                false,
                false,
                false,
                Cool.NO,
                0,
                0,
                0,
                TopType.SOFT
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Truck', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Truck(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Truck', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 'BBBBBB',
                        truckCountry: 'BBBBBB',
                        trailerCountry: 'BBBBBB',
                        truckLicense: 'BBBBBB',
                        trailerLicense: 'BBBBBB',
                        adr: true,
                        big: true,
                        rollerBed: true,
                        cool: 'BBBBBB',
                        minTemperature: 1,
                        maxTemperature: 1,
                        maximumHeight: 1,
                        topType: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Truck', async () => {
                const returnedFromService = Object.assign(
                    {
                        number: 'BBBBBB',
                        truckCountry: 'BBBBBB',
                        trailerCountry: 'BBBBBB',
                        truckLicense: 'BBBBBB',
                        trailerLicense: 'BBBBBB',
                        adr: true,
                        big: true,
                        rollerBed: true,
                        cool: 'BBBBBB',
                        minTemperature: 1,
                        maxTemperature: 1,
                        maximumHeight: 1,
                        topType: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
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

            it('should delete a Truck', async () => {
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
