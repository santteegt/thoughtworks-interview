package com.thoughtworks.galactic.merchant.util;

/**
 * Created by santteegt on 17/11/2016.
 * Rules that validate a Roman value as correct
 */
public enum RomanRules {

    REPEATED_LITERALS(false, "IXCM", "[IXCM]{0,3}", "IXCM can be consecutively repeated at most 3 times"),
    NON_REPEATED_CONSTRAINT(true, "DLV", "[DLV]{2,}", "DLV cannot be consecutively repeated"),
    SUFFIX_I_CONTRAINT(true, "ILCDM", "I[LCDM]", "I cannot subtract to LCDM left neighbors"),
    SUFFIX_X_CONSTRAINT(true, "XIVDM", "X[IVDM]", "X cannot subtract to IVDM left neighbors"),
    SUFFIX_C_CONSTRAINT(true, "CIVXL", "C[IVXL]", "C cannot subtract from IVXL left neighbors"),
    SUBTRACT_CONSTRAINT(true, "VLD", "(V[XLCDM]|L[CDM]|DM)", "VLD cannot subtract with left neighbors");


    private Boolean failCondition;

    private String involvedLiterals;

    private String pattern;

    private String errorMessage;

    RomanRules(Boolean failCondition, String involvedLiterals, String pattern, String errorMsg) {
        this.failCondition = failCondition;
        this.involvedLiterals = involvedLiterals;
        this.pattern = pattern;
        this.errorMessage = errorMsg;
    }

    public Boolean getFailCondition() {
        return failCondition;
    }

    public String getPattern() {
        return pattern;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getInvolvedLiterals() {
        return involvedLiterals;
    }
}
