package com.wonders.core.rest.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https客户端请求测试基类
 * @author silent
 */
public class HttpTest {
	private static final Logger logger = LoggerFactory
			.getLogger(HttpTest.class);
	private RequestConfig defaultRequestConfig;
	private CloseableHttpClient httpclient;
	private HttpClientContext context;
	private HttpGet httpget = null;
	private HttpPost httppost = null;

	public HttpTest() {
		super();

		// Use custom message parser / writer to customize the way HTTP
		// messages are parsed from and written out to the data stream.
		HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

			@Override
			public HttpMessageParser<HttpResponse> create(
					SessionInputBuffer buffer, MessageConstraints constraints) {
				LineParser lineParser = new BasicLineParser() {

					@Override
					public Header parseHeader(final CharArrayBuffer buffer) {
						try {
							return super.parseHeader(buffer);
						} catch (ParseException ex) {
							return new BasicHeader(buffer.toString(), null);
						}
					}

				};
				return new DefaultHttpResponseParser(buffer, lineParser,
						DefaultHttpResponseFactory.INSTANCE, constraints) {

					@Override
					protected boolean reject(final CharArrayBuffer line,
							int count) {
						// try to ignore all garbage preceding a status line
						// infinitely
						return false;
					}

				};
			}

		};
		HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();

		// Use a custom connection factory to customize the process of
		// initialization of outgoing HTTP connections. Beside standard
		// connection
		// configuration parameters HTTP connection factory can define message
		// parser / writer routines to be employed by individual connections.
		HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(
				requestWriterFactory, responseParserFactory);

		// Client HTTP connection objects when fully initialized can be bound to
		// an arbitrary network socket. The process of network socket
		// initialization,
		// its connection to a remote address and binding to a local one is
		// controlled
		// by a connection socket factory.

		// SSL context for secure connections can be created either based on
		// system or application specific properties.
		SSLContext sslcontext = null;
		try {
			String keystorePath = ClassLoader.getSystemResource("serverkeystore.p12").getPath();
			logger.debug("keystorePath==" + keystorePath);
			sslcontext = SSLContexts.custom().loadTrustMaterial(new File(keystorePath), "hlthspvJurassic!@#$qwer".toCharArray()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", new SSLConnectionSocketFactory(sslcontext))
				.build();

		// Use custom DNS resolver to override the system DNS resolution.
		DnsResolver dnsResolver = new SystemDefaultDnsResolver() {

			@Override
			public InetAddress[] resolve(final String host)
					throws UnknownHostException {
				if (host.equalsIgnoreCase("myhost")) {
					return new InetAddress[] { InetAddress
							.getByAddress(new byte[] { 127, 0, 0, 1 }) };
				} else {
					return super.resolve(host);
				}
			}

		};

		// Create a connection manager with custom configuration.
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry, connFactory, dnsResolver);

		// Create socket configuration
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true)
				.build();
		// Configure the connection manager to use socket configuration either
		// by default or for a specific host.
		connManager.setDefaultSocketConfig(socketConfig);
		connManager.setSocketConfig(new HttpHost("localhost", 8443), socketConfig);
		// Validate connections after 1 sec of inactivity
		connManager.setValidateAfterInactivity(1000);

		// Create message constraints
		MessageConstraints messageConstraints = MessageConstraints.custom()
				.setMaxHeaderCount(200).setMaxLineLength(2000).build();
		// Create connection configuration
		ConnectionConfig connectionConfig = ConnectionConfig.custom()
				.setMalformedInputAction(CodingErrorAction.IGNORE)
				.setUnmappableInputAction(CodingErrorAction.IGNORE)
				.setCharset(Consts.UTF_8)
				.setMessageConstraints(messageConstraints).build();
		// Configure the connection manager to use connection configuration
		// either
		// by default or for a specific host.
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setConnectionConfig(new HttpHost("localhost", 8443),
				ConnectionConfig.DEFAULT);

		// Configure total max or per route limits for persistent connections
		// that can be kept in the pool or leased by the connection manager.
		connManager.setMaxTotal(500);
		connManager.setDefaultMaxPerRoute(10);
		connManager.setMaxPerRoute(new HttpRoute(new HttpHost("localhost", 8443)),
				100);

