```shell
scp target/realtime-ads-1.0-SNAPSHOT.jar Data.Eng@192.168.138.15:/opt/poc-allin1/native/flink/flink-1.20.1/usrlib
```

### sqlserver2paimon.s3.sql

```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.ads.app.FlinkSQLExecutor \
$FLINK_HOME/usrlib/realtime-ads-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/sqlserver2paimon.s3.sql
```

### kafka2paimon.s3.sql

```
$FLINK_HOME/bin/flink run -d \
-c com.sands.realtime.ads.app.FlinkSQLExecutor \
$FLINK_HOME/usrlib/realtime-ads-1.0-SNAPSHOT.jar \
--sql $FLINK_HOME/scripts/kafka2paimon.s3.sql
```
