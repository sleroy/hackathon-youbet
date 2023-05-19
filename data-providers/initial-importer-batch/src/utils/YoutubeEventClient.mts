import { EventBridgeClient, PutEventsCommand, PutEventsCommandInput, PutEventsCommandOutput } from "@aws-sdk/client-eventbridge";
import { BasicEvent } from './BasicEvent.mjs';
import { logger } from "./logger.mjs";

export class YoubetEventBusClient {

    private eventBusName = "YouBetEventBus";

    constructor(private client: EventBridgeClient) {

    }

    send(payload: BasicEvent): Promise<PutEventsCommandOutput> {
        const params = {
            /** input parameters */
        };
        logger.debug(payload)
        const input: PutEventsCommandInput = { // PutEventsRequest
            Entries: [ // PutEventsRequestEntryList // required
                { // PutEventsRequestEntry
                    Time: new Date(),
                    Source: "InitialImporter",
                    Resources: [],
                    DetailType: payload.eventName,
                    Detail: JSON.stringify(payload),
                    EventBusName: this.eventBusName
                },
            ],
        };
        
        const command = new PutEventsCommand(input);
        return this.client.send(command);
    }
}