import { Moment } from 'moment';
import { ITruck } from 'app/shared/model/truck.model';
import { IDelivery } from 'app/shared/model/delivery.model';
import { ITruckCompany } from 'app/shared/model/truck-company.model';

export const enum OrderType {
    NEW = 'NEW',
    SENT = 'SENT',
    CANCELED = 'CANCELED',
    MODIFIED = 'MODIFIED',
    CONFIRMED = 'CONFIRMED'
}

export const enum OrderStatus {
    PRE_ADVICE = 'PRE_ADVICE',
    FINAL = 'FINAL'
}

export const enum Mode {
    FTL = 'FTL',
    LTL = 'LTL',
    WEIGHT = 'WEIGHT'
}

export interface IOrder {
    id?: number;
    number?: number;
    orderType?: OrderType;
    orderStatus?: OrderStatus;
    requestTimestamp?: Moment;
    origin?: string;
    destination?: string;
    weight?: number;
    volume?: number;
    truckingDate?: Moment;
    departureTimeLocal?: Moment;
    arrivalTimeLocal?: Moment;
    mode?: Mode;
    requestedPositions?: number;
    truck?: ITruck;
    delivery?: IDelivery;
    truckCompany?: ITruckCompany;
}

export class Order implements IOrder {
    constructor(
        public id?: number,
        public number?: number,
        public orderType?: OrderType,
        public orderStatus?: OrderStatus,
        public requestTimestamp?: Moment,
        public origin?: string,
        public destination?: string,
        public weight?: number,
        public volume?: number,
        public truckingDate?: Moment,
        public departureTimeLocal?: Moment,
        public arrivalTimeLocal?: Moment,
        public mode?: Mode,
        public requestedPositions?: number,
        public truck?: ITruck,
        public delivery?: IDelivery,
        public truckCompany?: ITruckCompany
    ) {}
}
