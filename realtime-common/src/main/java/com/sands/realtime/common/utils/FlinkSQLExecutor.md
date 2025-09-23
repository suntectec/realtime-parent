```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

DataStream API Jar Package: plugins/s3-fs-hadoop/flink-s3-fs-hadoop

SQL Client Jar Package: mv plugins/s3-fs-hadoop/flink-s3-fs-hadoop and add flink-shaded-hadoop to lib/common

org.apache.hadoop.conf.Configuration and com.ctc.wstx.io.InputBootstrapper - issues temporarily resolved by mv flink-s3-hadoop and flink-shaded-hadoop to lib/common