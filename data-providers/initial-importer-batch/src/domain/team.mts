import { BasicEvent } from "../utils/BasicEvent.mjs";


export interface CreateTeamCommand extends BasicEvent {
    team: object
}

export function createTeamCommand(res: any) : CreateTeamCommand {
    return {
        eventName: "CreateTeamCommand",
        team: {
            name: res.name,
            short_name: res.team_short_name
        }
    }

}