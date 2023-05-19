import * as cdk from 'aws-cdk-lib';
import * as rds from 'aws-cdk-lib/aws-rds';
import * as ec2 from 'aws-cdk-lib/aws-ec2';

import { Construct } from 'constructs';
import { CfnOutput, Duration, Stack, Token } from 'aws-cdk-lib/core'
import { CdkResourceInitializer } from './resource-initializer-props'
import { DockerImageCode } from 'aws-cdk-lib/aws-lambda'
import { InstanceClass, InstanceSize, InstanceType, Port, SubnetType, Vpc } from 'aws-cdk-lib/aws-ec2'
import { RetentionDays } from 'aws-cdk-lib/aws-logs'
import { Credentials, DatabaseInstance, DatabaseInstanceEngine, DatabaseSecret, MysqlEngineVersion } from 'aws-cdk-lib/aws-rds'

// import * as sqs from 'aws-cdk-lib/aws-sqs';


export interface YoubetMatchSystemStackProps extends cdk.StackProps {
  vpc: Vpc
}

const Aurora = {
  secretId: "AuroraSecret",
  secretUserName : 'clusteradmin',
  cluster : 'MatchSystemAuroraCluster',
  clusterIdentifier: 'db-matchsystem-endpoint',
  defaultDatabaseName: 'matchsystem',
  instanceIdentifier : 'mysql-01'
}


// https://dev.to/aws-builders/handling-serverless-aurora-schema-migration-using-custom-resource-with-python-cdk-5fpc
// https://aws.amazon.com/blogs/infrastructure-and-automation/use-aws-cdk-to-initialize-amazon-rds-instances/

export class YoubetMatchSystemStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props: YoubetMatchSystemStackProps) {
    super(scope, id, props);

    // Provisioning of a simple aurora database server
    // Create username and password secret for DB Cluster

    const credsSecretName = `/${id}/aurora/creds/${Aurora.instanceIdentifier}`.toLowerCase()
    const creds = new rds.DatabaseSecret(this, Aurora.secretId, {
      secretName: credsSecretName,
      username: Aurora.secretUserName,
    });


    // Create the serverless cluster, provide all values needed to customise the database.
    const cluster = new rds.ServerlessCluster(this, Aurora.cluster, {
      engine: rds.DatabaseClusterEngine.AURORA_MYSQL,
      vpc: props.vpc,
      credentials: Credentials.fromSecret(creds),
      clusterIdentifier: Aurora.clusterIdentifier,
      defaultDatabaseName: Aurora.defaultDatabaseName,
      vpcSubnets: {subnetType: ec2.SubnetType.PRIVATE_WITH_EGRESS},      
    });


    const initializer = new CdkResourceInitializer(this, 'MatchSystemInit', {
      config: {
        credsSecretName
      },
      fnLogRetention: RetentionDays.FIVE_MONTHS,
      fnCode: DockerImageCode.fromImageAsset(`${__dirname}/../demos/rds-init-fn-code`, {}),
      fnTimeout: Duration.minutes(2),
      fnSecurityGroups: [],
      vpc: props.vpc,
      subnetsSelection: props.vpc.selectSubnets({
        subnetType: SubnetType.PRIVATE_WITH_EGRESS
      })
    })
    // manage resources dependency
    initializer.customResource.node.addDependency(cluster)

    // allow the initializer function to connect to the RDS instance
    cluster.connections.allowFrom(initializer.function, Port.tcp(3306))

    // allow initializer function to read RDS instance creds secret
    creds.grantRead(initializer.function)

    /* eslint no-new: 0 */
    new CfnOutput(this, 'AuroraInitFnResponse', {
      value: Token.asString(initializer.response)
    })
  }

}
