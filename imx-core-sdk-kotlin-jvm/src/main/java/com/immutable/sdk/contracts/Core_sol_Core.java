package com.immutable.sdk.contracts;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Core_sol_Core extends Contract {
    public static final String BINARY = "";

    public static final String FUNC_ANNOUNCEAVAILABILITYVERIFIERREMOVALINTENT = "announceAvailabilityVerifierRemovalIntent";

    public static final String FUNC_ANNOUNCEVERIFIERREMOVALINTENT = "announceVerifierRemovalIntent";

    public static final String FUNC_deposit = "deposit";

    public static final String FUNC_DEPOSITCANCEL = "depositCancel";

    public static final String FUNC_DEPOSITERC20 = "depositERC20";

    public static final String FUNC_DEPOSITETH = "depositEth";

    public static final String FUNC_DEPOSITNFT = "depositNft";

    public static final String FUNC_DEPOSITNFTRECLAIM = "depositNftReclaim";

    public static final String FUNC_DEPOSITRECLAIM = "depositReclaim";

    public static final String FUNC_ESCAPE = "escape";

    public static final String FUNC_FREEZEREQUEST = "freezeRequest";

    public static final String FUNC_FULLWITHDRAWALREQUEST = "fullWithdrawalRequest";

    public static final String FUNC_GETASSETINFO = "getAssetInfo";

    public static final String FUNC_GETCANCELLATIONREQUEST = "getCancellationRequest";

    public static final String FUNC_GETDEPOSITBALANCE = "getDepositBalance";

    public static final String FUNC_GETETHKEY = "getEthKey";

    public static final String FUNC_GETFULLWITHDRAWALREQUEST = "getFullWithdrawalRequest";

    public static final String FUNC_GETLASTBATCHID = "getLastBatchId";

    public static final String FUNC_GETORDERROOT = "getOrderRoot";

    public static final String FUNC_GETORDERTREEHEIGHT = "getOrderTreeHeight";

    public static final String FUNC_GETQUANTIZEDDEPOSITBALANCE = "getQuantizedDepositBalance";

    public static final String FUNC_GETQUANTUM = "getQuantum";

    public static final String FUNC_GETREGISTEREDAVAILABILITYVERIFIERS = "getRegisteredAvailabilityVerifiers";

    public static final String FUNC_GETREGISTEREDVERIFIERS = "getRegisteredVerifiers";

    public static final String FUNC_GETSEQUENCENUMBER = "getSequenceNumber";

    public static final String FUNC_GETVAULTROOT = "getVaultRoot";

    public static final String FUNC_GETVAULTTREEHEIGHT = "getVaultTreeHeight";

    public static final String FUNC_GETWITHDRAWALBALANCE = "getWithdrawalBalance";

    public static final String FUNC_ISAVAILABILITYVERIFIER = "isAvailabilityVerifier";

    public static final String FUNC_ISFROZEN = "isFrozen";

    public static final String FUNC_ISOPERATOR = "isOperator";

    public static final String FUNC_ISTOKENADMIN = "isTokenAdmin";

    public static final String FUNC_ISUSERADMIN = "isUserAdmin";

    public static final String FUNC_ISVERIFIER = "isVerifier";

    public static final String FUNC_MAINACCEPTGOVERNANCE = "mainAcceptGovernance";

    public static final String FUNC_MAINCANCELNOMINATION = "mainCancelNomination";

    public static final String FUNC_MAINISGOVERNOR = "mainIsGovernor";

    public static final String FUNC_MAINNOMINATENEWGOVERNOR = "mainNominateNewGovernor";

    public static final String FUNC_MAINREMOVEGOVERNOR = "mainRemoveGovernor";

    public static final String FUNC_ONERC721RECEIVED = "onERC721Received";

    public static final String FUNC_REGISTERANDDEPOSITERC20 = "registerAndDepositERC20";

    public static final String FUNC_REGISTERANDDEPOSITETH = "registerAndDepositEth";

    public static final String FUNC_REGISTERAVAILABILITYVERIFIER = "registerAvailabilityVerifier";

    public static final String FUNC_REGISTEROPERATOR = "registerOperator";

    public static final String FUNC_REGISTERTOKEN = "registerToken";

    public static final String FUNC_REGISTERTOKENADMIN = "registerTokenAdmin";

    public static final String FUNC_REGISTERUSER = "registerUser";

    public static final String FUNC_REGISTERUSERADMIN = "registerUserAdmin";

    public static final String FUNC_REGISTERVERIFIER = "registerVerifier";

    public static final String FUNC_REMOVEAVAILABILITYVERIFIER = "removeAvailabilityVerifier";

    public static final String FUNC_REMOVEVERIFIER = "removeVerifier";

    public static final String FUNC_UNFREEZE = "unFreeze";

    public static final String FUNC_UNREGISTEROPERATOR = "unregisterOperator";

    public static final String FUNC_UNREGISTERTOKENADMIN = "unregisterTokenAdmin";

    public static final String FUNC_UNREGISTERUSERADMIN = "unregisterUserAdmin";

    public static final String FUNC_UPDATESTATE = "updateState";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final String FUNC_WITHDRAWANDMINT = "withdrawAndMint";

    public static final String FUNC_WITHDRAWNFT = "withdrawNft";

    public static final String FUNC_WITHDRAWNFTTO = "withdrawNftTo";

    public static final String FUNC_WITHDRAWTO = "withdrawTo";

    public static final Event LOGDEPOSIT_EVENT = new Event("LogDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGDEPOSITCANCEL_EVENT = new Event("LogDepositCancel", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGDEPOSITCANCELRECLAIMED_EVENT = new Event("LogDepositCancelReclaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGDEPOSITNFTCANCELRECLAIMED_EVENT = new Event("LogDepositNftCancelReclaimed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGFULLWITHDRAWALREQUEST_EVENT = new Event("LogFullWithdrawalRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGMINTWITHDRAWALPERFORMED_EVENT = new Event("LogMintWithdrawalPerformed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGMINTABLEWITHDRAWALALLOWED_EVENT = new Event("LogMintableWithdrawalAllowed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGNFTDEPOSIT_EVENT = new Event("LogNftDeposit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGNFTWITHDRAWALALLOWED_EVENT = new Event("LogNftWithdrawalAllowed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGNFTWITHDRAWALPERFORMED_EVENT = new Event("LogNftWithdrawalPerformed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event LOGROOTUPDATE_EVENT = new Event("LogRootUpdate", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGSTATETRANSITIONFACT_EVENT = new Event("LogStateTransitionFact", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    public static final Event LOGVAULTBALANCECHANGEAPPLIED_EVENT = new Event("LogVaultBalanceChangeApplied", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}));
    ;

    public static final Event LOGWITHDRAWALALLOWED_EVENT = new Event("LogWithdrawalAllowed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGWITHDRAWALPERFORMED_EVENT = new Event("LogWithdrawalPerformed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected Core_sol_Core(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Core_sol_Core(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Core_sol_Core(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Core_sol_Core(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<LogDepositEventResponse> getLogDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSIT_EVENT, transactionReceipt);
        ArrayList<LogDepositEventResponse> responses = new ArrayList<LogDepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDepositEventResponse typedResponse = new LogDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.depositorEthKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositEventResponse> logDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDepositEventResponse>() {
            @Override
            public LogDepositEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSIT_EVENT, log);
                LogDepositEventResponse typedResponse = new LogDepositEventResponse();
                typedResponse.log = log;
                typedResponse.depositorEthKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositEventResponse> logDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSIT_EVENT));
        return logDepositEventFlowable(filter);
    }

    public List<LogDepositCancelEventResponse> getLogDepositCancelEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSITCANCEL_EVENT, transactionReceipt);
        ArrayList<LogDepositCancelEventResponse> responses = new ArrayList<LogDepositCancelEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDepositCancelEventResponse typedResponse = new LogDepositCancelEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositCancelEventResponse> logDepositCancelEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDepositCancelEventResponse>() {
            @Override
            public LogDepositCancelEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSITCANCEL_EVENT, log);
                LogDepositCancelEventResponse typedResponse = new LogDepositCancelEventResponse();
                typedResponse.log = log;
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositCancelEventResponse> logDepositCancelEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSITCANCEL_EVENT));
        return logDepositCancelEventFlowable(filter);
    }

    public List<LogDepositCancelReclaimedEventResponse> getLogDepositCancelReclaimedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSITCANCELRECLAIMED_EVENT, transactionReceipt);
        ArrayList<LogDepositCancelReclaimedEventResponse> responses = new ArrayList<LogDepositCancelReclaimedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDepositCancelReclaimedEventResponse typedResponse = new LogDepositCancelReclaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositCancelReclaimedEventResponse> logDepositCancelReclaimedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDepositCancelReclaimedEventResponse>() {
            @Override
            public LogDepositCancelReclaimedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSITCANCELRECLAIMED_EVENT, log);
                LogDepositCancelReclaimedEventResponse typedResponse = new LogDepositCancelReclaimedEventResponse();
                typedResponse.log = log;
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositCancelReclaimedEventResponse> logDepositCancelReclaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSITCANCELRECLAIMED_EVENT));
        return logDepositCancelReclaimedEventFlowable(filter);
    }

    public List<LogDepositNftCancelReclaimedEventResponse> getLogDepositNftCancelReclaimedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSITNFTCANCELRECLAIMED_EVENT, transactionReceipt);
        ArrayList<LogDepositNftCancelReclaimedEventResponse> responses = new ArrayList<LogDepositNftCancelReclaimedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogDepositNftCancelReclaimedEventResponse typedResponse = new LogDepositNftCancelReclaimedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositNftCancelReclaimedEventResponse> logDepositNftCancelReclaimedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogDepositNftCancelReclaimedEventResponse>() {
            @Override
            public LogDepositNftCancelReclaimedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSITNFTCANCELRECLAIMED_EVENT, log);
                LogDepositNftCancelReclaimedEventResponse typedResponse = new LogDepositNftCancelReclaimedEventResponse();
                typedResponse.log = log;
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositNftCancelReclaimedEventResponse> logDepositNftCancelReclaimedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSITNFTCANCELRECLAIMED_EVENT));
        return logDepositNftCancelReclaimedEventFlowable(filter);
    }

    public List<LogFullWithdrawalRequestEventResponse> getLogFullWithdrawalRequestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGFULLWITHDRAWALREQUEST_EVENT, transactionReceipt);
        ArrayList<LogFullWithdrawalRequestEventResponse> responses = new ArrayList<LogFullWithdrawalRequestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogFullWithdrawalRequestEventResponse typedResponse = new LogFullWithdrawalRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogFullWithdrawalRequestEventResponse> logFullWithdrawalRequestEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogFullWithdrawalRequestEventResponse>() {
            @Override
            public LogFullWithdrawalRequestEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGFULLWITHDRAWALREQUEST_EVENT, log);
                LogFullWithdrawalRequestEventResponse typedResponse = new LogFullWithdrawalRequestEventResponse();
                typedResponse.log = log;
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogFullWithdrawalRequestEventResponse> logFullWithdrawalRequestEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGFULLWITHDRAWALREQUEST_EVENT));
        return logFullWithdrawalRequestEventFlowable(filter);
    }

    public List<LogMintWithdrawalPerformedEventResponse> getLogMintWithdrawalPerformedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGMINTWITHDRAWALPERFORMED_EVENT, transactionReceipt);
        ArrayList<LogMintWithdrawalPerformedEventResponse> responses = new ArrayList<LogMintWithdrawalPerformedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogMintWithdrawalPerformedEventResponse typedResponse = new LogMintWithdrawalPerformedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogMintWithdrawalPerformedEventResponse> logMintWithdrawalPerformedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogMintWithdrawalPerformedEventResponse>() {
            @Override
            public LogMintWithdrawalPerformedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGMINTWITHDRAWALPERFORMED_EVENT, log);
                LogMintWithdrawalPerformedEventResponse typedResponse = new LogMintWithdrawalPerformedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogMintWithdrawalPerformedEventResponse> logMintWithdrawalPerformedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGMINTWITHDRAWALPERFORMED_EVENT));
        return logMintWithdrawalPerformedEventFlowable(filter);
    }

    public List<LogMintableWithdrawalAllowedEventResponse> getLogMintableWithdrawalAllowedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGMINTABLEWITHDRAWALALLOWED_EVENT, transactionReceipt);
        ArrayList<LogMintableWithdrawalAllowedEventResponse> responses = new ArrayList<LogMintableWithdrawalAllowedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogMintableWithdrawalAllowedEventResponse typedResponse = new LogMintableWithdrawalAllowedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogMintableWithdrawalAllowedEventResponse> logMintableWithdrawalAllowedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogMintableWithdrawalAllowedEventResponse>() {
            @Override
            public LogMintableWithdrawalAllowedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGMINTABLEWITHDRAWALALLOWED_EVENT, log);
                LogMintableWithdrawalAllowedEventResponse typedResponse = new LogMintableWithdrawalAllowedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogMintableWithdrawalAllowedEventResponse> logMintableWithdrawalAllowedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGMINTABLEWITHDRAWALALLOWED_EVENT));
        return logMintableWithdrawalAllowedEventFlowable(filter);
    }

    public List<LogNftDepositEventResponse> getLogNftDepositEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNFTDEPOSIT_EVENT, transactionReceipt);
        ArrayList<LogNftDepositEventResponse> responses = new ArrayList<LogNftDepositEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNftDepositEventResponse typedResponse = new LogNftDepositEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.depositorEthKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogNftDepositEventResponse> logNftDepositEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogNftDepositEventResponse>() {
            @Override
            public LogNftDepositEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGNFTDEPOSIT_EVENT, log);
                LogNftDepositEventResponse typedResponse = new LogNftDepositEventResponse();
                typedResponse.log = log;
                typedResponse.depositorEthKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.starkKey = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogNftDepositEventResponse> logNftDepositEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGNFTDEPOSIT_EVENT));
        return logNftDepositEventFlowable(filter);
    }

    public List<LogNftWithdrawalAllowedEventResponse> getLogNftWithdrawalAllowedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNFTWITHDRAWALALLOWED_EVENT, transactionReceipt);
        ArrayList<LogNftWithdrawalAllowedEventResponse> responses = new ArrayList<LogNftWithdrawalAllowedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNftWithdrawalAllowedEventResponse typedResponse = new LogNftWithdrawalAllowedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogNftWithdrawalAllowedEventResponse> logNftWithdrawalAllowedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogNftWithdrawalAllowedEventResponse>() {
            @Override
            public LogNftWithdrawalAllowedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGNFTWITHDRAWALALLOWED_EVENT, log);
                LogNftWithdrawalAllowedEventResponse typedResponse = new LogNftWithdrawalAllowedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogNftWithdrawalAllowedEventResponse> logNftWithdrawalAllowedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGNFTWITHDRAWALALLOWED_EVENT));
        return logNftWithdrawalAllowedEventFlowable(filter);
    }

    public List<LogNftWithdrawalPerformedEventResponse> getLogNftWithdrawalPerformedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNFTWITHDRAWALPERFORMED_EVENT, transactionReceipt);
        ArrayList<LogNftWithdrawalPerformedEventResponse> responses = new ArrayList<LogNftWithdrawalPerformedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNftWithdrawalPerformedEventResponse typedResponse = new LogNftWithdrawalPerformedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogNftWithdrawalPerformedEventResponse> logNftWithdrawalPerformedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogNftWithdrawalPerformedEventResponse>() {
            @Override
            public LogNftWithdrawalPerformedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGNFTWITHDRAWALPERFORMED_EVENT, log);
                LogNftWithdrawalPerformedEventResponse typedResponse = new LogNftWithdrawalPerformedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.tokenId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogNftWithdrawalPerformedEventResponse> logNftWithdrawalPerformedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGNFTWITHDRAWALPERFORMED_EVENT));
        return logNftWithdrawalPerformedEventFlowable(filter);
    }

    public List<LogRootUpdateEventResponse> getLogRootUpdateEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGROOTUPDATE_EVENT, transactionReceipt);
        ArrayList<LogRootUpdateEventResponse> responses = new ArrayList<LogRootUpdateEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogRootUpdateEventResponse typedResponse = new LogRootUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sequenceNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.batchId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.vaultRoot = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.orderRoot = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogRootUpdateEventResponse> logRootUpdateEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogRootUpdateEventResponse>() {
            @Override
            public LogRootUpdateEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGROOTUPDATE_EVENT, log);
                LogRootUpdateEventResponse typedResponse = new LogRootUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.sequenceNumber = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.batchId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.vaultRoot = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.orderRoot = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogRootUpdateEventResponse> logRootUpdateEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGROOTUPDATE_EVENT));
        return logRootUpdateEventFlowable(filter);
    }

    public List<LogStateTransitionFactEventResponse> getLogStateTransitionFactEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSTATETRANSITIONFACT_EVENT, transactionReceipt);
        ArrayList<LogStateTransitionFactEventResponse> responses = new ArrayList<LogStateTransitionFactEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogStateTransitionFactEventResponse typedResponse = new LogStateTransitionFactEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.stateTransitionFact = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogStateTransitionFactEventResponse> logStateTransitionFactEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogStateTransitionFactEventResponse>() {
            @Override
            public LogStateTransitionFactEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSTATETRANSITIONFACT_EVENT, log);
                LogStateTransitionFactEventResponse typedResponse = new LogStateTransitionFactEventResponse();
                typedResponse.log = log;
                typedResponse.stateTransitionFact = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogStateTransitionFactEventResponse> logStateTransitionFactEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSTATETRANSITIONFACT_EVENT));
        return logStateTransitionFactEventFlowable(filter);
    }

    public List<LogVaultBalanceChangeAppliedEventResponse> getLogVaultBalanceChangeAppliedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGVAULTBALANCECHANGEAPPLIED_EVENT, transactionReceipt);
        ArrayList<LogVaultBalanceChangeAppliedEventResponse> responses = new ArrayList<LogVaultBalanceChangeAppliedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogVaultBalanceChangeAppliedEventResponse typedResponse = new LogVaultBalanceChangeAppliedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ethKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.quantizedAmountChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogVaultBalanceChangeAppliedEventResponse> logVaultBalanceChangeAppliedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogVaultBalanceChangeAppliedEventResponse>() {
            @Override
            public LogVaultBalanceChangeAppliedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGVAULTBALANCECHANGEAPPLIED_EVENT, log);
                LogVaultBalanceChangeAppliedEventResponse typedResponse = new LogVaultBalanceChangeAppliedEventResponse();
                typedResponse.log = log;
                typedResponse.ethKey = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetId = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.vaultId = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.quantizedAmountChange = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogVaultBalanceChangeAppliedEventResponse> logVaultBalanceChangeAppliedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGVAULTBALANCECHANGEAPPLIED_EVENT));
        return logVaultBalanceChangeAppliedEventFlowable(filter);
    }

    public List<LogWithdrawalAllowedEventResponse> getLogWithdrawalAllowedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWITHDRAWALALLOWED_EVENT, transactionReceipt);
        ArrayList<LogWithdrawalAllowedEventResponse> responses = new ArrayList<LogWithdrawalAllowedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogWithdrawalAllowedEventResponse typedResponse = new LogWithdrawalAllowedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWithdrawalAllowedEventResponse> logWithdrawalAllowedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogWithdrawalAllowedEventResponse>() {
            @Override
            public LogWithdrawalAllowedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWITHDRAWALALLOWED_EVENT, log);
                LogWithdrawalAllowedEventResponse typedResponse = new LogWithdrawalAllowedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWithdrawalAllowedEventResponse> logWithdrawalAllowedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWITHDRAWALALLOWED_EVENT));
        return logWithdrawalAllowedEventFlowable(filter);
    }

    public List<LogWithdrawalPerformedEventResponse> getLogWithdrawalPerformedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGWITHDRAWALPERFORMED_EVENT, transactionReceipt);
        ArrayList<LogWithdrawalPerformedEventResponse> responses = new ArrayList<LogWithdrawalPerformedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogWithdrawalPerformedEventResponse typedResponse = new LogWithdrawalPerformedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogWithdrawalPerformedEventResponse> logWithdrawalPerformedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, LogWithdrawalPerformedEventResponse>() {
            @Override
            public LogWithdrawalPerformedEventResponse apply(Log log) {
                EventValuesWithLog eventValues = extractEventParametersWithLog(LOGWITHDRAWALPERFORMED_EVENT, log);
                LogWithdrawalPerformedEventResponse typedResponse = new LogWithdrawalPerformedEventResponse();
                typedResponse.log = log;
                typedResponse.ownerKey = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.assetType = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.nonQuantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.quantizedAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogWithdrawalPerformedEventResponse> logWithdrawalPerformedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGWITHDRAWALPERFORMED_EVENT));
        return logWithdrawalPerformedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> announceAvailabilityVerifierRemovalIntent(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ANNOUNCEAVAILABILITYVERIFIERREMOVALINTENT, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> announceVerifierRemovalIntent(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ANNOUNCEVERIFIERREMOVALINTENT, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger starkKey, BigInteger assetType, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_deposit, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger starkKey, BigInteger assetType, BigInteger vaultId, BigInteger quantizedAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_deposit, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId),
                new Uint256(quantizedAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositCancel(BigInteger starkKey, BigInteger assetId, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITCANCEL, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetId),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositERC20(BigInteger starkKey, BigInteger assetType, BigInteger vaultId, BigInteger quantizedAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITERC20, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId),
                new Uint256(quantizedAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositEth(BigInteger starkKey, BigInteger assetType, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITETH, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositNft(BigInteger starkKey, BigInteger assetType, BigInteger vaultId, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITNFT, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositNftReclaim(BigInteger starkKey, BigInteger assetType, BigInteger vaultId, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITNFTRECLAIM, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetType),
                new Uint256(vaultId),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> depositReclaim(BigInteger starkKey, BigInteger assetId, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSITRECLAIM, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetId),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> escape(BigInteger starkKey, BigInteger vaultId, BigInteger assetId, BigInteger quantizedAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ESCAPE, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(vaultId),
                new Uint256(assetId),
                new Uint256(quantizedAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> freezeRequest(BigInteger starkKey, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FREEZEREQUEST, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> fullWithdrawalRequest(BigInteger starkKey, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_FULLWITHDRAWALREQUEST, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<byte[]> getAssetInfo(BigInteger assetType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETASSETINFO, 
                Arrays.<Type>asList(new Uint256(assetType)),
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<BigInteger> getCancellationRequest(BigInteger starkKey, BigInteger assetId, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCANCELLATIONREQUEST, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetId),
                new Uint256(vaultId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getDepositBalance(BigInteger starkKey, BigInteger assetId, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETDEPOSITBALANCE, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetId),
                new Uint256(vaultId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> getEthKey(BigInteger starkKey) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETETHKEY, 
                Arrays.<Type>asList(new Uint256(starkKey)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> getFullWithdrawalRequest(BigInteger starkKey, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETFULLWITHDRAWALREQUEST, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(vaultId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getLastBatchId() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETLASTBATCHID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getOrderRoot() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETORDERROOT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getOrderTreeHeight() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETORDERTREEHEIGHT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getQuantizedDepositBalance(BigInteger starkKey, BigInteger assetId, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETQUANTIZEDDEPOSITBALANCE, 
                Arrays.<Type>asList(new Uint256(starkKey),
                new Uint256(assetId),
                new Uint256(vaultId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getQuantum(BigInteger presumedAssetType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETQUANTUM, 
                Arrays.<Type>asList(new Uint256(presumedAssetType)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> getRegisteredAvailabilityVerifiers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETREGISTEREDAVAILABILITYVERIFIERS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> getRegisteredVerifiers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETREGISTEREDVERIFIERS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> getSequenceNumber() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETSEQUENCENUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getVaultRoot() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVAULTROOT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getVaultTreeHeight() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVAULTTREEHEIGHT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> getWithdrawalBalance(BigInteger ownerKey, BigInteger assetId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETWITHDRAWALBALANCE, 
                Arrays.<Type>asList(new Uint256(ownerKey),
                new Uint256(assetId)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> isAvailabilityVerifier(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISAVAILABILITYVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isFrozen() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISFROZEN, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isOperator(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISOPERATOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isTokenAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISTOKENADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isUserAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISUSERADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> isVerifier(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ISVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mainAcceptGovernance() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAINACCEPTGOVERNANCE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mainCancelNomination() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAINCANCELNOMINATION, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mainIsGovernor(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAINISGOVERNOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mainNominateNewGovernor(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAINNOMINATENEWGOVERNOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mainRemoveGovernor(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_MAINREMOVEGOVERNOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> onERC721Received(String param0, String param1, BigInteger param2, byte[] param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ONERC721RECEIVED, 
                Arrays.<Type>asList(new Address(160, param0),
                new Address(160, param1),
                new Uint256(param2),
                new DynamicBytes(param3)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndDepositERC20(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, BigInteger vaultId, BigInteger quantizedAmount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERANDDEPOSITERC20, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new Uint256(starkKey),
                new DynamicBytes(signature),
                new Uint256(assetType),
                new Uint256(vaultId),
                new Uint256(quantizedAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndDepositEth(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, BigInteger vaultId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERANDDEPOSITETH, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new Uint256(starkKey),
                new DynamicBytes(signature),
                new Uint256(assetType),
                new Uint256(vaultId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAvailabilityVerifier(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERAVAILABILITYVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0),
                new org.web3j.abi.datatypes.Utf8String(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerOperator(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTEROPERATOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerToken(BigInteger param0, byte[] param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERTOKEN, 
                Arrays.<Type>asList(new Uint256(param0),
                new DynamicBytes(param1)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerTokenAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERTOKENADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerUser(String param0, BigInteger param1, byte[] param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERUSER, 
                Arrays.<Type>asList(new Address(160, param0),
                new Uint256(param1),
                new DynamicBytes(param2)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerUserAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERUSERADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerVerifier(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0),
                new org.web3j.abi.datatypes.Utf8String(param1)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAvailabilityVerifier(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEAVAILABILITYVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> removeVerifier(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEVERIFIER, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unFreeze() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNFREEZE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unregisterOperator(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNREGISTEROPERATOR, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unregisterTokenAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNREGISTERTOKENADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> unregisterUserAdmin(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UNREGISTERUSERADMIN, 
                Arrays.<Type>asList(new Address(160, param0)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateState(List<BigInteger> publicInput, List<BigInteger> applicationData) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                        Uint256.class,
                        org.web3j.abi.Utils.typeMap(publicInput, Uint256.class)),
                new org.web3j.abi.datatypes.DynamicArray<Uint256>(
                        Uint256.class,
                        org.web3j.abi.Utils.typeMap(applicationData, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger ownerKey, BigInteger assetType) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new Uint256(ownerKey),
                new Uint256(assetType)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawAndMint(BigInteger ownerKey, BigInteger assetType, byte[] mintingBlob) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWANDMINT, 
                Arrays.<Type>asList(new Uint256(ownerKey),
                new Uint256(assetType),
                new DynamicBytes(mintingBlob)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawNft(BigInteger ownerKey, BigInteger assetType, BigInteger tokenId) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWNFT, 
                Arrays.<Type>asList(new Uint256(ownerKey),
                new Uint256(assetType),
                new Uint256(tokenId)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawNftTo(BigInteger param0, BigInteger param1, BigInteger param2, String param3) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWNFTTO, 
                Arrays.<Type>asList(new Uint256(param0),
                new Uint256(param1),
                new Uint256(param2),
                new Address(160, param3)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdrawTo(BigInteger param0, BigInteger param1, String param2) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAWTO, 
                Arrays.<Type>asList(new Uint256(param0),
                new Uint256(param1),
                new Address(160, param2)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Core_sol_Core load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Core_sol_Core(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Core_sol_Core load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Core_sol_Core(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Core_sol_Core load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Core_sol_Core(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Core_sol_Core load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Core_sol_Core(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Core_sol_Core> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Core_sol_Core.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Core_sol_Core> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Core_sol_Core.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Core_sol_Core> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(Core_sol_Core.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<Core_sol_Core> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Core_sol_Core.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogDepositEventResponse extends BaseEventResponse {
        public String depositorEthKey;

        public BigInteger starkKey;

        public BigInteger vaultId;

        public BigInteger assetType;

        public BigInteger nonQuantizedAmount;

        public BigInteger quantizedAmount;
    }

    public static class LogDepositCancelEventResponse extends BaseEventResponse {
        public BigInteger starkKey;

        public BigInteger vaultId;

        public BigInteger assetId;
    }

    public static class LogDepositCancelReclaimedEventResponse extends BaseEventResponse {
        public BigInteger starkKey;

        public BigInteger vaultId;

        public BigInteger assetType;

        public BigInteger nonQuantizedAmount;

        public BigInteger quantizedAmount;
    }

    public static class LogDepositNftCancelReclaimedEventResponse extends BaseEventResponse {
        public BigInteger starkKey;

        public BigInteger vaultId;

        public BigInteger assetType;

        public BigInteger tokenId;

        public BigInteger assetId;
    }

    public static class LogFullWithdrawalRequestEventResponse extends BaseEventResponse {
        public BigInteger starkKey;

        public BigInteger vaultId;
    }

    public static class LogMintWithdrawalPerformedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetType;

        public BigInteger nonQuantizedAmount;

        public BigInteger quantizedAmount;

        public BigInteger assetId;
    }

    public static class LogMintableWithdrawalAllowedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetId;

        public BigInteger quantizedAmount;
    }

    public static class LogNftDepositEventResponse extends BaseEventResponse {
        public String depositorEthKey;

        public BigInteger starkKey;

        public BigInteger vaultId;

        public BigInteger assetType;

        public BigInteger tokenId;

        public BigInteger assetId;
    }

    public static class LogNftWithdrawalAllowedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetId;
    }

    public static class LogNftWithdrawalPerformedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetType;

        public BigInteger tokenId;

        public BigInteger assetId;

        public String recipient;
    }

    public static class LogRootUpdateEventResponse extends BaseEventResponse {
        public BigInteger sequenceNumber;

        public BigInteger batchId;

        public BigInteger vaultRoot;

        public BigInteger orderRoot;
    }

    public static class LogStateTransitionFactEventResponse extends BaseEventResponse {
        public byte[] stateTransitionFact;
    }

    public static class LogVaultBalanceChangeAppliedEventResponse extends BaseEventResponse {
        public String ethKey;

        public BigInteger assetId;

        public BigInteger vaultId;

        public BigInteger quantizedAmountChange;
    }

    public static class LogWithdrawalAllowedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetType;

        public BigInteger nonQuantizedAmount;

        public BigInteger quantizedAmount;
    }

    public static class LogWithdrawalPerformedEventResponse extends BaseEventResponse {
        public BigInteger ownerKey;

        public BigInteger assetType;

        public BigInteger nonQuantizedAmount;

        public BigInteger quantizedAmount;

        public String recipient;
    }
}
