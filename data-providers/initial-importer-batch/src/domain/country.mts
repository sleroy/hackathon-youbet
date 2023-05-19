import { BasicEvent } from "../utils/BasicEvent.mjs";


export interface CountryCommand extends BasicEvent {
    country: object
}

export function createCountryCommand(res: any) : CountryCommand {
    return {
        eventName: "CreateCountryCommand",
        country: {
            name: res.name
        }
    }

}