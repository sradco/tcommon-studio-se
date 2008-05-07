package org.talend.dataprofiler.persistence;

// Generated Apr 23, 2008 1:33:52 PM by Hibernate Tools 3.2.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TdqAnalysis generated by hbm2java
 */
public class TdqAnalysis implements java.io.Serializable {

    private Integer anPk;

    private String anUuid;

    private String anLabel;

    private Date anCreationDate;

    private String anAuthor;

    private String anDataFilter;

    private String anStatus;

    private String repUuid;

    private Date repCreationDate;

    private String repLabel;

    private String repAuthor;

    private String repStatus;

    private Date anBeginDate;

    private Date anEndDate;

    private int anVersion;

    private Set<TdqIndicatorValue> tdqIndicatorValues = new HashSet<TdqIndicatorValue>(0);

    public TdqAnalysis() {
    }

    public TdqAnalysis(String anUuid, String anLabel, Date anCreationDate, String anAuthor, String anDataFilter, String anStatus,
            String repUuid, Date repCreationDate, String repLabel, String repAuthor, String repStatus, Date anBeginDate,
            Date anEndDate, int anVersion) {
        this.anUuid = anUuid;
        this.anLabel = anLabel;
        this.anCreationDate = anCreationDate;
        this.anAuthor = anAuthor;
        this.anDataFilter = anDataFilter;
        this.anStatus = anStatus;
        this.repUuid = repUuid;
        this.repCreationDate = repCreationDate;
        this.repLabel = repLabel;
        this.repAuthor = repAuthor;
        this.repStatus = repStatus;
        this.anBeginDate = anBeginDate;
        this.anEndDate = anEndDate;
        this.anVersion = anVersion;
    }

    public TdqAnalysis(String anUuid, String anLabel, Date anCreationDate, String anAuthor, String anDataFilter, String anStatus,
            String repUuid, Date repCreationDate, String repLabel, String repAuthor, String repStatus, Date anBeginDate,
            Date anEndDate, int anVersion, Set<TdqIndicatorValue> tdqIndicatorValues) {
        this.anUuid = anUuid;
        this.anLabel = anLabel;
        this.anCreationDate = anCreationDate;
        this.anAuthor = anAuthor;
        this.anDataFilter = anDataFilter;
        this.anStatus = anStatus;
        this.repUuid = repUuid;
        this.repCreationDate = repCreationDate;
        this.repLabel = repLabel;
        this.repAuthor = repAuthor;
        this.repStatus = repStatus;
        this.anBeginDate = anBeginDate;
        this.anEndDate = anEndDate;
        this.anVersion = anVersion;
        this.tdqIndicatorValues = tdqIndicatorValues;
    }

    public Integer getAnPk() {
        return this.anPk;
    }

    public void setAnPk(Integer anPk) {
        this.anPk = anPk;
    }

    public String getAnUuid() {
        return this.anUuid;
    }

    public void setAnUuid(String anUuid) {
        this.anUuid = anUuid;
    }

    public String getAnLabel() {
        return this.anLabel;
    }

    public void setAnLabel(String anLabel) {
        this.anLabel = anLabel;
    }

    public Date getAnCreationDate() {
        return this.anCreationDate;
    }

    public void setAnCreationDate(Date anCreationDate) {
        this.anCreationDate = anCreationDate;
    }

    public String getAnAuthor() {
        return this.anAuthor;
    }

    public void setAnAuthor(String anAuthor) {
        this.anAuthor = anAuthor;
    }

    public String getAnDataFilter() {
        return this.anDataFilter;
    }

    public void setAnDataFilter(String anDataFilter) {
        this.anDataFilter = anDataFilter;
    }

    public String getAnStatus() {
        return this.anStatus;
    }

    public void setAnStatus(String anStatus) {
        this.anStatus = anStatus;
    }

    public String getRepUuid() {
        return this.repUuid;
    }

    public void setRepUuid(String repUuid) {
        this.repUuid = repUuid;
    }

    public Date getRepCreationDate() {
        return this.repCreationDate;
    }

    public void setRepCreationDate(Date repCreationDate) {
        this.repCreationDate = repCreationDate;
    }

    public String getRepLabel() {
        return this.repLabel;
    }

    public void setRepLabel(String repLabel) {
        this.repLabel = repLabel;
    }

    public String getRepAuthor() {
        return this.repAuthor;
    }

    public void setRepAuthor(String repAuthor) {
        this.repAuthor = repAuthor;
    }

    public String getRepStatus() {
        return this.repStatus;
    }

    public void setRepStatus(String repStatus) {
        this.repStatus = repStatus;
    }

    public Date getAnBeginDate() {
        return this.anBeginDate;
    }

    public void setAnBeginDate(Date anBeginDate) {
        this.anBeginDate = anBeginDate;
    }

    public Date getAnEndDate() {
        return this.anEndDate;
    }

    public void setAnEndDate(Date anEndDate) {
        this.anEndDate = anEndDate;
    }

    public int getAnVersion() {
        return this.anVersion;
    }

    public void setAnVersion(int anVersion) {
        this.anVersion = anVersion;
    }

    public Set<TdqIndicatorValue> getTdqIndicatorValues() {
        return this.tdqIndicatorValues;
    }

    public void setTdqIndicatorValues(Set<TdqIndicatorValue> tdqIndicatorValues) {
        this.tdqIndicatorValues = tdqIndicatorValues;
    }

    public boolean valueEqual(TdqAnalysis tdqAnalysis) {
        if (!this.anAuthor.equals(tdqAnalysis.getAnAuthor())) {
            return false;
        } else if (!this.anDataFilter.equals(tdqAnalysis.getAnDataFilter())) {
            return false;
        } else if (!this.anLabel.equals(tdqAnalysis.getAnLabel())) {
            return false;
        } else if (!this.anStatus.equals(tdqAnalysis.getAnStatus())) {
            return false;
        } else if (!this.repAuthor.equals(tdqAnalysis.getRepAuthor())) {
            return false;
        } else if (!this.repLabel.equals(tdqAnalysis.getRepLabel())) {
            return false;
        } else if (!this.repStatus.equals(tdqAnalysis.getRepStatus())) {
            return false;
        }
        return true;
    }
}
