package org.apache.playframework.service.filter;

import org.apache.playframework.cache.CacheSwitcher;
import org.apache.playframework.log.Logger;
import org.apache.playframework.log.LoggerFactory;
import org.apache.playframework.util.Request;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;

@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class DubboServiceFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(DubboServiceFilter.class);

	private String reqidKey = "X-Request-ID";
	private String cachedKey = "X-Cached";

	public DubboServiceFilter() {
		logger.info("DubboServiceFilter created");
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		RpcContext context = RpcContext.getContext();
		String remoteIp = context.getRemoteAddressString();
		boolean provider = context.isProviderSide();
		URL url = invoker.getUrl();
		String interfaceStr = url.getParameter("interface");
		if (provider) {
			String reqId = invocation.getAttachment(reqidKey);
			Request.setId(reqId);
			boolean cached = Boolean.valueOf(invocation.getAttachment(cachedKey, "true"));
			CacheSwitcher.set(cached);
		} else {
			String reqId = Request.getId();
			invocation.getAttachments().put(reqidKey, reqId);
			Boolean cached = CacheSwitcher.get();
			invocation.getAttachments().put(cachedKey, cached == null ? null : String.valueOf(cached));
		}
		long start = System.currentTimeMillis();
		Result result = null;
		try {
			result = invoker.invoke(invocation);
		} finally {
			long timeused = (System.currentTimeMillis() - start);
			if (!"com.alibaba.dubbo.monitor.MonitorService".equalsIgnoreCase(interfaceStr)) {
				logger.info("{} {} interface={}.{} timeused={}", provider ? "from client" : "to service", remoteIp,
						interfaceStr, invocation.getMethodName(), timeused);
			}
		}
		return result;
	}
}
