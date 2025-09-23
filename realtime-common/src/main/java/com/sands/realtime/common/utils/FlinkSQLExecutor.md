```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.common.utils.FlinkSQLExecutor \
$FLINK_HOME/lib/common/realtime-common-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

org.apache.hadoop.conf.Configuration and com.ctc.wstx.io.InputBootstrapper - issues temporarily resolved by mv flink-s3-hadoop and flink-shaded-hadoop to lib/common

### **Flink S3 Hadoop dependency policy**:

plugins/s3-fs-hadoop/flink-s3-fs-hadoop only for DataStream API, which does not support Table API & SQL.
BUT, lib/common/flink-s3-fs-hadoop and lib/common/flink-shaded-hadoop, which supports both DataStream API and Table API & SQL.
In addition, PS: flink-s3-fs-hadoop can only occur once, alternative one place in plugins/s3-fs-hadoop/ or in lib/common