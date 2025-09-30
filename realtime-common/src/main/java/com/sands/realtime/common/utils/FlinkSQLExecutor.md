### sqlserver2paimon.s3.sql

```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

**Issue**: `java.lang.ClassNotFoundException: org.apache.hadoop.conf.Configuration and com.ctc.wstx.io.InputBootstrapper`
**Reason**: FLINK_HOME/lib and FLINK_HOME/plugins dependency conflict.
**Solution**: Temporarily resolved by mv flink-s3-hadoop and flink-shaded-hadoop to lib/common.
After TEST, plugins/s3-fs-hadoop/flink-s3-fs-hadoop only for DataStream API, which does not support Flink Table API & SQL.
BUT, lib/common/flink-s3-fs-hadoop and lib/common/flink-shaded-hadoop, which supports both DataStream API and Table API & SQL.
In addition, PS: flink-s3-fs-hadoop can only occur once, alternative one place in plugins/s3-fs-hadoop/ or in lib/common

### kafka2paimon.s3.sql

```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/kafka2paimon.s3.sql
```

**Issue**: `java.lang.ClassCastException: cannot assign instance of org.apache.kafka.clients.consumer.OffsetResetStrategy to field org.apache.flink.connector.kafka.source.enumerator.initializer.ReaderHandledOffsetsInitializer.offsetResetStrategy of type org.apache.kafka.clients.consumer.OffsetResetStrategy in instance of org.apache.flink.connector.kafka.source.enumerator.initializer.ReaderHandledOffsetsInitializer`
**Reason**: Flink Kafka dependency policy. FlinkSQLExecutor Local Test Passed, BUT Submit to Flink Cluster Failed. Because of Kafka-Clients OffsetResetStrategy ClassCastException dependency conflict between flink-connector-kafka and kafka-clients in realtime-common jar.
**Solution**: Change flink-connector-kafka to flink-sql-connector-kafka dependency.
~~1| tried to change Kafka-Client version 3.9.1 to 3.4.0 but still failed;
2| tried remove Kafka-Client version 3.9.1 dependency but still failed;
3| added config.set_string("classloader.resolve-order", "parent-first") but still failed ([reference](https://www.waitingforcode.com/apache-flink/yes-i-am-learning-apache-flink-beginners-problems/read));~~
4| tried to change flink-connector-kafka to flink-sql-connector-kafka that successful ([reference](https://github.com/apache/paimon/issues/803));
