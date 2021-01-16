export interface CBResult {
    value: result;
}
export interface result {
    stringValue: string;
    intValue: number;
    boolValue: boolean;
    longValue: number;
    objValue: object[];
    error: number;
}