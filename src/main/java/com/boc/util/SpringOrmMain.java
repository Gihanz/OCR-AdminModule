

package com.boc.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boc.service.ProductService;

/*
Created By SaiMadan on Nov 10, 2016
*/
public class SpringOrmMain 
{

public static void main(String[] args) {
		
		//Create Spring application context
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
		
		//Get service from context. (service's dependency (ProductDAO) is autowired in ProductService)
		ProductService productService = ctx.getBean(ProductService.class);
		
		//Do some data operation
		//productService.getUserList("2");
}
		
}
