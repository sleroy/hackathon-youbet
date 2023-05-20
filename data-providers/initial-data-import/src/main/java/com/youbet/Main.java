package com.youbet;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.EventBridgeException;
import software.amazon.awssdk.services.eventbridge.model.EventBus;
import software.amazon.awssdk.services.eventbridge.model.ListEventBusesRequest;
import software.amazon.awssdk.services.eventbridge.model.ListEventBusesResponse;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        Region region = Region.US_EAST_1;
        EventBridgeClient eventBrClient = EventBridgeClient.builder()
                                                           .region(region)
                                                           .credentialsProvider(ProfileCredentialsProvider.create())
                                                           .build();
        
        listBuses(eventBrClient);
        eventBrClient.close();
    }
    
    
    public static void listBuses(EventBridgeClient eventBrClient) {
        try {
            ListEventBusesRequest busesRequest = ListEventBusesRequest.builder()
                                                                      .limit(10)
                                                                      .build();
            
            ListEventBusesResponse response = eventBrClient.listEventBuses(busesRequest);
            List<EventBus> buses = response.eventBuses();
            for (EventBus bus : buses) {
                System.out.println("The name of the event bus is: " + bus.name());
                System.out.println("The ARN of the event bus is: " + bus.arn());
            }
            
        } catch (EventBridgeException e) {
            
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
    
}