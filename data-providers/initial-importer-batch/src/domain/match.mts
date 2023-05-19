import { BasicEvent } from "../utils/BasicEvent.mjs";


export interface RegisterMatchCommand extends BasicEvent {
    match: object
}

export function registerMatchCommand(res: any) : RegisterMatchCommand {
    return {
        eventName: "RegisterMatchCommand",
        match: {
            name: res.name,
            season: res.season,
            stage: res.sage,
            date:  res.date,
            thome: res.thome,
            taway: res.taway
        }
    }

}

export function updateMatchDetailsCommand(state:string, res: any) : RegisterMatchCommand {
    return {
        eventName: "UpdateMatchDetailsCommand",
        match: {
            name: res.name,
            season: res.season,
            stage: res.sage,
            date:  res.date,
            state: state,

        }
    }

}