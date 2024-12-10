

package com.boc.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.boc.form.logonForm;
import com.boc.form.searchAreaForm;
import com.boc.form.searchBranchForm;
import com.boc.form.searchForm;
import com.boc.form.searchProvinceForm;
import com.boc.form.updateUserForm;
import com.boc.ldap.UpmLdapEvents;
import com.boc.model.RoleBase;
import com.boc.model.UsrBase;
import com.boc.response.AreaBaseRs;
import com.boc.response.AreaBranchRs;
import com.boc.response.BranchRs;
import com.boc.response.ProductCategoryRs;
import com.boc.response.ProvinceBaseRs;
import com.boc.response.ProvinceBranchRs;
import com.boc.response.RoleBaseRs;
import com.boc.response.UserBaseRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserProductCategoryBranchRs;
import com.boc.service.ProductService;


/*
Created By SaiMadan on Dec 6, 2016
*/
@Controller
public class AdminController {
	private static Logger log =LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private ProductService serviceImpl;
	/**
	 * Exception Page
	 */
	public static final String exceptionPage = "ExceptionPage";
	

	@RequestMapping("/search")
	public String search(Model model,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request)
	{
		try
		{
			String userName = null;
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				userName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			
			String userLoggedIn = (String)session.getAttribute("userLoggedIn");
			List<UserBaseRs> userBaseRsLst = null;
			if(userLoggedIn.equalsIgnoreCase("Manager"))
			{
				userBaseRsLst = serviceImpl.getUserIdListByRole(userName.toUpperCase());
				log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			}
			else
			{
				userBaseRsLst =  serviceImpl.getUserIdList();
				log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			}
			model.addAttribute("searchForm", new searchForm());
			model.addAttribute("userIds",userBaseRsLst);
			List<UserProductBranchRs> UserProductBranchRsLst=null;
			UserProductBranchRsLst = new ArrayList<UserProductBranchRs>();
			model.addAttribute("searchResults",UserProductBranchRsLst);
			model.addAttribute("responseMessage", responseMessage);
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "search";
	}
	
	@RequestMapping("/searchBranch")
	public String seaarchBranch(Model model,HttpServletRequest request)
	{
		try
		{
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String userName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			model.addAttribute("searchBranchForm", new searchBranchForm());
			model.addAttribute("userIds",userBaseRsLst);
			BranchRs branchRs= new BranchRs();
			model.addAttribute("edit", false);
			model.addAttribute("searchUserBranchResults",branchRs);
		
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchBranch";
	}
	
	
	@RequestMapping("/searchArea")
	public String searchArea(Model model,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		String responsePage = null;
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
				//return responsePage;
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
			//return responsePage;
		}
		try
		{
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			model.addAttribute("searchAreaForm", new searchAreaForm());
			model.addAttribute("userIds",userBaseRsLst);
			AreaBranchRs areaBranchRs= new AreaBranchRs();
			model.addAttribute("edit", false);
			model.addAttribute("searchUserAreaResults",areaBranchRs);
			responsePage = "searchArea";
		
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		
	
		
		return responsePage;
	}
	

	@RequestMapping("/searchUser")
	/*public String searchUser(Model model,@ModelAttribute("searchForm") searchForm searchForm,HttpServletRequest request)
	{
		try
		{
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			//model.addAttribute("searchForm", new searchForm());
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected is "+searchForm.getNtId());
			String ntId = searchForm.getNtId();
			model.addAttribute("userName", ntId);
			List<UserProductBranchRs> UserProductBranchRsLst =  serviceImpl.getProductCategoryBranchLst(ntId);
			log.debug("userBaseRsLst size is "+UserProductBranchRsLst.size());
			model.addAttribute("searchResults",UserProductBranchRsLst);
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "search";
	}*/
	public String searchUser(Model model,@ModelAttribute("searchForm") searchForm searchForm,HttpServletRequest request)
	{
		try
		{
			String userName = null;
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				userName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			
			
			String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			
			boolean areaFound = false;
			boolean branchFound = false;
			boolean provinceFound = false;
			ResourceBundle configMsgBundle = ResourceBundle.getBundle("config");
			String areaManager = configMsgBundle.getString("AREAMANAGERROLE");
			String branchRoleManager = configMsgBundle.getString("BRANCHROLE");
			String rlcRoleBranchManager = configMsgBundle.getString("RLCMANAGER");
			String userLoggedIn = (String)session.getAttribute("userLoggedIn");
			List<UserBaseRs> userBaseRsLst = null;
			if(userLoggedIn.equalsIgnoreCase("Manager"))
			{
				userBaseRsLst = serviceImpl.getUserIdListByRole(userName.toUpperCase());
				log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			}
			else
			{
				userBaseRsLst =  serviceImpl.getUserIdList();
				log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			}
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected is calling getUserProductCategory"+searchForm.getNtId());
			String ntId = searchForm.getNtId();
			model.addAttribute("userName", ntId);
			UsrBase usrBase = serviceImpl.getUserBasebyUserName(ntId);
			if(null!=usrBase)
			{
			//List<UserProductBranchRs> UserProductBranchRsLst =  serviceImpl.getUserLst(ntId);
			UserProductCategoryBranchRs UserProductCategoryBranchRs = serviceImpl.getUserProductCategory(ntId,usrBase.getBranchBase().getBid());
			
			log.debug("UserProductCategoryBranchRs "+UserProductCategoryBranchRs.getRoleName());
			//RoleBase roleBase =  serviceImpl.getRolebasebyRoleName(UserProductCategoryBranchRs.getRoleName());
			
			updateUserForm updateUserForm = new updateUserForm();
			updateUserForm.setExistingBid(usrBase.getBranchBase().getBid());
			updateUserForm.setExistingBranch(UserProductCategoryBranchRs.getBranchName());
			//updateUserForm.setExistingRole(String.valueOf(roleBase.getRid()));
			
			updateUserForm.setBranch(UserProductCategoryBranchRs.getBranchName());
			updateUserForm.setRole(UserProductCategoryBranchRs.getRoleName());
			updateUserForm.setRoleSet(UserProductCategoryBranchRs.getRoleSet());			
			updateUserForm.setExistingRoleSet(UserProductCategoryBranchRs.getRoleSet());
			
			updateUserForm.setProductCategorySet(UserProductCategoryBranchRs.getProductCategory());
			updateUserForm.setExistingProductCategorySet(UserProductCategoryBranchRs.getProductCategory());
			updateUserForm.setNtId(UserProductCategoryBranchRs.getNtId());
			updateUserForm.setName(UserProductCategoryBranchRs.getNtId());
			updateUserForm.setFirstName(UserProductCategoryBranchRs.getFirstName());
			updateUserForm.setMiddleName(UserProductCategoryBranchRs.getMiddleName());
			Set<String> roleSet = UserProductCategoryBranchRs.getRoleSet();
			Iterator iter = roleSet.iterator();
			List<String> updatedMasterRoleSet  = new ArrayList<String>();
			
			List<RoleBaseRs> roleBaseLst = serviceImpl.getAllRoles();
			for(RoleBaseRs roleBaseRs:roleBaseLst)
			{
				updatedMasterRoleSet.add(roleBaseRs.getRoleName());
			}
			while(iter.hasNext())
			{
				String key = (String)iter.next();
				log.debug("key is "+key);
			    if (updatedMasterRoleSet.contains(key))
			    {
			    	updatedMasterRoleSet.remove(key);
			    }
			}
			log.debug("updatedMasterRoleSet is "+updatedMasterRoleSet);
			log.debug("roleSet is "+roleSet);
			if(null!=roleSet)
			{
				for(String role:roleSet)
				{
					log.debug("role is "+role);
					if(role.equalsIgnoreCase("AreaManager"))
					//if("AreaManager".contentEquals(role))
					{
						//roleSelected="AreaManager";
						areaFound = true;
						log.debug("areaFound is "+areaFound);
					}
					if(role.equalsIgnoreCase("CreditAssistance") ||role.equalsIgnoreCase("CreditOfficer") || role.equalsIgnoreCase("BranchManager"))
					//if(role.contentEquals("CreditAssistance") ||role.contentEquals("CreditOfficer") || role.contentEquals("BranchManager"))
					{
						branchFound = true;
						log.debug("branchFound is "+branchFound);
					}
					/*else
					{
						provinceFound = true;
					}*/
				}
				if(!areaFound && !branchFound)
				{
					provinceFound = true;
					log.debug("provinceFound is "+provinceFound);
				}
				/*if(roleSet.contains(areaManager));
				{
					areaFound = true;
					roleSelected="AreaManager";
					roleFound = true;
					log.debug("roleSelected is "+roleSelected);
				}
				if(roleSet.contains(branchRoleManager))
				{
					branchFound = true;
					roleSelected="Branch";
					roleFound = true;
					log.debug("roleSelected is "+roleSelected);
				}
				if(!areaFound && branchFound)
				{
					provinceFound = true;
				}*/
			}
			
			Iterator productCategoryIter = UserProductCategoryBranchRs.getProductCategory().iterator();
			List<String> updatedMasterProductCategorySet  = new ArrayList<String>();
			
			List<ProductCategoryRs> productCategoryLst = serviceImpl.getAllProductCategory();
			for(ProductCategoryRs productCategoryRs:productCategoryLst)
			{
				updatedMasterProductCategorySet.add(productCategoryRs.getProductCategory());
			}
			while(productCategoryIter.hasNext())
			{
				String key = (String)productCategoryIter.next();
				//}
			//for (String key: existingRoleIdLst) {
				log.debug("ProductCategorykey is "+key);
			    if (updatedMasterProductCategorySet.contains(key))
			    {
			    	updatedMasterProductCategorySet.remove(key);
			    }
			}
			
			
			
			AreaBranchRs areaBranchRs = serviceImpl.getUserAreaBranchList(ntId);
			List<AreaBaseRs> allAreaRsLst = serviceImpl.getAllArea();
			Set<String> areaSet = null;
			 areaSet =areaBranchRs.getAreaName();
				List<String> updatedAreaLst  = null;
				updatedAreaLst =	new ArrayList<String>();
				 if(null!=areaSet && areaSet.size()>0 && areaFound)
				 {
					 Iterator areaIter = areaSet.iterator();
						for(AreaBaseRs areaRs:allAreaRsLst)
						{
							updatedAreaLst.add(areaRs.getAreaName());
						}
						while(areaIter.hasNext())
						{
							String key = (String)areaIter.next();
							log.debug("Area is "+key);
						    if (updatedAreaLst.contains(key))
						    {
						    	areaFound = true;
						    	updatedAreaLst.remove(key);
						    }
						}
				 }
				 else	//User doesn't have area
				 {
					 for(AreaBaseRs areaRs:allAreaRsLst)
						{
							updatedAreaLst.add(areaRs.getAreaName());
						}
				 }
			if(areaFound)
			{
				updateUserForm.setAreaName(areaSet);
				updateUserForm.setExistingAreaNameSet(areaSet);
			}
			
			
			ProvinceBranchRs provinceBranchRs = serviceImpl.getUserProvinceBranchList(ntId,roleSet,rlcRoleBranchManager);
			List<ProvinceBaseRs> allProvinceRsLst = serviceImpl.getAllProvince();
			Set<String> provinceSet = null;
			provinceSet =provinceBranchRs.getProvinceName();
			List<String> updatedProvinceLst  = null;
			updatedProvinceLst =	new ArrayList<String>();
			 if(null!=provinceSet && provinceSet.size()>0 && !areaFound)
			 {
				 Iterator provinceIter = provinceSet.iterator();
					for(ProvinceBaseRs provinceBaseRs:allProvinceRsLst)
					{
						updatedProvinceLst.add(provinceBaseRs.getProvinceName());
					}
					while(provinceIter.hasNext())
					{
						String key = (String)provinceIter.next();
						log.debug("Province is "+key);
					    if (updatedProvinceLst.contains(key))
					    {
					    	updatedProvinceLst.remove(key);
					    }
					}
			 }
			 else
			 {
				 for(ProvinceBaseRs provinceBaseRs:allProvinceRsLst)
					{
						updatedProvinceLst.add(provinceBaseRs.getProvinceName());
					}
			 }
			 if(provinceFound)
			 {
				 updateUserForm.setProvinceName(provinceSet);
				 updateUserForm.setExistingProvinceNameSet(provinceSet);
			 }
			
			String branchAllowedRoles = configMsgBundle.getString("branchAllowedRoles");
			log.debug("updatedMasterProductCategorySet is "+updatedMasterProductCategorySet);
			model.addAttribute("updateUserForm", updateUserForm);
			//model.addAttribute("searchResults",updateUserForm);
			model.addAttribute("productCategories", updatedMasterProductCategorySet);
			model.addAttribute("branches", serviceImpl.getAllBranches());
			model.addAttribute("roles", updatedMasterRoleSet);
			model.addAttribute("areas", updatedAreaLst);
			model.addAttribute("province", updatedProvinceLst);
			model.addAttribute("branchAllowedRoles", branchAllowedRoles);
			model.addAttribute("branchRoles", branchRoleManager);
			
			}
			else
			{
				String response = "Please Select a Valid User";
				model.addAttribute("responseMessage",response);
			}
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchUser";
	}
	
	
	@RequestMapping("/searchUserArea")
	public String searchUserArea(Model model,@ModelAttribute("searchAreaForm") searchAreaForm searchAreaForm,HttpServletRequest request)
	{
		try
		{
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String userName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected is "+searchAreaForm.getNtId());
			String ntId = searchAreaForm.getNtId();
			searchAreaForm searchAreaFormResults = new searchAreaForm();
			AreaBranchRs areaBranchRs = serviceImpl.getUserAreaBranchList(ntId);
			/*Set<String> roleNameSet = areaBranchRs.getRoleName();
			Iterator iter = roleNameSet.iterator();
			while(iter.hasNext())
			{
				log.debug("Role Name is "+(String) iter.next());
			}*/
			searchAreaFormResults.setFirstName(areaBranchRs.getFirstName());
			searchAreaFormResults.setMiddleName(areaBranchRs.getMiddleName());
			searchAreaFormResults.setAreaName(areaBranchRs.getAreaName());
			searchAreaFormResults.setBranchName(areaBranchRs.getBranchName());
			searchAreaFormResults.setRoleName(areaBranchRs.getRoleName());
			searchAreaFormResults.setProductCategory(areaBranchRs.getProductCategory());
			searchAreaFormResults.setUrpcid(areaBranchRs.getUrpcid());
			searchAreaFormResults.setExistingProductCategorySet(areaBranchRs.getProductCategory());
			//model.addAttribute("searchAreaForm", );
			
			log.debug("FirstName is "+areaBranchRs.getFirstName());
			log.debug("Middle Name is "+areaBranchRs.getMiddleName());
			
			Set<String> productCategoryRsSet = null;
			productCategoryRsSet =areaBranchRs.getProductCategory();
			List<String> updatedMasterProductCategoryLst  = null;
			updatedMasterProductCategoryLst =	new ArrayList<String>();
			 if(null!=productCategoryRsSet)
			 {
				Iterator iter = productCategoryRsSet.iterator();
				List<ProductCategoryRs> allProductCategoryRsLst = serviceImpl.getAllProductCategory();
				for(ProductCategoryRs productCategoryRs:allProductCategoryRsLst)
				{
					updatedMasterProductCategoryLst.add(productCategoryRs.getProductCategory());
				}
				while(iter.hasNext())
				{
					String key = (String)iter.next();
					log.debug("ProductCategory is "+key);
				    if (updatedMasterProductCategoryLst.contains(key))
				    {
				    	updatedMasterProductCategoryLst.remove(key);
				    }
				}
			 }
			 
			 Set<String> areaSet = null;
			 areaSet =areaBranchRs.getAreaName();
				List<String> updatedAreaLst  = null;
				updatedAreaLst =	new ArrayList<String>();
				 if(null!=areaSet)
				 {
					 Iterator iter = areaSet.iterator();
						List<AreaBaseRs> allAreaRsLst = serviceImpl.getAllArea();
						for(AreaBaseRs areaRs:allAreaRsLst)
						{
							updatedAreaLst.add(areaRs.getAreaName());
						}
						while(iter.hasNext())
						{
							String key = (String)iter.next();
							log.debug("Area is "+key);
						    if (updatedAreaLst.contains(key))
						    {
						    	updatedAreaLst.remove(key);
						    }
						}
				 } 
				 
			log.debug("Branch Name is "+areaBranchRs.getBranchName());
			log.debug("Product Category is "+areaBranchRs.getProductCategory());
			model.addAttribute("edit", true);
			model.addAttribute("userName", ntId);
			model.addAttribute("name", areaBranchRs.getFirstName()+" "+areaBranchRs.getMiddleName());
			model.addAttribute("searchUserAreaResults",searchAreaFormResults);
			model.addAttribute("areas", updatedAreaLst);
			model.addAttribute("masterProductCategories", updatedMasterProductCategoryLst);
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchArea";
	}
	
	@RequestMapping("/searchProvince")
	public String searchProvince(Model model,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		try
		{
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			model.addAttribute("searchProvinceForm", new searchProvinceForm());
			model.addAttribute("userIds",userBaseRsLst);
			ProvinceBranchRs provinceBranchRs= new ProvinceBranchRs();
			model.addAttribute("edit", false);
			model.addAttribute("searchUserProvinceResults",provinceBranchRs);
		
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchProvince";
	}
	
	@RequestMapping("/searchUserProvince")
	public String searchUserProvince(Model model,@ModelAttribute("searchProvinceForm") searchProvinceForm searchProvinceForm,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		try
		{
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected in searchProvinceForm is "+searchProvinceForm.getNtId());
			String ntId = searchProvinceForm.getNtId();
			searchProvinceForm searchProvinceFormResults = new searchProvinceForm();
			ProvinceBranchRs provinceBranchRs = null; // serviceImpl.getUserProvinceBranchList(ntId);
			
			searchProvinceFormResults.setFirstName(provinceBranchRs.getFirstName());
			searchProvinceFormResults.setMiddleName(provinceBranchRs.getMiddleName());
			searchProvinceFormResults.setProvinceName(provinceBranchRs.getProvinceName());
			searchProvinceFormResults.setAreaName(provinceBranchRs.getAreaName());
			searchProvinceFormResults.setBranchName(provinceBranchRs.getBranchName());
			searchProvinceFormResults.setRoleName(provinceBranchRs.getRoleName());
			searchProvinceFormResults.setProductCategory(provinceBranchRs.getProductCategory());
			searchProvinceFormResults.setUrpcid(provinceBranchRs.getUrpcid());
			//model.addAttribute("searchAreaForm", );
			model.addAttribute("edit", true);
			model.addAttribute("userName", ntId);
			model.addAttribute("searchUserProvinceResults",searchProvinceFormResults);
			model.addAttribute("province", serviceImpl.getAllProvince());
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchProvince";
	}
	
	@RequestMapping("/searchUserBranch")
	public String searchUserBranch(Model model,@ModelAttribute("searchBranchForm") searchBranchForm searchBranchForm,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		try
		{
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected in searchBranchForm is "+searchBranchForm.getNtId());
			String ntId = searchBranchForm.getNtId();
			searchBranchForm searchBranchFormResults = new searchBranchForm();
			BranchRs branchRs = serviceImpl.getUserBranchList(ntId);
			/*Set<String> roleNameSet = areaBranchRs.getRoleName();
			Iterator iter = roleNameSet.iterator();
			while(iter.hasNext())
			{
				log.debug("Role Name is "+(String) iter.next());
			}*/
			searchBranchFormResults.setBranchName(branchRs.getBranchName());
			searchBranchFormResults.setRoleName(branchRs.getRoleName());
			searchBranchFormResults.setProductCategory(branchRs.getProductCategory());
			searchBranchFormResults.setUrpcid(branchRs.getUrpcid());
			//model.addAttribute("searchAreaForm", );
			model.addAttribute("edit", true);
			model.addAttribute("userName", ntId);
			model.addAttribute("searchUserBranchResults",searchBranchFormResults);
			model.addAttribute("branches", serviceImpl.getAllBranches());
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "searchBranch";
	}
	
	@RequestMapping("/submitUserArea")
	public String submitUserArea(Model model,@ModelAttribute("searchAreaForm") searchAreaForm searchAreaForm,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		String response= null;
		try
		{
			String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			log.debug("AreaName"+searchAreaForm.getAreaName());
			log.debug("NTID"+searchAreaForm.getNtId());
			//log.debug("urpcid "+searchAreaForm.getUrpcid());
			
			Set<String> roleSet = searchAreaForm.getRoleName();
			List<Integer> roleIdLst = serviceImpl.getRoleIdByName(roleSet);
			
			log.debug("updateUserForm ProductCategory "+searchAreaForm.getProductCategory().size());
			Set<String> productCategorySet = searchAreaForm.getProductCategory();
			
			Set<String> existingProductCategorySet = null;
			existingProductCategorySet = new HashSet<String>();
			
			for(String key:searchAreaForm.getExistingProductCategorySet()) // to remove exta [] coming from jsp page
			{
				key = key.replaceAll("\\[", "");
				key = key.replaceAll("\\]", "");
				existingProductCategorySet.add(key.trim());
			}
			
			log.debug("existingProductCategorySet is "+existingProductCategorySet);
			
			List<Integer> productCategoryIdLst = serviceImpl.getProductCategoryIdByName(productCategorySet);
			List<Integer> existingProductCategoryIdLst = serviceImpl.getProductCategoryIdByName(existingProductCategorySet);
			
			List<Integer> newProductCategoryIdLst = null;
			newProductCategoryIdLst = new ArrayList<Integer>();
			newProductCategoryIdLst.addAll(productCategoryIdLst);
			
			Iterator iter1 = existingProductCategoryIdLst.iterator();
				while(iter1.hasNext())
				{
					Integer key = (Integer)iter1.next();
				//}
			//for (String key: existingRoleIdLst) {
				log.debug("productCategorykey is "+key);
			    if (newProductCategoryIdLst.contains(key))
			    {
			    	newProductCategoryIdLst.remove(key);
			    }
			    else
			    {
			    	newProductCategoryIdLst.add(key);
			    }
			}
			log.debug("newProductCategoryIdLst is "+newProductCategoryIdLst);
			
			
			serviceImpl.updateProductCategoryBranchByArea(clientIP,searchAreaForm,newProductCategoryIdLst,roleIdLst);
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);
		}
		 catch (HibernateException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Hibernate Exception occurred:\n", ex);
				log.error("Hibernate Exception occurred:\n", ex);
				return exceptionPage;
			} catch (ArrayIndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (IndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (NullPointerException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Null pointer exception:\n", ex);
				log.error("Null pointer exception:\n", ex);
				return exceptionPage;
			} catch (RuntimeException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Runtime Exception occurred:\n", ex);
				log.error("Runtime Exception occurred:\n", ex);
				return exceptionPage;
			} catch (Exception ex) {
				request.setAttribute("exception", "exception");
				log.debug("An unknown Exception occurred:\n", ex);
				log.error("An unknown Exception occurred:\n", ex);
				return exceptionPage;
			}
		
		return "searchArea";
	}
	
	@RequestMapping("/submitUserProvince")
	public String submitUserProvince(Model model,@ModelAttribute("searchProvinceForm") searchProvinceForm searchProvinceForm,HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		String response= null;
		try
		{
		
			log.debug("ProvinceName"+searchProvinceForm.getProvinceName());
			log.debug("NTID"+searchProvinceForm.getNtId());
			//log.debug("urpcid "+searchAreaForm.getUrpcid());
			serviceImpl.updateProductCategoryBranchByProvince(searchProvinceForm);
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);
		}
		 catch (HibernateException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Hibernate Exception occurred:\n", ex);
				log.error("Hibernate Exception occurred:\n", ex);
				return exceptionPage;
			} catch (ArrayIndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (IndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (NullPointerException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Null pointer exception:\n", ex);
				log.error("Null pointer exception:\n", ex);
				return exceptionPage;
			} catch (RuntimeException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Runtime Exception occurred:\n", ex);
				log.error("Runtime Exception occurred:\n", ex);
				return exceptionPage;
			} catch (Exception ex) {
				request.setAttribute("exception", "exception");
				log.debug("An unknown Exception occurred:\n", ex);
				log.error("An unknown Exception occurred:\n", ex);
				return exceptionPage;
			}
		
		return "searchProvince";
	}
	
	@RequestMapping("/submitUserBranch")
	public String submitUserBranch(Model model, @ModelAttribute("searchBranchForm") searchBranchForm searchBranchForm,
			 HttpServletRequest request)
	{
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		String response= null;
		try
		{
		
			log.debug("BranchName"+searchBranchForm.getBranchName());
			log.debug("NTID"+searchBranchForm.getNtId());
			//log.debug("urpcid "+searchAreaForm.getUrpcid());
			serviceImpl.updateProductCategoryBranchByBranch(searchBranchForm);
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);
		}
		 catch (HibernateException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Hibernate Exception occurred:\n", ex);
				log.error("Hibernate Exception occurred:\n", ex);
				return exceptionPage;
			} catch (ArrayIndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (IndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (NullPointerException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Null pointer exception:\n", ex);
				log.error("Null pointer exception:\n", ex);
				return exceptionPage;
			} catch (RuntimeException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Runtime Exception occurred:\n", ex);
				log.error("Runtime Exception occurred:\n", ex);
				return exceptionPage;
			} catch (Exception ex) {
				request.setAttribute("exception", "exception");
				log.debug("An unknown Exception occurred:\n", ex);
				log.error("An unknown Exception occurred:\n", ex);
				return exceptionPage;
			}
		
		return "searchBranch";
	}
	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	/*public String update(Model model, @ModelAttribute("updateUserForm") updateUserForm updateUserForm,
			 @RequestParam(value="urpcid") String urpcid,HttpServletRequest request) {
		try {
			String response = null;
			log.debug("updateUserForm branch "+updateUserForm.getBranch());
			log.debug("updateUserForm role "+updateUserForm.getRole());
			log.debug("updateUserForm Existing role "+updateUserForm.getExistingRole());
			log.debug("updateUserForm branch "+updateUserForm.getProductCategory());
			log.debug("updateUserForm Name "+updateUserForm.getName());
			log.debug("urpcid "+urpcid);
			UserProductBranchRs userProductBranchRs = new UserProductBranchRs();
			userProductBranchRs.setBid(Integer.parseInt(updateUserForm.getBranch()));
			userProductBranchRs.setRid(Integer.parseInt(updateUserForm.getRole()));
			userProductBranchRs.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			userProductBranchRs.setUrpcid(Integer.parseInt(urpcid));
			serviceImpl.updateUsrProductCategory(userProductBranchRs);
			ResourceBundle configMsgBundle = ResourceBundle.getBundle("config");
			String providerUri = configMsgBundle.getString("providerUri");
			String AdminUser = configMsgBundle.getString("AdminUser");
			String adminPwd = configMsgBundle.getString("adminPwd");
			String adminDN = configMsgBundle.getString("adminDN");
			UpmLdapEvents ldapEvents = new UpmLdapEvents();
			String userName= updateUserForm.getName();
			userName = userName.replaceAll(",", "");
			log.debug("userName is "+userName);
			String roleName = serviceImpl.getRoleName(Integer.parseInt(updateUserForm.getRole()));
			ldapEvents.updateRole(updateUserForm.getExistingRole(), roleName,userName);
			
			ldapEvents.GetUserGroupsByUserName(userName);
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);
		}
		 catch (HibernateException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Hibernate Exception occurred:\n", ex);
				log.error("Hibernate Exception occurred:\n", ex);
				return exceptionPage;
			} catch (ArrayIndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (IndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (NullPointerException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Null pointer exception:\n", ex);
				log.error("Null pointer exception:\n", ex);
				return exceptionPage;
			} catch (RuntimeException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Runtime Exception occurred:\n", ex);
				log.error("Runtime Exception occurred:\n", ex);
				return exceptionPage;
			} catch (Exception ex) {
				request.setAttribute("exception", "exception");
				log.debug("An unknown Exception occurred:\n", ex);
				log.error("An unknown Exception occurred:\n", ex);
				return exceptionPage;
			}
			return "redirect:/search";
		}*/
	public String update(Model model, @ModelAttribute("updateUserForm") updateUserForm updateUserForm,
			 HttpServletRequest request) {
		try {
			HttpSession session = null;
			String loggedInUser=null;
			session = request.getSession(false);
			if(null!=session){
				loggedInUser = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+loggedInUser);
				if (loggedInUser == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			String response = null;
			
			String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			 
			log.debug("updateUserForm User "+updateUserForm.getNtId());
			log.debug("updateUserForm  Branch "+updateUserForm.getBranch());
			log.debug("updateUserForm Exsting branch "+updateUserForm.getExistingBranch());
			log.debug("updateUserForm Exsting BID "+updateUserForm.getExistingBid());
			log.debug("updateUserForm role "+updateUserForm.getRoleSet());
			log.debug("updateUserForm Existing role "+updateUserForm.getExistingRoleSet());
			log.debug("updateUserForm ProductCategory "+updateUserForm.getProductCategorySet().size());
			
			Set<String> productCategorySet = updateUserForm.getProductCategorySet();
			Set<String> roleSet = updateUserForm.getRoleSet();
			Set<String> areaSet = updateUserForm.getAreaName();
			Set<String> provinceSet = updateUserForm.getProvinceName();

			Set<String> existingRoleSet = null;
			existingRoleSet = new HashSet<String>();
			for(String key:updateUserForm.getExistingRoleSet()) // to remove exta [] coming from jsp page
			{
				key = key.replaceAll("\\[", "");
				key = key.replaceAll("\\]", "");
				existingRoleSet.add(key.trim());
			}
			
			Set<String> existingProductCategorySet = null;
			existingProductCategorySet = new HashSet<String>();
			
			for(String key:updateUserForm.getExistingProductCategorySet()) // to remove exta [] coming from jsp page
			{
				key = key.replaceAll("\\[", "");
				key = key.replaceAll("\\]", "");
				existingProductCategorySet.add(key.trim());
			}
			
			Set<String> existingAreaSet = null;
			existingAreaSet = new HashSet<String>();
			for(String key:updateUserForm.getExistingAreaNameSet()) // to remove exta [] coming from jsp page
			{
				key = key.replaceAll("\\[", "");
				key = key.replaceAll("\\]", "");
				existingAreaSet.add(key.trim());
			}
			
			Set<String> existingProvinceSet = null;
			existingProvinceSet = new HashSet<String>();
			for(String key:updateUserForm.getExistingProvinceNameSet())  // to remove exta [] coming from jsp page
			{
				key = key.replaceAll("\\[", "");
				key = key.replaceAll("\\]", "");
				existingProvinceSet.add(key.trim());
			}
			
			log.debug("existingRoleSet is "+existingRoleSet);
			log.debug("existingProductCategorySet is "+existingProductCategorySet);
			log.debug("existingAreaSet is "+existingAreaSet);
			log.debug("existingProvinceSet is "+existingProvinceSet);
			
			List<Integer> productCategoryIdLst = serviceImpl.getProductCategoryIdByName(productCategorySet);
			List<Integer> existingProductCategoryIdLst = serviceImpl.getProductCategoryIdByName(existingProductCategorySet);
			List<Integer> roleIdLst = serviceImpl.getRoleIdByName(roleSet);
			List<Integer> existingRoleIdLst = serviceImpl.getRoleIdByName(existingRoleSet);
			
			List<Integer> areaIdLst = null, existingAreaIdLst=null,provinceIdLst = null,existingProvinceIdLst=null;
			if(null!=areaSet && areaSet.size()>0)
			{
				areaIdLst = serviceImpl.getAreaIdByName(areaSet);
			}
			if(null!=existingAreaSet && existingAreaSet.size()>0)
			{
				existingAreaIdLst = serviceImpl.getAreaIdByName(existingAreaSet);
			}
			if(null!=provinceSet && provinceSet.size()>0)
			{
				provinceIdLst = serviceImpl.getProvinceIdByName(provinceSet);
			}
			if(null!=existingProvinceSet && existingProvinceSet.size()>0)
			{
				existingProvinceIdLst = serviceImpl.getRoleIdByName(existingProvinceSet);
			}
			
			
			log.debug("existingRoleIdLst before difference is "+existingRoleIdLst);
			log.debug("existingProductCategoryIdLst before difference is "+existingProductCategoryIdLst);
			log.debug("existingAreaIdLst before difference is "+existingAreaIdLst);
			log.debug("existingProvinceIdLst before difference is "+existingProvinceIdLst);
			List<Integer> newRoleIdLst = null;
			newRoleIdLst = new ArrayList<Integer>();
			newRoleIdLst.addAll(roleIdLst);
			List<Integer> newAreaIdLst = null;
			if(null!=areaIdLst)
			{
				
				newAreaIdLst = new ArrayList<Integer>();
				newAreaIdLst.addAll(areaIdLst);
			}
			List<Integer> newProvinceIdLst = null;
			if(null!=provinceIdLst)
			{
				newProvinceIdLst = new ArrayList<Integer>();
				newProvinceIdLst.addAll(provinceIdLst);
			}
			
			if(null!=existingAreaIdLst && null!=newAreaIdLst)
			{
				Iterator areaIter = existingAreaIdLst.iterator();
				while(areaIter.hasNext())
				{
					Integer key = (Integer)areaIter.next();
			    if (newAreaIdLst.contains(key))
			    {
			    	newAreaIdLst.remove(key);
			    }
			    else
			    {
			    	//newAreaIdLst.add(key); //No need to add Existing again
			    }
				}
			}
		log.debug("newAreaIdLst is "+newAreaIdLst);
		if(null!=existingProvinceIdLst && null!=newProvinceIdLst)
		{
			Iterator provinceIter = existingProvinceIdLst.iterator();
			while(provinceIter.hasNext())
			{
				Integer key = (Integer)provinceIter.next();
			    if (newProvinceIdLst.contains(key))
			    {
			    	newProvinceIdLst.remove(key);
			    }
			    else
			    {
			    	//newProvinceIdLst.add(key);
			    }
			}
		}
		log.debug("newProvinceIdLst is "+newProvinceIdLst);	
			Iterator iter = existingRoleIdLst.iterator();
				while(iter.hasNext())
				{
					Integer key = (Integer)iter.next();
			    if (newRoleIdLst.contains(key))
			    {
			    	newRoleIdLst.remove(key);
			    }
			    else
			    {
			    	newRoleIdLst.add(key);
			    }
			}
			log.debug("newRoleIdLst is "+newRoleIdLst);
			
			List<Integer> newProductCategoryIdLst = null;
			newProductCategoryIdLst = new ArrayList<Integer>();
			newProductCategoryIdLst.addAll(productCategoryIdLst);
			
			Iterator iter1 = existingProductCategoryIdLst.iterator();
				while(iter1.hasNext())
				{
					Integer key = (Integer)iter1.next();
			    if (newProductCategoryIdLst.contains(key))
			    {
			    	newProductCategoryIdLst.remove(key);
			    }
			    else
			    {
			    	newProductCategoryIdLst.add(key);
			    }
			}
			log.debug("newProductCategoryIdLst is "+newProductCategoryIdLst);
			
			UsrBase usrBase = serviceImpl.getUserBasebyUserName(updateUserForm.getNtId());
			updateUserForm.setUid(usrBase.getUid());
//Setting Branch to ExistingBid			
			updateUserForm.setExistingBid(usrBase.getBranchBase().getBid());
			serviceImpl.updateProductCategoryBranchByUser(loggedInUser,clientIP,updateUserForm,roleIdLst,newRoleIdLst,productCategoryIdLst,newProductCategoryIdLst,areaIdLst,newAreaIdLst,provinceIdLst,newProvinceIdLst);
			
			ResourceBundle configMsgBundle = ResourceBundle.getBundle("config");
			String providerUri = configMsgBundle.getString("providerUri");
			String AdminUser = configMsgBundle.getString("AdminUser");
			String adminPwd = configMsgBundle.getString("adminPwd");
			String adminDN = configMsgBundle.getString("adminDN");
			UpmLdapEvents ldapEvents = new UpmLdapEvents();
			String userName= updateUserForm.getName();
			userName = userName.replaceAll(",", "");
			log.debug("userName is "+userName);
			//String roleName = serviceImpl.getRoleName(Integer.parseInt(updateUserForm.getRole()));
			for(String existingRole:existingRoleSet)
			{
				//existingRole = existingRole+"_Test";
				existingRole = existingRole;
				log.debug("existingRole is "+existingRole);
				
				ldapEvents.RemoveUserFromGroup(existingRole, userName);
			}
			for(String newRole:roleSet)
			{
			
			//newRole = newRole+"_Test";
			newRole = newRole;
			log.debug("newRole is "+newRole);
			ldapEvents.AddUserToGroup(newRole, userName);
			}
			
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);
			
			//log.debug("updateUserForm Name "+updateUserForm.getName());
			//log.debug("urpcid "+urpcid);
			/*UserProductBranchRs userProductBranchRs = new UserProductBranchRs();
			userProductBranchRs.setBid(Integer.parseInt(updateUserForm.getBranch()));
			userProductBranchRs.setRid(Integer.parseInt(updateUserForm.getRole()));
			userProductBranchRs.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			userProductBranchRs.setUrpcid(Integer.parseInt(urpcid));
			serviceImpl.updateUserBranch(userProductBranchRs);
			ResourceBundle configMsgBundle = ResourceBundle.getBundle("config");
			String providerUri = configMsgBundle.getString("providerUri");
			String AdminUser = configMsgBundle.getString("AdminUser");
			String adminPwd = configMsgBundle.getString("adminPwd");
			String adminDN = configMsgBundle.getString("adminDN");
			UpmLdapEvents ldapEvents = new UpmLdapEvents();
			String userName= updateUserForm.getName();
			userName = userName.replaceAll(",", "");
			log.debug("userName is "+userName);
			String roleName = serviceImpl.getRoleName(Integer.parseInt(updateUserForm.getRole()));
			ldapEvents.updateRole(updateUserForm.getExistingRole(), roleName,userName);
			
			ldapEvents.GetUserGroupsByUserName(userName);
			response="User details updated successfully";
			model.addAttribute("responseMessage",response);*/
		}
		 catch (HibernateException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Hibernate Exception occurred:\n", ex);
				log.error("Hibernate Exception occurred:\n", ex);
				return exceptionPage;
			} catch (ArrayIndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (IndexOutOfBoundsException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Array Index is out of bound:\n", ex);
				log.error("Array Index is out of bound:\n", ex);
				return exceptionPage;
			} catch (NullPointerException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Null pointer exception:\n", ex);
				log.error("Null pointer exception:\n", ex);
				return exceptionPage;
			} catch (RuntimeException ex) {
				request.setAttribute("exception", "exception");
				log.debug("Runtime Exception occurred:\n", ex);
				log.error("Runtime Exception occurred:\n", ex);
				return exceptionPage;
			} catch (Exception ex) {
				request.setAttribute("exception", "exception");
				log.debug("An unknown Exception occurred:\n", ex);
				log.error("An unknown Exception occurred:\n", ex);
				return exceptionPage;
			}
			return "redirect:/search";
		}
	
	
	@RequestMapping("/user")
	/*public String user(
			Model model,
			@RequestParam(value = "edit", defaultValue = "false", required = true) boolean edit,
			@RequestParam(value = "urpcid", defaultValue = "default", required = true) String urpcid,
			@RequestParam(value = "userName", defaultValue = "default", required = true) String userName,
			@RequestParam(value = "roleName", defaultValue = "default", required = true) String roleName,
			HttpServletRequest request) {
		try {
			if (request.getSession(false) == null)
				return "redirect:/logout";
			if(edit)
			{
				
				model.addAttribute("editUser", serviceImpl.getProductCategoryBranch(urpcid));
				model.addAttribute("branches", serviceImpl.getAllBranches());
				model.addAttribute("roles", serviceImpl.getAllRoles());
				model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
				model.addAttribute("edit", edit);
				model.addAttribute("existingRoleName", roleName);
				model.addAttribute("urpcid", urpcid);
				model.addAttribute("updateUserForm", new updateUserForm());
			}
			else
			{
				model.addAttribute("branches", serviceImpl.getAllBranches());
				model.addAttribute("roles", serviceImpl.getAllRoles());
				model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
				model.addAttribute("edit", edit);
				model.addAttribute("userName", userName);
				model.addAttribute("updateUserForm", new updateUserForm());
			}
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "editUser";
	}*/
	public String user(
			Model model,
			@RequestParam(value = "edit", defaultValue = "false", required = true) boolean edit,
			@RequestParam(value = "urpcid", defaultValue = "default", required = true) String urpcid,
			@RequestParam(value = "userName", defaultValue = "default", required = true) String userName,
			@RequestParam(value = "roleName", defaultValue = "default", required = true) String roleName,
			HttpServletRequest request) {
		try {
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String LoggedUserName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+LoggedUserName);
				if (LoggedUserName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			if(edit)
			{
				
				model.addAttribute("editUser", serviceImpl.getUserLstByUrpcId(urpcid));
				model.addAttribute("branches", serviceImpl.getAllBranches());
				model.addAttribute("roles", serviceImpl.getAllRoles());
				model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
				model.addAttribute("edit", edit);
				model.addAttribute("existingRoleName", roleName);
				model.addAttribute("urpcid", urpcid);
				model.addAttribute("updateUserForm", new updateUserForm());
			}
			else
			{
				model.addAttribute("branches", serviceImpl.getAllBranches());
				model.addAttribute("roles", serviceImpl.getAllRoles());
				model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
				model.addAttribute("edit", edit);
				model.addAttribute("userName", userName);
				model.addAttribute("updateUserForm", new updateUserForm());
			}
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "editUser";
	}
	
	@RequestMapping("/delete")
	public String delete(
			Model model,			
			@RequestParam(value = "urpcid", defaultValue = "default", required = true) String urpcid,
			@RequestParam(value = "userName", defaultValue = "default", required = true) String userName,
			@RequestParam(value = "roleName", defaultValue = "default", required = true) String roleName,
			HttpServletRequest request) {
		try {
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String LoggedUserName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+LoggedUserName);
				if (LoggedUserName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}			
				model.addAttribute("branches", serviceImpl.getAllBranches());
				model.addAttribute("roles", serviceImpl.getAllRoles());
				model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
				model.addAttribute("userName", userName);
				model.addAttribute("updateUserForm", new updateUserForm());
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "editUser";
	}
	
	
	@RequestMapping(value = "/searchAD")
	public String searchAD(Model model,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request) {
		String response=null;
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		model.addAttribute("updateUserForm", new updateUserForm());
		model.addAttribute("edit", "true");
		//response="User not found in ActiveDirectory";
		model.addAttribute("responseMessage",responseMessage);
		return "createUser";
	}
	
	@RequestMapping("/searchDeleteUser")
	public String searchDeleteUser(Model model,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request)
	{
		try
		{
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String userName = (String)session.getAttribute("userName");
				log.debug("userName in searchDeleteUser is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			List<UserBaseRs> userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			model.addAttribute("searchForm", new searchForm());
			model.addAttribute("userIds",userBaseRsLst);
			/*List<UserProductBranchRs> UserProductBranchRsLst=null;
			UserProductBranchRsLst = new ArrayList<UserProductBranchRs>();
			model.addAttribute("searchResults",UserProductBranchRsLst);*/
			model.addAttribute("responseMessage", responseMessage);
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
	return "deleteUser";
	}
	
	@RequestMapping(value = "/deleteUser")
	public String deleteUser(Model model,@ModelAttribute("searchForm") searchForm searchForm,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request)
	{
		String response=null;
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			String userName = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+userName);
			if (userName == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		List<UserBaseRs> userBaseRsLst;
		try {
			
			
			userBaseRsLst =  serviceImpl.getUserIdList();
			log.debug("userBaseRsLst size is "+userBaseRsLst.size());
			model.addAttribute("userIds",userBaseRsLst);
			
			log.debug("ntId Selected is calling getUserProductCategory"+searchForm.getNtId());
			String ntId = searchForm.getNtId();
			model.addAttribute("userName", ntId);
			UsrBase usrBase = serviceImpl.getUserBasebyUserName(ntId);
			if(null!=usrBase)
			{
			//List<UserProductBranchRs> UserProductBranchRsLst =  serviceImpl.getUserLst(ntId);
			UserProductCategoryBranchRs UserProductCategoryBranchRs = serviceImpl.getUserProductCategory(ntId,usrBase.getBranchBase().getBid());
			
			log.debug("UserProductCategoryBranchRs "+UserProductCategoryBranchRs.getRoleName());
			//RoleBase roleBase =  serviceImpl.getRolebasebyRoleName(UserProductCategoryBranchRs.getRoleName());
			
			updateUserForm updateUserForm = new updateUserForm();
			updateUserForm.setExistingBid(usrBase.getBranchBase().getBid());
			
			//updateUserForm.setExistingRole(String.valueOf(roleBase.getRid()));
			
			updateUserForm.setBranch(UserProductCategoryBranchRs.getBranchName());
			updateUserForm.setRole(UserProductCategoryBranchRs.getRoleName());
			updateUserForm.setRoleSet(UserProductCategoryBranchRs.getRoleSet());			
			updateUserForm.setExistingRoleSet(UserProductCategoryBranchRs.getRoleSet());
			
			updateUserForm.setProductCategorySet(UserProductCategoryBranchRs.getProductCategory());
			updateUserForm.setExistingProductCategorySet(UserProductCategoryBranchRs.getProductCategory());
			updateUserForm.setNtId(UserProductCategoryBranchRs.getNtId());
			updateUserForm.setFirstName(UserProductCategoryBranchRs.getFirstName());
			updateUserForm.setMiddleName(UserProductCategoryBranchRs.getMiddleName());
			Iterator iter = UserProductCategoryBranchRs.getRoleSet().iterator();
			List<String> updatedMasterRoleSet  = new ArrayList<String>();
			
			List<RoleBaseRs> roleBaseLst = serviceImpl.getAllRoles();
			for(RoleBaseRs roleBaseRs:roleBaseLst)
			{
				updatedMasterRoleSet.add(roleBaseRs.getRoleName());
			}
			while(iter.hasNext())
			{
				String key = (String)iter.next();
				//}
			//for (String key: existingRoleIdLst) {
				log.debug("key is "+key);
			    if (updatedMasterRoleSet.contains(key))
			    {
			    	updatedMasterRoleSet.remove(key);
			    }
			}
			log.debug("updatedMasterRoleSet is "+updatedMasterRoleSet);
			Iterator productCategoryIter = UserProductCategoryBranchRs.getProductCategory().iterator();
			List<String> updatedMasterProductCategorySet  = new ArrayList<String>();
			
			List<ProductCategoryRs> productCategoryLst = serviceImpl.getAllProductCategory();
			for(ProductCategoryRs productCategoryRs:productCategoryLst)
			{
				updatedMasterProductCategorySet.add(productCategoryRs.getProductCategory());
			}
			while(productCategoryIter.hasNext())
			{
				String key = (String)productCategoryIter.next();
				//}
			//for (String key: existingRoleIdLst) {
				log.debug("ProductCategorykey is "+key);
			    if (updatedMasterProductCategorySet.contains(key))
			    {
			    	updatedMasterProductCategorySet.remove(key);
			    }
			}
			log.debug("updatedMasterProductCategorySet is "+updatedMasterProductCategorySet);
			model.addAttribute("updateUserForm", updateUserForm);
			//model.addAttribute("searchResults",updateUserForm);
			model.addAttribute("productCategories", updatedMasterProductCategorySet);
			model.addAttribute("branches", serviceImpl.getAllBranches());
			model.addAttribute("roles", updatedMasterRoleSet);
			model.addAttribute("responseMessage",responseMessage);
			}
			else
			{
				response = "Please Select a Valid User";
				model.addAttribute("responseMessage",response);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "deleteUser";
	}
	
	@RequestMapping(value = "/submitDeleteUser")
	public String submitDeleteUser(Model model,@ModelAttribute("updateUserForm") updateUserForm updateUserForm,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request)
	{
		String loggedInUser = null;
		HttpSession session = null;
		session = request.getSession(false);
		if(null!=session){
			loggedInUser = (String)session.getAttribute("userName");
			log.debug("userName in searchArea is "+loggedInUser);
			if (loggedInUser == null)
			{
				return "redirect:/logout";
			}
		}
		else if(null==session)
		{
			return "redirect:/logout";
		}
		 String clintHost = request.getRemoteHost();
		 log.debug("clintHost "+clintHost);
		 String clientIP = request.getRemoteAddr();
		 log.debug("clientIP "+clientIP);
		serviceImpl.deleteUser(clientIP,loggedInUser,updateUserForm);
		Set<String> existingRoleSet = null;
		existingRoleSet = new HashSet<String>();
		UpmLdapEvents ldapEvents = new UpmLdapEvents();
		for(String key:updateUserForm.getExistingRoleSet()) // to remove exta [] coming from jsp page
		{
			key = key.replaceAll("\\[", "");
			key = key.replaceAll("\\]", "");
			//String existingRole = key.trim()+"_Test";
			String existingRole = key.trim();
			existingRoleSet.add(key.trim());
			log.debug("existingRole is "+existingRole);
			ldapEvents.RemoveUserFromGroup(existingRole, updateUserForm.getNtId());
		}
		responseMessage = "User deleted succesfully ";
		model.addAttribute("searchForm", new searchForm());
		model.addAttribute("responseMessage",responseMessage);
		return "redirect:/searchDeleteUser";
	}
	
	@RequestMapping(value = "/logon")
	public String logon(Model model,@RequestParam(value="responseMessage",defaultValue = "", required = false) String responseMessage,HttpServletRequest request)
	{
		model.addAttribute("logonForm",new logonForm());	
		model.addAttribute("responseMessage",responseMessage);
		return "index";
	}
	@RequestMapping(value = "/submitLogon")
	public String submitLogon(Model model,@ModelAttribute("logonForm") logonForm logonForm,HttpServletRequest request)
	{
		log.debug(" userName "+logonForm.getUserName());
		//log.debug(" password "+logonForm.getPassword());
		String responseMessage= null;
		String responsePage = null;
		UpmLdapEvents ldapEvents = new UpmLdapEvents();
		String userLoggedIn = null;
		try {
			//boolean flag = ldapEvents.userAuthentication(logonForm.getUserName(), logonForm.getPassword());
			userLoggedIn =  ldapEvents.userAuthentication(logonForm.getUserName(), logonForm.getPassword());
			if(null!=userLoggedIn)
			{
			model.addAttribute("responseMessage",responseMessage);
			HttpSession session = request.getSession();
			session.setAttribute("userName", logonForm.getUserName());
			session.setAttribute("userLoggedIn", userLoggedIn);
			 String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			log.debug(logonForm.getUserName()+"	Logged In from IpAdress "+clientIP);
			responsePage = "redirect:/search";
			}
			else
			{
				//responseMessage= "User doesn't exists, please check username and password";
				responseMessage= "Administrator or BranchManager are allowed to logon";
				model.addAttribute("responseMessage",responseMessage);
				responsePage = "redirect:/logon";
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			responseMessage = "Unable to login";
			model.addAttribute("responseMessage",responseMessage);
			responsePage = "redirect:/logon";
		}
		
		return responsePage;
	}
	@RequestMapping(value = "/logout")
	public String logout(Model model,HttpServletRequest request)
	{
		HttpSession session = request.getSession(false);
		if(null!=session)
		{
			String userName = (String)session.getAttribute("userName");
			String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			log.debug(userName+"	Logged Out from IpAdress "+clientIP);
			return "redirect:/logon";
		}
		else
		{
			session.invalidate();
			return "redirect:/logon";
		}
		/*HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/logon";*/
	}
	
	@RequestMapping(value = "/getUserAD", method = RequestMethod.POST)
	public String getUserAD(
			Model model,@ModelAttribute("updateUserForm") updateUserForm updateUserForm,HttpServletRequest request) {
		try
		{
			String response=null;
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				String userName = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+userName);
				if (userName == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			log.debug("ntId Selected in getUserAD is "+updateUserForm.getNtId());
			String ntId = updateUserForm.getNtId();
			UpmLdapEvents ldapEvents = new UpmLdapEvents();
			String userName = ldapEvents.findUserByUserName(ntId);
			if(null!=userName)
			{
				UsrBase usrBase = serviceImpl.getUserBasebyUserName(ntId.toUpperCase());
				if(null!=usrBase && ((usrBase.getDeleteflag()==null) || (usrBase.getDeleteflag()!=0) ))
				{
					response="User already exist";
					model.addAttribute("edit", "true");
					model.addAttribute("responseMessage",response);
				}
				else
				{
					model.addAttribute("branches", serviceImpl.getAllBranches());
					model.addAttribute("roles", serviceImpl.getAllRoles());
					model.addAttribute("productCategory", serviceImpl.getAllProductCategory());
					model.addAttribute("edit", "false");
					model.addAttribute("userName", userName);
				}
			}
			else
			{
				response="User not found in ActiveDirectory";
				model.addAttribute("edit", "true");
				model.addAttribute("responseMessage",response);
				model.addAttribute("updateUserForm", new updateUserForm());
			}
			
			
		}
		catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "createUser";
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	/*public String createUser(Model model, @ModelAttribute("updateUserForm") updateUserForm updateUserForm,
			HttpServletRequest request) {
		try {
			if (request.getSession(false) == null)
				return "redirect:/logout";
			
			String response = null;
			String message = null;
			log.debug("Branch "+updateUserForm.getBranch());
			log.debug("Role "+updateUserForm.getRole());
			log.debug("name in createUser is  "+updateUserForm.getName());
			log.debug("Message is " + message);
			String roleName = serviceImpl.getRoleName(Integer.parseInt(updateUserForm.getRole()));
			roleName = roleName+"_Test";
			int uid = serviceImpl.getUserId(updateUserForm.getName());
			log.debug("uid is "+uid);
			if(uid!=0)
			{
			updateUserForm.setUid(uid);
			serviceImpl.createUser(updateUserForm);
			UpmLdapEvents ldapEvents = new UpmLdapEvents();
			ldapEvents.AddUserToGroup(roleName,updateUserForm.getName());
			response="User details added successfully";
			model.addAttribute("responseMessage",response);
			}
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "redirect:/search";
	}*/
	public String createUser(Model model, @ModelAttribute("updateUserForm") updateUserForm updateUserForm,
			HttpServletRequest request) {
		try {
			String loggedInUser = null;
			HttpSession session = null;
			session = request.getSession(false);
			if(null!=session){
				loggedInUser = (String)session.getAttribute("userName");
				log.debug("userName in searchArea is "+loggedInUser);
				if (loggedInUser == null)
				{
					return "redirect:/logout";
				}
			}
			else if(null==session)
			{
				return "redirect:/logout";
			}
			String response = null;
			String message = null;
			 
			 String clintHost = request.getRemoteHost();
			 log.debug("clintHost "+clintHost);
			 String clientIP = request.getRemoteAddr();
			 log.debug("clientIP "+clientIP);
			 updateUserForm.setName(updateUserForm.getName().toUpperCase());
			 log.debug("Branch "+updateUserForm.getBranch());
			 log.debug("Role "+updateUserForm.getRole());
			 log.debug("name in createUser is  "+updateUserForm.getName());
			 log.debug("Message is " + message);
			 String roleName = serviceImpl.getRoleName(Integer.parseInt(updateUserForm.getRole()));
			 //roleName = roleName+"_Test";
			 roleName = roleName;
			 log.debug("roleName is "+roleName);
			 String ntId = updateUserForm.getName();
			 log.debug(ntId);
			 if(null!=ntId)
			 {
				if(ntId.indexOf(" ")>0)
				{
					ntId = ntId.split(" ")[0];
					log.debug("ntId after split is "+ntId);					
				}
				UsrBase usrBase = serviceImpl.getUserBasebyUserName(ntId);
				log.debug("usrBase is "+usrBase);
				if(usrBase==null)
				{
				updateUserForm.setNtId(ntId);
				serviceImpl.createUserBranch(clientIP,loggedInUser,updateUserForm);
				UpmLdapEvents ldapEvents = new UpmLdapEvents();
				ldapEvents.AddUserToGroup(roleName,ntId);
				response="User details added successfully";
				model.addAttribute("responseMessage",response);
				}
				else
				{
					response="User already exists";
					model.addAttribute("responseMessage",response);
					/*updateUserForm.setNtId(ntId);
					updateUserForm.setUid(uid);
					response = serviceImpl.createUserProductCategory(updateUserForm);
					UpmLdapEvents ldapEvents = new UpmLdapEvents();
					ldapEvents.AddUserToGroup(roleName,ntId);
//					/response="User details added successfully";
					model.addAttribute("responseMessage",response);*/
				}
			}
			
		} catch (HibernateException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Hibernate Exception occurred:\n", ex);
			log.error("Hibernate Exception occurred:\n", ex);
			return exceptionPage;
		} catch (ArrayIndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (IndexOutOfBoundsException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Array Index is out of bound:\n", ex);
			log.error("Array Index is out of bound:\n", ex);
			return exceptionPage;
		} catch (NullPointerException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Null pointer exception:\n", ex);
			log.error("Null pointer exception:\n", ex);
			return exceptionPage;
		} catch (RuntimeException ex) {
			request.setAttribute("exception", "exception");
			log.debug("Runtime Exception occurred:\n", ex);
			log.error("Runtime Exception occurred:\n", ex);
			return exceptionPage;
		} catch (Exception ex) {
			request.setAttribute("exception", "exception");
			log.debug("An unknown Exception occurred:\n", ex);
			log.error("An unknown Exception occurred:\n", ex);
			return exceptionPage;
		}
		return "redirect:/searchAD";
	}
	
	public static String getClientIpAddr(HttpServletRequest request) {  
	    String ip = request.getHeader("X-Forwarded-For");  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("WL-Proxy-Client-IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_CLIENT_IP");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED_FOR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_FORWARDED");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("HTTP_VIA");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getHeader("REMOTE_ADDR");  
	    }  
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
	        ip = request.getRemoteAddr();  
	    }  
	    return ip;  
	}

}
