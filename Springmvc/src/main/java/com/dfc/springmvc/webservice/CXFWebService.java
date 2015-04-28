package com.dfc.springmvc.webservice;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@WebService
@Path("userInfo")
public interface CXFWebService {

	@GET
	@Path(value = "/getUserInfo")
	@Consumes({ "*/*" })
	public String getArticleInfo(@FormParam("userId") String userId);

}
