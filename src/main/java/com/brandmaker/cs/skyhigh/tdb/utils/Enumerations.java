package com.brandmaker.cs.skyhigh.tdb.utils;

/**
 * Class for enumerations
 */
public class Enumerations {

    /**
     * Enumeration for Raw PO Data load status
     */
    public enum LoadStatusEnum
    {
        LOADED("Loaded"),
        PROCESSED("Processed");

        private String status;

        LoadStatusEnum(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    /**
     * Enumeration for Raw PO Data Process outcome status
     */
    public enum ProcessOutcomeStatusEnum
    {
        SUCCESSED("Success"),
        FAILED("Failed");

        private String status;

        ProcessOutcomeStatusEnum (String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum InheritFromParentEnum {

        NOT_SUPPORTED("NOT_SUPPORTED"),
        INHERITED_FROM_PARENT("INHERITED_FROM_PARENT"),
        BROKEN_INHERITANCE(" BROKEN_INHERITANCE");

        private String status;

        InheritFromParentEnum (String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public enum SftpUploadFileTypeEnum {
        ARCHIVE,
        LOG,
        DB_EXTREME_EXPORT
    }
}