		// Use custom cookie store if necessary.
		CookieStore cookieStore = new BasicCookieStore();
		// Use custom credentials provider if necessary.
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// Create global request configuration
		defaultRequestConfig = RequestConfig
				.custom()
				.setCookieSpec(CookieSpecs.DEFAULT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(
						Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.build();

		// Create an HttpClient with the given custom dependencies and
		// configuration.
		httpclient = HttpClients.custom().setConnectionManager(connManager)
				.setDefaultCookieStore(cookieStore)
				.setDefaultCredentialsProvider(credentialsProvider)
				.setDefaultRequestConfig(defaultRequestConfig).build();

		// Execution context can be customized locally.
		context = HttpClientContext.create();
		// Contextual attributes set the local context level will take
		// precedence over those set at the client level.
		context.setCookieStore(cookieStore);
		context.setCredentialsProvider(credentialsProvider);
	}

	public HttpEntity get(String url) {
		try {
			httpget = new HttpGet(url);
			// Request configuration can be overridden at the request level.
			// They will take precedence over the one set at the client level.
			RequestConfig requestConfig = RequestConfig
					.copy(defaultRequestConfig).setSocketTimeout(5000)
					.setConnectTimeout(5000).setConnectionRequestTimeout(5000)
					.build();
			httpget.setConfig(requestConfig);

			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget,
					context);
			if (response.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			HttpEntity httpEntity = response.getEntity();
			return httpEntity;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HttpEntity post(String url, Map<String, String> params,
			Map<String, File> fileParam) {
		try {
			httppost = new HttpPost(url);
			// Request configuration can be overridden at the request level.
			// They will take precedence over the one set at the client level.
			RequestConfig requestConfig = RequestConfig
					.copy(defaultRequestConfig).setSocketTimeout(10000)
					.setConnectTimeout(10000).setConnectionRequestTimeout(10000)
					.build();

			// 参数传入1，UrlEncodedFormEntity 相当于?aa=xx&bb=dd传值，只能传string，类似于get
			// List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			// for (Iterator<String> iterator = params.keySet().iterator();
			// iterator.hasNext();) {
			// String key = (String) iterator.next();
			// nvps.add(new BasicNameValuePair(key, params.get(key)));
			// }
			// HttpEntity entity = new UrlEncodedFormEntity(nvps);

			// 参数传入2，UrlEncodedFormEntity 相当于form表单传值。
			MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder
					.create();
			// 文件类型参数
			if (fileParam != null && !fileParam.isEmpty()) {
				for (Iterator<String> iterator = fileParam.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					File file = fileParam.get(key);
					mEntityBuilder.addBinaryBody(key, file);
				}
			}
			// 字符串参数
			if (params != null && !params.isEmpty()) {
				for (Iterator<String> iterator = params.keySet().iterator(); iterator
						.hasNext();) {
					String key = (String) iterator.next();
					mEntityBuilder.addTextBody(key, params.get(key), ContentType.create("text/plain", Charset.forName("UTF-8")));
				}
			}
			HttpEntity entity = mEntityBuilder.build();

			// request
			httppost.setEntity(entity);
			httppost.setConfig(requestConfig);

			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost,
					context);
			if (response.getStatusLine().getStatusCode() != 200) {
				System.out.println("response status code" + response.getStatusLine().getStatusCode());
				return null;
			}
			HttpEntity httpEntity = response.getEntity();
			return httpEntity;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getContent(HttpEntity httpEntity) {
		InputStream inputstream = null;
		StringBuffer sb = new StringBuffer();
		try {
			inputstream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputstream, "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				System.out.println(line);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public void write(HttpEntity httpEntity, OutputStream out) {
		try {
			httpEntity.writeTo(out);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	public void close() {
		// release connection
		if (httpget != null) {
			httpget.abort();
		}
		if (httppost != null) {
			httppost.abort();
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		HttpTest hf = new HttpTest();
		HttpEntity httpEntity;
//		httpEntity = hf
//				.get("http://10.40.0.197/jswjJson/appinfo/download?id=8f1d052182b14283b5158388cde6e799");
//		OutputStream out = new FileOutputStream(new File("d:/aa.apk"));
//		hf.write(httpEntity, out);
//		hf.close();

		System.out
				.println("=====================华丽的分割线============================");
		httpEntity = hf
				.get("http://10.40.0.197/jswjJson/appinfo/view?id=8f1d052182b14283b5158388cde6e799");
		String content = hf.getContent(httpEntity);
		hf.close();

		System.out.println(content);
//		System.out
//				.println("=====================华丽的分割线============================");
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("id", "8f1d052182b14283b5158388cde6e799");
//		Map<String, File> fileParam = new HashMap<String, File>();
//
//		httpEntity = hf.post("http://10.40.0.197/jswjJson/appinfo/view",
//				params, fileParam);
//		String content2 = hf.getContent(httpEntity);
//		hf.close();
//		System.out.println(content2);
	}
}
