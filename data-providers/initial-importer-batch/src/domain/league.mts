import { BasicEvent } from "../utils/BasicEvent.mjs";


export interface CountryCommand extends BasicEvent {
    country: object
}

export function createLeagueCommand(res: any) : CountryCommand {
    return {
        eventName: "CreateLeagueCommand",
        country: {
            name: res.name
        }
    }

}