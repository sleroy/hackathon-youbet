#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { YoubetPredictionStack } from '../lib/youbet-prediction-stack';
import { YoubetMatchSystemStack } from '../lib/youbet-matchsystem-stack';
import { YoubetMessageStack } from '../lib/youbet-message-stack';
import { YoubetVpcStack } from '../lib/youbet-vpc-stack';

const app = new cdk.App();

const env = { account: '897212419817', region: 'us-east-1' };

const vpcStack = new YoubetVpcStack(app, 'YoubetVpcStack', {
  env: env
});

new YoubetMessageStack(app, 'YoubetMessageStack', {
  env: env
});

new YoubetPredictionStack(app, 'YoubetPredictionStack', {
  env: env
});
new YoubetMatchSystemStack(app, 'YoubetMatchSystemStack', {
  env: env,
  vpc: vpcStack.vpc
});