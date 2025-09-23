```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

plugins/s3-fs-hadoop is for DataStream API Jar Package

mv plugins/s3-fs-hadoop/flink-s3-hadoop and add flink-shaded-hadoop to lib/common are for SQL Client
