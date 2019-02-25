export const enum DeliveryType {
    DPU = 'DPU',
    DTA = 'DTA'
}

export interface IDelivery {
    id?: number;
    type?: DeliveryType;
    agentName?: string;
    street?: string;
    city?: string;
}

export class Delivery implements IDelivery {
    constructor(public id?: number, public type?: DeliveryType, public agentName?: string, public street?: string, public city?: string) {}
}
