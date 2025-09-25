package com.sands.realtime.ods.app;

import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.TableResult;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @author Jagger
 * @since 2025/9/25 10:11
 */
@Slf4j
public class OdsKafkaToPaimonS3APP1 {
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

        tableEnv.executeSql("CREATE DATABASE IF NOT EXISTS inventory;");

        tableEnv.executeSql("USE inventory;");

        tableEnv.executeSql("CREATE TEMPORARY TABLE OrdersKafkaSource\n" +
                "(\n" +
                "    id              BIGINT,\n" +
                "    order_id        STRING,\n" +
                "    supplier_id     INT,\n" +
                "    item_id         INT,\n" +
                "    status          STRING,\n" +
                "    qty             INT,\n" +
                "    net_price       INT,\n" +
                "    issued_at       TIMESTAMP,\n" +
                "    completed_at    TIMESTAMP,\n" +
                "    spec            STRING,\n" +
                "    created_at      TIMESTAMP,\n" +
                "    updated_at      TIMESTAMP,\n" +
                "    _row_kind STRING,\n" +
                "    _ingestion_time TIMESTAMP,\n" +
                "    _process_time   TIMESTAMP,\n" +
                "    _source_time    TIMESTAMP,\n" +
                "    _record_time TIMESTAMP_LTZ(3) METADATA FROM 'timestamp'    -- reads and writes a Kafka record's timestamp\n" +
                ") WITH (\n" +
                "      'connector' = 'kafka',\n" +
                "      'topic' = 'ods_orders_topic',\n" +
                "      'properties.bootstrap.servers' = '192.168.138.15:9092',\n" +
                "      'properties.group.id' = 'data_group',\n" +
                "      'scan.startup.mode' = 'earliest-offset',\n" +
                "      'format' = 'json');");

        tableEnv.executeSql("CREATE TABLE IF NOT EXISTS OrdersFromKafka (\n" +
                "    id              BIGINT,\n" +
                "    order_id        STRING,\n" +
                "    supplier_id     INT,\n" +
                "    item_id         INT,\n" +
                "    status          STRING,\n" +
                "    qty             INT,\n" +
                "    net_price       INT,\n" +
                "    issued_at       TIMESTAMP,\n" +
                "    completed_at    TIMESTAMP,\n" +
                "    spec            STRING,\n" +
                "    created_at      TIMESTAMP,\n" +
                "    updated_at      TIMESTAMP,\n" +
                "    _row_kind STRING,\n" +
                "    _ingestion_time TIMESTAMP,\n" +
                "    _process_time   TIMESTAMP,\n" +
                "    _source_time    TIMESTAMP,\n" +
                "    _record_time    TIMESTAMP,\n" +
                "    PRIMARY KEY (id) NOT ENFORCED\n" +
                "    );");

        TableResult tableResult = tableEnv.executeSql("INSERT INTO OrdersFromKafka SELECT * FROM OrdersKafkaSource;");
        if (tableResult.getJobClient().isPresent()) log.info("----------"+tableResult.getJobClient().get().getJobStatus());

        // tableEnv.sqlQuery("select * from OrdersFromKafka").execute().print();

    }
}
