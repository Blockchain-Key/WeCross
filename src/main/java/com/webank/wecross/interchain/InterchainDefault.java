package com.webank.wecross.interchain;

public class InterchainDefault {
    public static final int CALL_TYPE_QUERY = 0;
    public static final int CALL_TYPE_INVOKE = 1;

    public static final String SPLIT_REGEX = " ";
    public static final String NULL_FLAG = "null";
    public static final String INTER_CHAIN_JOB_DATA_KEY = "inter_chain_job";
    public static final String MAX_NUMBER_PER_POLLING = "16";
    public static final String GET_INTER_CHAIN_REQUESTS_METHOD = "getInterchainRequests";
    public static final String UPDATE_CURRENT_REQUEST_INDEX_METHOD = "updateCurrentRequestIndex";
    public static final String GET_TRANSACTION_STATE_METHOD = "getTransactionState";
    public static final String REGISTER_CALLBACK_RESULT_METHOD = "registerCallbackResult";
}