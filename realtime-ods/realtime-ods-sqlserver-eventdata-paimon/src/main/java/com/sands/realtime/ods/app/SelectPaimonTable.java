package com.sands.realtime.ods.app;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jagger
 * @since 2025/9/11 17:51
 */
public class SelectPaimonTable {
    public static void main(String[] args) {

        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        streamEnv.enableCheckpointing(10000);
        streamEnv.setParallelism(1);
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(streamEnv);

        tableEnv.executeSql("CREATE CATALOG paimon_catalog WITH (\n" +
                "    'type'='paimon',\n" +
                "    'warehouse'='s3://lakehouse/paimon/',\n" +
                "    's3.endpoint'='http://192.168.138.15:9000',\n" +
                "    's3.access-key'='minioadmin',\n" +
                "    's3.secret-key'='minioadmin',\n" +
                "    's3.path.style.access'='true'\n" +
                ");");

        tableEnv.executeSql("USE CATALOG paimon_catalog;");

        tableEnv.executeSql("USE inventory;");

        tableEnv.sqlQuery("SELECT * FROM orders_from_sqlserver;").execute().print();

    }
}
