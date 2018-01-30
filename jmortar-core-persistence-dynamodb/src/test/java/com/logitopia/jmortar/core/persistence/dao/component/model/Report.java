/*
 * Software is the property of Stephen Cheesley.
 * All Rights Reserved.
 */
package com.logitopia.jmortar.core.persistence.dao.component.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.logitopia.jmortar.core.persistence.annotation.Key;
import com.logitopia.jmortar.core.persistence.dao.annotation.DynamoDBPersistent;

import java.util.Date;

/**
 * A travel report.
 *
 * @author Stephen Cheesley
 */
@DynamoDBTable(tableName = "Report")
@DynamoDBPersistent()
public class Report {

    /**
     * The ID of the report.
     */
    @Key
    private Long reportId;

    /**
     * The travel line.
     */
    private String lineName;

    /**
     * The status of the report.
     */
    private int status;

    /**
     * The report type.
     */
    private ReportType type;

    /**
     * The time at which the report was logged.
     */
    private Date time;

    @DynamoDBHashKey(attributeName = "reportId")
    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    @DynamoDBAttribute(attributeName = "lineName")
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @DynamoDBAttribute(attributeName = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "type")
    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    @DynamoDBAttribute(attributeName = "time")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
