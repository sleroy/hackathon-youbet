import { BasicEvent } from "../utils/BasicEvent.mjs";


export interface CreateLeagueCommand extends BasicEvent {
    league: object
}

export function createLeagueCommand(res: any) : CreateLeagueCommand {
    return {
        eventName: "CreateLeagueCommand",
        league: {
            name: res.name
        }
    }

}