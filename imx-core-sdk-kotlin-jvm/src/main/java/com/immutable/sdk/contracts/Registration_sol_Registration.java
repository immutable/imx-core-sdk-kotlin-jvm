package com.immutable.sdk.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class Registration_sol_Registration extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051610b25380380610b2583398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b610a92806100936000396000f3fe608060405234801561001057600080fd5b50600436106100885760003560e01c80634280d50a1161005b5780634280d50a146100f85780634627d5981461010b578063579a69881461011e578063ea864adf1461014157600080fd5b80630a9c3beb1461008d5780630f08025f146100a25780631259cc6c146100d2578063352eb84c146100e5575b600080fd5b6100a061009b3660046106c2565b610154565b005b6000546100b5906001600160a01b031681565b6040516001600160a01b0390911681526020015b60405180910390f35b6100a06100e0366004610758565b61022d565b6100a06100f33660046107df565b6102dc565b6100a061010636600461084d565b6103b8565b6100a06101193660046108c4565b610466565b61013161012c366004610940565b61050e565b60405190151581526020016100c9565b6100a061014f366004610959565b61058d565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d49061018a908a908a908a908a906004016109e6565b600060405180830381600087803b1580156101a457600080fd5b505af11580156101b8573d6000803e3d6000fd5b505060005460405163d91443b760e01b81526001600160a01b03909116925063d91443b791506101f2908990879087908790600401610a18565b600060405180830381600087803b15801561020c57600080fd5b505af1158015610220573d6000803e3d6000fd5b5050505050505050505050565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d490610263908a908a908a908a906004016109e6565b600060405180830381600087803b15801561027d57600080fd5b505af1158015610291573d6000803e3d6000fd5b5050600054604051630ebef0fd60e41b8152600481018a905260248101879052604481018690526001600160a01b038581166064830152909116925063ebef0fd091506084016101f2565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d4906103129089908990899089906004016109e6565b600060405180830381600087803b15801561032c57600080fd5b505af1158015610340573d6000803e3d6000fd5b505060005460405162cda0bd60e11b81526004810189905260248101869052604481018590526001600160a01b03909116925063019b417a91506064015b600060405180830381600087803b15801561039857600080fd5b505af11580156103ac573d6000803e3d6000fd5b50505050505050505050565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d4906103ee908a908a908a908a906004016109e6565b600060405180830381600087803b15801561040857600080fd5b505af115801561041c573d6000803e3d6000fd5b505060005460405163570e6ef360e11b8152600481018a90526024810187905260448101869052606481018590526001600160a01b03909116925063ae1cdde691506084016101f2565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d49061049c9089908990899089906004016109e6565b600060405180830381600087803b1580156104b657600080fd5b505af11580156104ca573d6000803e3d6000fd5b50506000546040516305335c3960e21b815260048101899052602481018690526001600160a01b03858116604483015290911692506314cd70e4915060640161037e565b60008054604051631dbd1da760e01b81526004810184905282916001600160a01b031690631dbd1da790602401602060405180830381865afa158015610558573d6000803e3d6000fd5b505050506040513d601f19601f8201168201806040525081019061057c9190610a38565b6001600160a01b0316141592915050565b600054604051633749053560e21b81526001600160a01b039091169063dd2414d4906105c39088908890889088906004016109e6565b600060405180830381600087803b1580156105dd57600080fd5b505af11580156105f1573d6000803e3d6000fd5b5050600054604051630441a3e760e41b815260048101889052602481018590526001600160a01b03909116925063441a3e709150604401600060405180830381600087803b15801561064257600080fd5b505af1158015610656573d6000803e3d6000fd5b505050505050505050565b6001600160a01b038116811461067657600080fd5b50565b60008083601f84011261068b57600080fd5b50813567ffffffffffffffff8111156106a357600080fd5b6020830191508360208285010111156106bb57600080fd5b9250929050565b600080600080600080600060a0888a0312156106dd57600080fd5b87356106e881610661565b965060208801359550604088013567ffffffffffffffff8082111561070c57600080fd5b6107188b838c01610679565b909750955060608a0135945060808a013591508082111561073857600080fd5b506107458a828b01610679565b989b979a50959850939692959293505050565b600080600080600080600060c0888a03121561077357600080fd5b873561077e81610661565b965060208801359550604088013567ffffffffffffffff8111156107a157600080fd5b6107ad8a828b01610679565b909650945050606088013592506080880135915060a08801356107cf81610661565b8091505092959891949750929550565b60008060008060008060a087890312156107f857600080fd5b863561080381610661565b955060208701359450604087013567ffffffffffffffff81111561082657600080fd5b61083289828a01610679565b979a9699509760608101359660809091013595509350505050565b600080600080600080600060c0888a03121561086857600080fd5b873561087381610661565b965060208801359550604088013567ffffffffffffffff81111561089657600080fd5b6108a28a828b01610679565b989b979a50986060810135976080820135975060a09091013595509350505050565b60008060008060008060a087890312156108dd57600080fd5b86356108e881610661565b955060208701359450604087013567ffffffffffffffff81111561090b57600080fd5b61091789828a01610679565b90955093505060608701359150608087013561093281610661565b809150509295509295509295565b60006020828403121561095257600080fd5b5035919050565b60008060008060006080868803121561097157600080fd5b853561097c81610661565b945060208601359350604086013567ffffffffffffffff81111561099f57600080fd5b6109ab88828901610679565b96999598509660600135949350505050565b81835281816020850137506000828201602090810191909152601f909101601f19169091010190565b60018060a01b0385168152836020820152606060408201526000610a0e6060830184866109bd565b9695505050505050565b848152836020820152606060408201526000610a0e6060830184866109bd565b600060208284031215610a4a57600080fd5b8151610a5581610661565b939250505056fea2646970667358221220c8844e461f736bc38b25667b8d25e0b792fd07db446b0054e927a3875f1171c064736f6c63430008110033";

    public static final String FUNC_IMX = "imx";

    public static final String FUNC_ISREGISTERED = "isRegistered";

    public static final String FUNC_REGISTERANDDEPOSITNFT = "registerAndDepositNft";

    public static final String FUNC_REGISTERANDWITHDRAW = "registerAndWithdraw";

    public static final String FUNC_REGISTERANDWITHDRAWNFT = "registerAndWithdrawNft";

    public static final String FUNC_REGISTERANDWITHDRAWNFTTO = "registerAndWithdrawNftTo";

    public static final String FUNC_REGISTERANDWITHDRAWTO = "registerAndWithdrawTo";

    public static final String FUNC_REGSITERANDWITHDRAWANDMINT = "regsiterAndWithdrawAndMint";

    protected Registration_sol_Registration(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected Registration_sol_Registration(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> imx() {
        final Function function = new Function(FUNC_IMX, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isRegistered(BigInteger starkKey) {
        final Function function = new Function(FUNC_ISREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(starkKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndDepositNft(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, BigInteger vaultId, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_REGISTERANDDEPOSITNFT, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType), 
                new org.web3j.abi.datatypes.generated.Uint256(vaultId), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndWithdraw(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType) {
        final Function function = new Function(
                FUNC_REGISTERANDWITHDRAW, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndWithdrawNft(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, BigInteger tokenId) {
        final Function function = new Function(
                FUNC_REGISTERANDWITHDRAWNFT, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndWithdrawNftTo(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, BigInteger tokenId, String recipient) {
        final Function function = new Function(
                FUNC_REGISTERANDWITHDRAWNFTTO, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType), 
                new org.web3j.abi.datatypes.generated.Uint256(tokenId), 
                new Address(160, recipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAndWithdrawTo(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, String recipient) {
        final Function function = new Function(
                FUNC_REGISTERANDWITHDRAWTO, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType), 
                new Address(160, recipient)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> regsiterAndWithdrawAndMint(String ethKey, BigInteger starkKey, byte[] signature, BigInteger assetType, byte[] mintingBlob) {
        final Function function = new Function(
                FUNC_REGSITERANDWITHDRAWANDMINT, 
                Arrays.<Type>asList(new Address(160, ethKey),
                new org.web3j.abi.datatypes.generated.Uint256(starkKey), 
                new org.web3j.abi.datatypes.DynamicBytes(signature), 
                new org.web3j.abi.datatypes.generated.Uint256(assetType), 
                new org.web3j.abi.datatypes.DynamicBytes(mintingBlob)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static Registration_sol_Registration load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Registration_sol_Registration(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Registration_sol_Registration load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Registration_sol_Registration(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Registration_sol_Registration> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _imx) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _imx)));
        return deployRemoteCall(Registration_sol_Registration.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Registration_sol_Registration> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _imx) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new Address(160, _imx)));
        return deployRemoteCall(Registration_sol_Registration.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }
}
