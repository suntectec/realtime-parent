```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/kafka2paimon.s3.sql
```

### sqlserver2paimon.s3.sql raised org.apache.hadoop.conf.Configuration and com.ctc.wstx.io.InputBootstrapper - issues temporarily resolved by mv flink-s3-hadoop and flink-shaded-hadoop to lib/common

**Flink S3 Hadoop dependency policy**:

plugins/s3-fs-hadoop/flink-s3-fs-hadoop only for DataStream API, which does not support Table API & SQL.
BUT, lib/common/flink-s3-fs-hadoop and lib/common/flink-shaded-hadoop, which supports both DataStream API and Table API & SQL.
In addition, PS: flink-s3-fs-hadoop can only occur once, alternative one place in plugins/s3-fs-hadoop/ or in lib/common

### kafka2paimon.s3.sql raised java.lang.ClassCastException: cannot assign instance of org.apache.kafka.clients.consumer.OffsetResetStrategy to field org.apache.flink.connector.kafka.source.enumerator.initializer.ReaderHandledOffsetsInitializer.offsetResetStrategy of type org.apache.kafka.clients.consumer.OffsetResetStrategy in instance of org.apache.flink.connector.kafka.source.enumerator.initializer.ReaderHandledOffsetsInitializer

**Flink Kafka dependency policy**: FlinkSQLExecutor Local Test Passed, BUT Submit to Flink Cluster Failed. Because of Kafka-Clients OffsetResetStrategy ClassCastException dependency conflict between flink-connector-kafka and kafka-clients in realtime-common jar. 
Issue resolved by add configuration 
```
config = Configuration()
config.set_string("classloader.resolve-order", "parent-first")
```

refers to: [config "classloader.resolve-order", "parent-first"](https://www.waitingforcode.com/apache-flink/yes-i-am-learning-apache-flink-beginners-problems/read)