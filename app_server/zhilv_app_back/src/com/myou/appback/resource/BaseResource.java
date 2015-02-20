package com.myou.appback.resource;

import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Description: 基础resource<br>
 * @version 1.0
 */
public class BaseResource extends ServerResource{

	/** 日志对象 */
	protected Logger logger = LoggerFactory.getLogger(BaseResource.class);
}
