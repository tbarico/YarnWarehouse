export class StorageLocation {
    public locationId :number;
    public currentCapacity :number;
    public totalCapacity :number;
    public phoneNumber :string;
    public type :string;
    public valid :boolean;

    constructor(locationId: number, currentCapacity: number, totalCapacity: number, phoneNumber: string, type: string, valid: boolean) {
        this.locationId = locationId;
        this.currentCapacity = currentCapacity;
        this.totalCapacity = totalCapacity;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.valid = valid;
    } 
}
