package com.lawu.eshop.pay.srv.sdk.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088521405698435";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;
	
	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMx1t5K2k2HdpPSusRnDmJfMKVEIRFoRPevkSw7LrrMcfUTqqhijNWNnCwjRX3BQ25n2Nll0I5y8ZZCyHURY46CJtn3T6JvmcrMEvQS1lFIM3nWuOQiUxI0xas/2AATVN/hA9PS02Heyzid0qGsunkWRqlyezuaqnLrLLXe9iHT3AgMBAAECgYAIfFpCHJHBXhGaliWDjDqpEjAkzNMJae3H/JGg65M0m9oXIiYtH5j3py8uDoKHhtOWcEGRNODFgIw2lS3hyE6qKAOUwGnsucyUZ+chuP/q6WwlQARuGIKCvmLBCdh9CMmW6ifHGXMLtNyRDr8+0NaT0pHGtizClsCvcAf2a/JzAQJBAPVNh2gSuS094drh5KWyGKuIU/S3KdNqifaE2dagCkvKLGYxnUiP7IHT6PYEW9CFb5zBdtyDG1vKEtpWjZhc6mMCQQDVYDtQ2qwpRYz6FnRhDAVNGIP31+IOGDNVfJqVcWbXHIG2vrSsFNvDxnytS3E990SH9ZOxZ5TD+Tnxph57rSVdAkEAt7wacBmWGgz/3rLUUEfVsqWqPOH0siZasAtSHzBAhgQovWLsLC2hOJ1BIQSiwM0xjHhcT07kbxwsgt3RsQ3ngQJBAKqsN6Xb05uLk1EzBtHdcJwTQPPMzXdiqiT17xwcQhtMJQFMl22RnZuCN5XR43miQEHUD9f2N0tuHYgJzRY2D80CQHrYNG+P7rd4AjNe3fW0IO9VTkGckGueqTcE1NcY5w1n2IXCUyRlh3RMDJ+HosawCYtEbFDHo7ozT0bir5n7xF4=";
		
	/**商家PC----------开始**/
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	/**商家PC----------结束**/
	
	//app_id
	public static String app_id_member = "2017010804931124";//用户端
	public static String app_id_business = "2017010804931124";//商家端
	
	/**e店（用户端）----------开始**/
	public static String alipay_edian_member_public_key= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	/**e店（用户端）----------结束**/
	/**e店（商家端）----------开始**/
	public static String alipay_edian_business_public_key= "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	/**e店（商家端）----------结束**/
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "";//"/usr/local/alipay/log/";//"E:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

