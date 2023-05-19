import * as cdk from 'aws-cdk-lib';
import { Duration, Stack } from 'aws-cdk-lib';
import * as events from 'aws-cdk-lib/aws-events';
import { Construct } from 'constructs';

// import * as sqs from 'aws-cdk-lib/aws-sqs';

export class YoubetMessageStack extends cdk.Stack {
  bus: cdk.aws_events.EventBus;

  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
    this.bus = new events.EventBus(this, 'bus', {
      eventBusName: 'YouBetEventBus'
    });
    
    this.bus.archive('YoubetEventArchive', {
      archiveName: 'YoubetEventArchive',
      description: 'YoubetArchive EventArchive',
      eventPattern: {
        account: [Stack.of(this).account],
      },
      retention: Duration.days(365),
    });
  }
}
