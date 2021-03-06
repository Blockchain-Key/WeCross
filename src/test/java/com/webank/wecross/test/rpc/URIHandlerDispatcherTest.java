package com.webank.wecross.test.rpc;

import com.webank.wecross.common.NetworkQueryStatus;
import com.webank.wecross.host.WeCrossHost;
import com.webank.wecross.network.rpc.URIHandlerDispatcher;
import com.webank.wecross.network.rpc.handler.URIHandler;
import com.webank.wecross.network.rpc.netty.URIMethod;
import com.webank.wecross.restserver.RestResponse;
import com.webank.wecross.restserver.Versions;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class URIHandlerDispatcherTest {
    @Test
    public void URIHandlerDispatcherTest() throws Exception {
        WeCrossHost host = Mockito.mock(WeCrossHost.class);
        Mockito.when(host.getAccountManager()).thenReturn(null);
        Mockito.when(host.getXaTransactionManager()).thenReturn(null);

        URIHandlerDispatcher uriHandlerDispatcher = new URIHandlerDispatcher();
        uriHandlerDispatcher.initializeRequestMapper(host);
        Assert.assertTrue(uriHandlerDispatcher.getRequestURIMapper().size() == 16);

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("GET", "/test"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("POST", "/test"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("GET", "/state"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("POST", "/state"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("GET", "/supportedStubs"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("POST", "/supportedStubs"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("GET", "/listAccounts"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("POST", "/listAccounts"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("GET", "/listResources"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                new URIMethod("POST", "/listResources"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("GET", "/a/b/c/d"))));
        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("POST", "/a/b/c/d"))));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(
                                URIHandlerDispatcher.RESOURCE_URIMETHOD)));

        Assert.assertTrue(
                Objects.nonNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("POST", "/a/b/c/d"))));

        Assert.assertTrue(
                Objects.isNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("GET", "/other"))));
        Assert.assertTrue(
                Objects.isNull(
                        uriHandlerDispatcher.matchURIHandler(new URIMethod("POST", "/other"))));
    }

    @Test
    public void TestURIHandlerTest() throws Exception {
        WeCrossHost host = Mockito.mock(WeCrossHost.class);
        Mockito.when(host.getAccountManager()).thenReturn(null);
        Mockito.when(host.getXaTransactionManager()).thenReturn(null);

        URIHandlerDispatcher uriHandlerDispatcher = new URIHandlerDispatcher();
        uriHandlerDispatcher.initializeRequestMapper(host);

        URIHandler uriHandler = uriHandlerDispatcher.matchURIHandler(new URIMethod("GET", "/test"));

        final RestResponse[] restResp = {null};
        uriHandler.handle(
                null,
                null,
                null,
                new URIHandler.Callback() {
                    @Override
                    public void onResponse(RestResponse restResponse) {
                        restResp[0] = restResponse;
                    }
                });

        Assert.assertEquals(restResp[0].getData(), "OK!");
        Assert.assertEquals(restResp[0].getVersion(), Versions.currentVersion);
        Assert.assertEquals(restResp[0].getErrorCode().intValue(), NetworkQueryStatus.SUCCESS);
        Assert.assertEquals(
                restResp[0].getMessage(),
                NetworkQueryStatus.getStatusMessage(restResp[0].getErrorCode().intValue()));
    }
}
