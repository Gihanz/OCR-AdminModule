

package com.boc.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.boc.dao.exceptions.DAOException;
import com.boc.form.searchAreaForm;
import com.boc.form.updateUserForm;
import com.boc.model.BranchBase;
import com.boc.model.RoleBase;
import com.boc.model.UserRoleProductCategoryBranchMapping;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.model.UsrBase;
import com.boc.response.AreaBaseRs;
import com.boc.response.AreaBranchRs;
import com.boc.response.BranchBaseRs;
import com.boc.response.BranchRs;
import com.boc.response.ProductCategoryRs;
import com.boc.response.ProvinceBaseRs;
import com.boc.response.ProvinceBranchRs;
import com.boc.response.RoleBaseRs;
import com.boc.response.UserBaseRs;
import com.boc.response.UserProductBranchRs;
import com.boc.response.UserProductCategoryBranchRs;
import com.boc.response.UserRoleProductCategoryBranchMappingRs;
import com.boc.response.UserRoleProductCategoryMappingRs;


/*
Created By SaiMadan on Nov 10, 2016
*/
@Component
public class ProductDao {
	private static Logger log =LoggerFactory.getLogger(ProductDao.class);

	@PersistenceContext(unitName="wfconfigPersistence")
	private EntityManager entityManager;

	public List<UsrBase> getUserIdList()
	{
		String maxLimit = "30";
		String query = null; 
		query = "from com.boc.model.UsrBase usrBase where usrBase.deleteflag is null or usrBase.deleteflag!=0 ";
		Query qry = entityManager.createQuery(query);
		//List<UsrBase> userIdLst = qry.setMaxResults(Integer.parseInt(maxLimit)).getResultList();
		List<UsrBase> userIdLst = qry.getResultList();
		if(null!=userIdLst)
		{
			log.debug("userIdLst size is "+userIdLst.size());
		}
		return userIdLst;
	}
	
	public List<UserBaseRs> getUserIdListByRole(String userLoggedIn)
	{
		String query = null; 
		//String rolesStr = convert(roleArray);
		//query = "from com.boc.model.UsrBase usrBase where usrBase.deleteflag is null or usrBase.deleteflag!=0 ";
		query = "Select usrBaseMain.NT_ID from USR_BASE usrBaseMain where usrBaseMain.BID = (select usrBase.BID from USR_BASE usrBase join BRANCH_BASE branchBase on usrBase.BID = branchBase.BID where usrBase.NT_ID='"+userLoggedIn+"') and (usrBaseMain.deleteFlag is null or usrBaseMain.deleteFlag!=0) and usrBaseMain.NT_ID!='"+userLoggedIn+"'";
		Query qry = entityManager.createNativeQuery(query);
		List<String> userIdLst = qry.getResultList();
		List<UserBaseRs> usrBaseRsLst = null;
		usrBaseRsLst = new ArrayList<UserBaseRs>();
		if(null!=userIdLst)
		{
			log.debug("userIdLst size is "+userIdLst.size());
			for(String ntId:userIdLst)
			{
				UserBaseRs UserBaseRs = new UserBaseRs();
				UserBaseRs.setNtId(ntId);
				usrBaseRsLst.add(UserBaseRs);
			}
		}
		return usrBaseRsLst;
	}
	
	public List<UserProductBranchRs> getUserLst(String usrName)
	{
		List<UserProductBranchRs> UserProductBranchRsLst = null;
		UserProductBranchRsLst = new ArrayList<UserProductBranchRs>();		
		UserProductBranchRs userproductBranch = null;
		String userQry = "Select DISTINCT leftProductCategory.URPCID,userBase.UID,userBase.NT_ID,userBase.FIRST_NAME,userBase.LAST_NAME,productCategoryBase.PRODUCT_CATEGORY_ID,productCategoryBase.Product_Category,branchBase.BID,branchBase.branch_code,branchBase.branch_name,roleBase.RID,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " join WFCONFIG.BRANCH_BASE branchBase on userBase.bid = branchBase.bid where userBase.NT_ID=:usrName";
		log.debug("Query is "+userQry);
		Query qry = entityManager.createNativeQuery(userQry).setParameter("usrName", usrName.toUpperCase());
		List<Object[]> lstusr  = qry.getResultList();
		if(lstusr !=null)
		{
			log.debug("lstusr size is "+lstusr.size());
			for(Object[] usr:lstusr)
			{
				userproductBranch =	new UserProductBranchRs();
				if(null!=usr[0])
				{
					userproductBranch.setUrpcid((Integer)usr[0]);
				}
				if(null!=usr[1])
				{
					userproductBranch.setUid((Integer)usr[1]);
				}
				if(null!=usr[2])
				{
					userproductBranch.setUserName((String)usr[2]);
				}
				if(null!=usr[3])
				{
					userproductBranch.setFirstName((String)usr[3]);
				}
				if(null!=usr[4])
				{
					userproductBranch.setMiddleName((String)usr[4]);
				}
				if(null!=usr[5])
				{
					userproductBranch.setProductCategoryId((Integer)usr[5]);
				}
				if(null!=usr[6])
				{
					userproductBranch.setProductCategory((String)usr[6]);
				}
				if(null!=usr[7])
				{
					userproductBranch.setBid((Integer)usr[7]);
				}
				if(null!=usr[8])
				{
					userproductBranch.setBranchCode((String)usr[8]);
				}
				if(null!=usr[9])
				{
					userproductBranch.setBranchName((String)usr[9]);
				}
				if(null!=usr[10])
				{
					userproductBranch.setRid((Integer)usr[10]);
				}
				if(null!=usr[11])
				{
					userproductBranch.setRoleName((String)usr[11]);
				}
				UserProductBranchRsLst.add(userproductBranch);	
			}
		}
		return UserProductBranchRsLst;
	}
	
	
	
	
	public List<UserProductBranchRs> getProductCategoryBranchLst(String usrName)
	{
		List<UserProductBranchRs> UserProductBranchRsLst = null;
		UserProductBranchRsLst = new ArrayList<UserProductBranchRs>();		
		UserProductBranchRs userproductBranch = null; 
		try
		{
			/*String productCategoryBranchQry = "Select DISTINCT productCategorybranch.URPCID,userBase.UID,userBase.NT_ID,productCategoryBase.PRODUCT_CATEGORY_ID,productCategoryBase.Product_Category,branchBase.BID,branchBase.branch_code,branchBase.branch_name,roleBase.RID,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left join "
					+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.USR_BASE usrBase "
					+ " on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0)";
			*/
			String productCategoryBranchQry = "Select DISTINCT productCategorybranch.URPCID,userBase.UID,userBase.NT_ID,productCategoryBase.PRODUCT_CATEGORY_ID,productCategoryBase.Product_Category,branchBase.BID,branchBase.branch_code,branchBase.branch_name,roleBase.RID,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left join "
					+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.USR_BASE usrBase "
					+ " on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0)";
			
			
			log.debug("Query is "+productCategoryBranchQry);
			Query qry = entityManager.createNativeQuery(productCategoryBranchQry).setParameter("usrName", usrName.toUpperCase());
			List<Object[]> lstproductCategoryBranch  = qry.getResultList();
			if(lstproductCategoryBranch !=null)
			{
				for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
				{
					userproductBranch =	new UserProductBranchRs();
					if(null !=productCategoryBranchArray[0])
					{
						userproductBranch.setUrpcid((Integer)productCategoryBranchArray[0]);
						//productCategoryLst.add((String)productCategoryBranchArray[0]);							
					}
					if(null !=productCategoryBranchArray[1])
					{
						userproductBranch.setUid((Integer)productCategoryBranchArray[1]);
						//branchCodeLst.add((String)productCategoryBranchArray[1]);
					}
					if(null !=productCategoryBranchArray[2])
					{
						userproductBranch.setUserName((String)productCategoryBranchArray[2]);
						//branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[3])
					{
						userproductBranch.setProductCategoryId((Integer)productCategoryBranchArray[3]);
						//roleNameLst.add((String)productCategoryBranchArray[3]);
					}
					if(null !=productCategoryBranchArray[4])
					{
						userproductBranch.setProductCategory((String)productCategoryBranchArray[4]);
						//branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[5])
					{
						userproductBranch.setBid((Integer)productCategoryBranchArray[5]);
						//roleNameLst.add((String)productCategoryBranchArray[3]);
					}
					if(null !=productCategoryBranchArray[6])
					{
						userproductBranch.setBranchCode((String)productCategoryBranchArray[6]);
						//branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[7])
					{
						userproductBranch.setBranchName((String)productCategoryBranchArray[7]);
						//branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					if(null !=productCategoryBranchArray[8])
					{
						userproductBranch.setRid((Integer)productCategoryBranchArray[8]);
						//roleNameLst.add((String)productCategoryBranchArray[3]);
					}
					if(null !=productCategoryBranchArray[9])
					{
						userproductBranch.setRoleName((String)productCategoryBranchArray[9]);
						//branchNameLst.add((String)productCategoryBranchArray[2]);
					}
					UserProductBranchRsLst.add(userproductBranch);	
				}
							
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return UserProductBranchRsLst;
	}
	
	public List<Integer> getProductCategoryIdByName(Set<String> productCategorySet)
	{
		List<String> productCategoryLst = new ArrayList<String>();
		productCategoryLst.addAll(productCategorySet);
		String[] productCategoryArray =  new String[productCategoryLst.size()];
		productCategoryArray = productCategoryLst.toArray(productCategoryArray);
		
		String productCategoryStr = convert(productCategoryArray);
		log.debug("productCategoryStr is "+productCategoryStr);
		String productCategoryQry = "Select productCategoryId from com.boc.model.ProductCategoryBase where productCategory in ("+productCategoryStr+")";
		log.debug("productCategoryQry is "+productCategoryQry);
		Query productCategory = entityManager.createQuery(productCategoryQry);//.setParameter("productCategoryList", productCategoryStr);
		List<Integer>  productCategoryIdList = productCategory.getResultList();
		return productCategoryIdList;
	}
	
	public List<Integer> getRoleIdByName(Set<String> roleSet)
	{
		List<String> roleLst = null; 
		roleLst =	new ArrayList<String>();
		roleLst.addAll(roleSet);
		String[] roleArray =  new String[roleLst.size()];
		roleArray = roleLst.toArray(roleArray);
		
		String roleStr = convert(roleArray);
		log.debug("roleStr is "+roleStr);
		String roleQry = "Select rid from com.boc.model.RoleBase  where roleName in ("+roleStr+")";
		log.debug("roleQry is "+roleQry);
		Query query = entityManager.createQuery(roleQry);//.setParameter("productCategoryList", productCategoryStr);
		List<Integer>  roleIdList = query.getResultList();
		return roleIdList;
	}
	
	public List<Integer> getAreaIdByName(Set<String> areaSet)
	{
		List<String> areaLst = null; 
		areaLst = new ArrayList<String>();
		areaLst.addAll(areaSet);
		String[] areaArray =  new String[areaLst.size()];
		areaArray = areaLst.toArray(areaArray);
		String areaStr = convert(areaArray);
		log.debug("areaStr is "+areaStr);
		String areaQry = "Select aid from com.boc.model.AreaBase  where areaName in ("+areaStr+")";
		log.debug("areaQry is "+areaQry);
		Query query = entityManager.createQuery(areaQry);//.setParameter("productCategoryList", productCategoryStr);
		List<Integer>  areaIdList = query.getResultList();
		return areaIdList;
	}
	public List<Integer> getProvinceIdByName(Set<String> provinceSet)
	{
		List<String> provinceLst = null; 
		provinceLst = new ArrayList<String>();
		provinceLst.addAll(provinceSet);
		String[] provinceArray =  new String[provinceLst.size()];
		provinceArray = provinceLst.toArray(provinceArray);
		String provinceStr = convert(provinceArray);
		log.debug("provinceStr is "+provinceStr);
		String provinceQry = "Select pid from com.boc.model.ProvinceBase  where provinceName in ("+provinceStr+")";
		log.debug("provinceQry is "+provinceQry);
		Query query = entityManager.createQuery(provinceQry);//.setParameter("productCategoryList", productCategoryStr);
		List<Integer>  provinceIdList = query.getResultList();
		return provinceIdList;
	}
	
	public List<Integer> getRLCProvinceIdByName(Set<String> provinceSet)
	{
		List<String> provinceLst = null; 
		provinceLst = new ArrayList<String>();
		provinceLst.addAll(provinceSet);
		String[] provinceArray =  new String[provinceLst.size()];
		provinceArray = provinceLst.toArray(provinceArray);
		String provinceStr = convert(provinceArray);
		log.debug("provinceStr is "+provinceStr);
		String provinceQry = "Select rlcId from com.boc.model.RlcBase  where rlcName in ("+provinceStr+")";
		log.debug("provinceQry is "+provinceQry);
		Query query = entityManager.createQuery(provinceQry);//.setParameter("productCategoryList", productCategoryStr);
		List<Integer>  provinceIdList = query.getResultList();
		return provinceIdList;
	}
	
	
	public UserProductCategoryBranchRs getUserProductCategory(String usrName, int bid)
	{
		Set<String> productCategorySet = null;
		productCategorySet = new HashSet<String>();
		Set<String> roleSet = null;
		roleSet = new HashSet<String>();
		List qryResults;
		UserProductCategoryBranchRs userProductCategoryBranchRs = null;
		try
		{
			
/*			String usrBranchQry = "select branchBase.branch_name,roleBase.role_name,productCategoryBase.Product_Category,usrBase.FIRST_NAME,usrBase.LAST_NAME from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
					+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping join USR_BASE usrBase on userRoleProductCategoryBranchMapping.BID=usrBase.BID  where userRoleProductCategoryBranchMapping.bid="+bid
			+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)";
*/			
			String usrBranchQry = "select branchBase.branch_name,roleBase.role_name,productCategoryBase.Product_Category,usrBase.FIRST_NAME,usrBase.LAST_NAME from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
					+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)";

					Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", usrName.toUpperCase());
					log.debug("usrBranchQry is "+usrBranchQry);
					List<Object[]> lstUsrBranch  = usrQry.getResultList();
					if(lstUsrBranch !=null)
					{
						userProductCategoryBranchRs = new UserProductCategoryBranchRs();
						for(Object[] usrBranch:lstUsrBranch)
						{
							if(null!=usrBranch[0])
							{
								userProductCategoryBranchRs.setBranchName((String) usrBranch[0]);
							}
							if(null!=usrBranch[1])
							{
								//userProductCategoryBranchRs.setRoleName((String) usrBranch[1]);
								roleSet.add((String) usrBranch[1]);
							}
							if(null!=usrBranch[2])
							{
								productCategorySet.add((String) usrBranch[2]);
							}
							if(null!=usrBranch[3])
							{
								userProductCategoryBranchRs.setFirstName((String) usrBranch[3]);
							}
							if(null!=usrBranch[4])
							{
								userProductCategoryBranchRs.setMiddleName((String) usrBranch[4]);
							}
						}
						userProductCategoryBranchRs.setNtId(usrName);
					userProductCategoryBranchRs.setProductCategory(productCategorySet);
					userProductCategoryBranchRs.setRoleSet(roleSet);
					}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return userProductCategoryBranchRs;
	}
	
	public List<UserRoleProductCategoryMappingRs> getUserProductCategoryList(String usrName)
	{
		Set<String> productCategorySet = null;
		productCategorySet = new HashSet<String>();
		List qryResults;
		List<UserRoleProductCategoryMappingRs> userProductCategoryMappingListRs = null;
		userProductCategoryMappingListRs = new ArrayList<UserRoleProductCategoryMappingRs>();
		UserRoleProductCategoryMappingRs userProductCategoryMappingRs = null;
		try
		{
			String usrBranchQry = "Select leftProductCategory.URPCID from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory "
					//+ "join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ "join  WFCONFIG.USR_BASE usrBase on leftProductCategory.uid = usrBase.uid join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid  where usrBase.nt_id=:usrName";
					log.debug("Query is "+usrBranchQry);
					Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", usrName.toUpperCase());
					List<Integer> lstUsrBranch  = usrQry.getResultList();
					if(lstUsrBranch !=null)
					{
						for(Integer usrBranch:lstUsrBranch)
						{
							userProductCategoryMappingRs = new UserRoleProductCategoryMappingRs();
							if(null!=usrBranch)
							{
								userProductCategoryMappingRs.setUrpcid((Integer) usrBranch);
							}
							userProductCategoryMappingListRs.add(userProductCategoryMappingRs);
						}
					}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return userProductCategoryMappingListRs;
	}
	
	public List<Integer> getUserProductCategoryIdList(String usrName)
	{
		List<Integer> lstUsrBranch = null;
		try
		{
			String usrBranchQry = "Select leftProductCategory.URPCID from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory "
					+ "join  WFCONFIG.USR_BASE usrBase on leftProductCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and (leftProductCategory.DELETEFLAG is null or leftProductCategory.DELETEFLAG!=0)";
					log.debug("Query is "+usrBranchQry);
					Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", usrName.toUpperCase());
					lstUsrBranch  = usrQry.getResultList();
					/*if(lstUsrBranch !=null)
					{
						for(Integer usrBranch:lstUsrBranch)
						{
							userProductCategoryMappingRs = new UserRoleProductCategoryMappingRs();
							if(null!=usrBranch)
							{
								userProductCategoryMappingRs.setUrpcid((Integer) usrBranch);
							}
							userProductCategoryMappingListRs.add(userProductCategoryMappingRs);
						}
					}*/
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return lstUsrBranch;
	}
	
	
	public BranchRs getUserBranchList(String ntId)
	{
		BranchRs branchRs = null;
		try
		{
			List<BranchRs> branchLst = null;
			branchLst = new ArrayList<BranchRs>();
			Set<String> branchNameSet = null;
			branchNameSet = new HashSet<String>();
			Set<String> roleNameSet = null;
			roleNameSet = new HashSet<String>();
			Set<String> productCategorySet = null;
			productCategorySet = new HashSet<String>();
			Set<Integer> urpcidSet = null;
			urpcidSet = new HashSet<Integer>();
			String areaBranchQry = "Select userBase.NT_ID,branchBase.branch_Name,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left "
					+ " join WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid "
					+ " join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id"
					+ "  where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory"
					+ " join WFCONFIG.USR_BASE usrBase on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0) group by userBase.NT_ID,branchBase.branch_Name,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid";
			log.debug("Query is "+areaBranchQry);
			Query qry = entityManager.createNativeQuery(areaBranchQry).setParameter("usrName", ntId.toUpperCase());
			List<Object[]> lstBranch  = qry.getResultList();
			if(lstBranch !=null)
			{
				branchRs =	new BranchRs();
				for(Object[] branchArray:lstBranch)
				{
					if(null!=branchArray[0])
					{
						branchRs.setNtId(ntId);
					}					
					if(null!=branchArray[1])
					{
						branchNameSet.add((String)branchArray[1]);
					}
					if(null!=branchArray[2])
					{
						roleNameSet.add((String)branchArray[2]);
					}
					if(null!=branchArray[3])
					{
						productCategorySet.add((String)branchArray[3]);
					}
					if(null!=branchArray[4])
					{
						urpcidSet.add((Integer)branchArray[4]);
					}
				}
				branchRs.setBranchName(branchNameSet);
				branchRs.setRoleName(roleNameSet);
				branchRs.setProductCategory(productCategorySet);
				branchRs.setUrpcid(urpcidSet);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return branchRs;
	}
	
	public int getProductCategoryBranchCount(String ntId,String productCategory)
	{
		int count = 0;
		try
		{
			String productCategoryBranch = " select count(*) from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch  "
					+" where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on productCategory.Product_Category_Id=productCategoryBase.Product_Category_Id"
					+ " join WFCONFIG.USR_BASE usrBase on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and productCategoryBase.PRODUCT_CATEGORY=:productCategory and (productCategory.deleteflag is null or productCategory.deleteflag!=0)) ";
			Query qry = entityManager.createNativeQuery(productCategoryBranch).setParameter("usrName", ntId.toUpperCase()).setParameter("productCategory", productCategory);
			List<Integer> lstproductCategory  = qry.getResultList();
			if(null!=lstproductCategory)
			{
				count =lstproductCategory.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return count;
	}
	
	
	public AreaBranchRs getUserAreaBranchList(String ntId)
	{
		AreaBranchRs areaBranchRs = null;
		try
		{
			List<AreaBranchRs> areaBranchLst = null;
			areaBranchLst = new ArrayList<AreaBranchRs>();
			Set<String> areaNameSet = null;
			Set<String> branchNameSet = null;
			areaNameSet = new HashSet<String>();
			branchNameSet = new HashSet<String>();
			Set<String> roleNameSet = null;
			roleNameSet = new HashSet<String>();
			Set<String> productCategorySet = null;
			productCategorySet = new HashSet<String>();
			Set<Integer> urpcidSet = null;
			urpcidSet = new HashSet<Integer>();
			String areaBranchQry = "Select userBase.NT_ID,branchBase.branch_Name ,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,userBase.FIRST_NAME,userBase.LAST_NAME from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left "
					+ " join WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid "
					+ " join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id"
					+ " join WFCONFIG.AREA_BRANCH_MAPPING areaBranchMapping on areaBranchMapping.bid=branchBase.bid join WFCONFIG.AREA_BASE areaBase on areaBranchMapping.aid=areaBase.aid where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory"
					+ " join WFCONFIG.USR_BASE usrBase on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and (productCategory.deleteflag is null or productCategory.deleteflag!=0) and productCategorybranch.bid!=usrBase.bid) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0) group by userBase.NT_ID,branchBase.branch_Name,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,userBase.FIRST_NAME,userBase.LAST_NAME";
			log.debug("Query is "+areaBranchQry);
			Query qry = entityManager.createNativeQuery(areaBranchQry).setParameter("usrName", ntId.toUpperCase());
			List<Object[]> lstAreaBranch  = qry.getResultList();
			if(lstAreaBranch !=null)
			{
				areaBranchRs =	new AreaBranchRs();
				for(Object[] areaBranchArray:lstAreaBranch)
				{
					if(null!=areaBranchArray[0])
					{
						areaBranchRs.setNtId(ntId);
					}					
					if(null!=areaBranchArray[1])
					{
						branchNameSet.add((String)areaBranchArray[1]);
					}
					if(null!=areaBranchArray[2])
					{
						areaNameSet.add((String)areaBranchArray[2]);
					}
					if(null!=areaBranchArray[3])
					{
						roleNameSet.add((String)areaBranchArray[3]);
					}
					if(null!=areaBranchArray[4])
					{
						productCategorySet.add((String)areaBranchArray[4]);
					}
					if(null!=areaBranchArray[5])
					{
						urpcidSet.add((Integer)areaBranchArray[5]);
					}
					if(null!=areaBranchArray[6])
					{
						areaBranchRs.setFirstName((String)areaBranchArray[6]);
					}
					if(null!=areaBranchArray[7])
					{
						areaBranchRs.setMiddleName((String)areaBranchArray[7]);
					}
				}
				
				/*String usrBranchQry = "Select branchBase.branch_name from WFCONFIG.BRANCH_BASE branchBase join WFCONFIG.USR_BASE usrBase on branchBase.bid=usrBase.bid where usrBase.nt_id=:usrName ";
				log.debug("Query is "+usrBranchQry);
				Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", ntId.toUpperCase());
				List<String> lstUsrBranch  = usrQry.getResultList();
				if(lstUsrBranch !=null)
				{
					for(String usrBranch:lstUsrBranch)
					{
											
						if(null!=usrBranch)
						{
							branchNameSet.add((String) usrBranch);
						}
					}
				}*/
				
				areaBranchRs.setAreaName(areaNameSet);
				areaBranchRs.setBranchName(branchNameSet);
				areaBranchRs.setRoleName(roleNameSet);
				areaBranchRs.setProductCategory(productCategorySet);
				areaBranchRs.setUrpcid(urpcidSet);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return areaBranchRs;
	}
	
	public ProvinceBranchRs getUserProvinceBranchList(String ntId)
	{
		ProvinceBranchRs provinceBranchRs = null;
		try
		{
			List<ProvinceBranchRs> provinceBranchLst = null;
			provinceBranchLst = new ArrayList<ProvinceBranchRs>();
			Set<String> provinceNameSet = null;
			Set<String> areaNameSet = null;
			Set<String> branchNameSet = null;
			provinceNameSet = new HashSet<String>();
			areaNameSet = new HashSet<String>();
			branchNameSet = new HashSet<String>();
			Set<String> roleNameSet = null;
			roleNameSet = new HashSet<String>();
			Set<String> productCategorySet = null;
			productCategorySet = new HashSet<String>();
			Set<Integer> urpcidSet = null;
			urpcidSet = new HashSet<Integer>();
			String areaBranchQry = "Select userBase.NT_ID,branchBase.branch_Name,provinceBase.PROVINCE_NAME,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,userBase.FIRST_NAME,userBase.LAST_NAME from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left "
					+ " join WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid "
					+ " join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id"
					+ " join WFCONFIG.AREA_BRANCH_MAPPING areaBranchMapping on areaBranchMapping.bid=branchBase.bid join WFCONFIG.AREA_BASE areaBase on areaBranchMapping.aid=areaBase.aid "
					+ " join WFCONFIG.PROVINCE_BRANCH_MAPPING provinceBranchMapping on provinceBranchMapping.bid=branchBase.bid join WFCONFIG.PROVINCE_BASE provinceBase on provinceBranchMapping.pid=provinceBase.pid where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory"
					+ " join WFCONFIG.USR_BASE usrBase on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and (productCategory.deleteflag is null or productCategory.deleteflag!=0) and productCategorybranch.bid!=usrBase.bid) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0) group by userBase.NT_ID,branchBase.branch_Name,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,provinceBase.PROVINCE_NAME,userBase.FIRST_NAME,userBase.LAST_NAME";
			log.debug("Query is "+areaBranchQry);
			Query qry = entityManager.createNativeQuery(areaBranchQry).setParameter("usrName", ntId.toUpperCase());
			List<Object[]> lstAreaBranch  = qry.getResultList();
			if(lstAreaBranch !=null)
			{
				provinceBranchRs =	new ProvinceBranchRs();
				for(Object[] provinceBranchArray:lstAreaBranch)
				{
					if(null!=provinceBranchArray[0])
					{
						provinceBranchRs.setNtId(ntId);
					}					
					if(null!=provinceBranchArray[1])
					{
						branchNameSet.add((String)provinceBranchArray[1]);
					}
					if(null!=provinceBranchArray[2])
					{
						provinceNameSet.add((String)provinceBranchArray[2]);
					}
					if(null!=provinceBranchArray[3])
					{
						areaNameSet.add((String)provinceBranchArray[3]);
					}
					if(null!=provinceBranchArray[4])
					{
						roleNameSet.add((String)provinceBranchArray[4]);
					}
					if(null!=provinceBranchArray[5])
					{
						productCategorySet.add((String)provinceBranchArray[5]);
					}
					if(null!=provinceBranchArray[6])
					{
						urpcidSet.add((Integer)provinceBranchArray[6]);
					}
					if(null!=provinceBranchArray[7])
					{
						provinceBranchRs.setFirstName((String)provinceBranchArray[7]);
					}
					if(null!=provinceBranchArray[8])
					{
						provinceBranchRs.setMiddleName((String)provinceBranchArray[8]);
					}
				}
				
				/*String usrBranchQry = "Select branchBase.branch_name from WFCONFIG.BRANCH_BASE branchBase join WFCONFIG.USR_BASE usrBase on branchBase.bid=usrBase.bid where usrBase.nt_id=:usrName ";
				log.debug("Query is "+usrBranchQry);
				Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", ntId.toUpperCase());
				List<String> lstUsrBranch  = usrQry.getResultList();
				if(lstUsrBranch !=null)
				{
					for(String usrBranch:lstUsrBranch)
					{
											
						if(null!=usrBranch)
						{
							branchNameSet.add((String) usrBranch);
						}
					}
				}*/
				
				provinceBranchRs.setProvinceName(provinceNameSet);
				provinceBranchRs.setAreaName(areaNameSet);
				provinceBranchRs.setBranchName(branchNameSet);
				provinceBranchRs.setRoleName(roleNameSet);
				provinceBranchRs.setProductCategory(productCategorySet);
				provinceBranchRs.setUrpcid(urpcidSet);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return provinceBranchRs;
	}
	
	public ProvinceBranchRs getUserRLCProvinceBranchList(String ntId)
	{
		ProvinceBranchRs provinceBranchRs = null;
		try
		{
			List<ProvinceBranchRs> provinceBranchLst = null;
			provinceBranchLst = new ArrayList<ProvinceBranchRs>();
			Set<String> provinceNameSet = null;
			Set<String> areaNameSet = null;
			Set<String> branchNameSet = null;
			provinceNameSet = new HashSet<String>();
			areaNameSet = new HashSet<String>();
			branchNameSet = new HashSet<String>();
			Set<String> roleNameSet = null;
			roleNameSet = new HashSet<String>();
			Set<String> productCategorySet = null;
			productCategorySet = new HashSet<String>();
			Set<Integer> urpcidSet = null;
			urpcidSet = new HashSet<Integer>();
			String areaBranchQry = "Select userBase.NT_ID,branchBase.branch_Name,rlcBase.RLC_NAME,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,userBase.FIRST_NAME,userBase.LAST_NAME from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left "
					+ " join WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid "
					+ " join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id"
					+ " join WFCONFIG.AREA_BRANCH_MAPPING areaBranchMapping on areaBranchMapping.bid=branchBase.bid join WFCONFIG.AREA_BASE areaBase on areaBranchMapping.aid=areaBase.aid "
					+ " join WFCONFIG.RLC_BRANCH_MAPPING rlcBranchMapping on rlcBranchMapping.bid=branchBase.bid join WFCONFIG.RLC_BASE rlcBase on rlcBranchMapping.RLC_ID=rlcBase.RLC_ID where productCategorybranch.urpcid in (select productCategory.urpcid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING productCategory"
					+ " join WFCONFIG.USR_BASE usrBase on productCategory.uid = usrBase.uid where usrBase.nt_id=:usrName and (productCategory.deleteflag is null or productCategory.deleteflag!=0) and productCategorybranch.bid!=usrBase.bid) and (productCategorybranch.deleteflag is null or productCategorybranch.deleteflag!=0) group by userBase.NT_ID,branchBase.branch_Name,areaBase.AREA_NAME,roleBase.ROLE_NAME,productCategoryBase.PRODUCT_CATEGORY,leftProductCategory.urpcid,rlcBase.RLC_NAME,userBase.FIRST_NAME,userBase.LAST_NAME";
			log.debug("Query is "+areaBranchQry);
			Query qry = entityManager.createNativeQuery(areaBranchQry).setParameter("usrName", ntId.toUpperCase());
			List<Object[]> lstAreaBranch  = qry.getResultList();
			if(lstAreaBranch !=null)
			{
				provinceBranchRs =	new ProvinceBranchRs();
				for(Object[] provinceBranchArray:lstAreaBranch)
				{
					if(null!=provinceBranchArray[0])
					{
						provinceBranchRs.setNtId(ntId);
					}					
					if(null!=provinceBranchArray[1])
					{
						branchNameSet.add((String)provinceBranchArray[1]);
					}
					if(null!=provinceBranchArray[2])
					{
						provinceNameSet.add((String)provinceBranchArray[2]);
					}
					if(null!=provinceBranchArray[3])
					{
						areaNameSet.add((String)provinceBranchArray[3]);
					}
					if(null!=provinceBranchArray[4])
					{
						roleNameSet.add((String)provinceBranchArray[4]);
					}
					if(null!=provinceBranchArray[5])
					{
						productCategorySet.add((String)provinceBranchArray[5]);
					}
					if(null!=provinceBranchArray[6])
					{
						urpcidSet.add((Integer)provinceBranchArray[6]);
					}
					if(null!=provinceBranchArray[7])
					{
						provinceBranchRs.setFirstName((String)provinceBranchArray[7]);
					}
					if(null!=provinceBranchArray[8])
					{
						provinceBranchRs.setMiddleName((String)provinceBranchArray[8]);
					}
				}
				
				/*String usrBranchQry = "Select branchBase.branch_name from WFCONFIG.BRANCH_BASE branchBase join WFCONFIG.USR_BASE usrBase on branchBase.bid=usrBase.bid where usrBase.nt_id=:usrName ";
				log.debug("Query is "+usrBranchQry);
				Query usrQry = entityManager.createNativeQuery(usrBranchQry).setParameter("usrName", ntId.toUpperCase());
				List<String> lstUsrBranch  = usrQry.getResultList();
				if(lstUsrBranch !=null)
				{
					for(String usrBranch:lstUsrBranch)
					{
											
						if(null!=usrBranch)
						{
							branchNameSet.add((String) usrBranch);
						}
					}
				}*/
				
				provinceBranchRs.setProvinceName(provinceNameSet);
				provinceBranchRs.setAreaName(areaNameSet);
				provinceBranchRs.setBranchName(branchNameSet);
				provinceBranchRs.setRoleName(roleNameSet);
				provinceBranchRs.setProductCategory(productCategorySet);
				provinceBranchRs.setUrpcid(urpcidSet);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return provinceBranchRs;
	}
	
	public UserProductBranchRs getProductCategoryBranch(String urpcid)
	{
		UserProductBranchRs userproductBranch = null; 
		try
		{
		String productCategoryBranchQry = "Select leftProductCategory.URPCID,userBase.UID,userBase.NT_ID,productCategoryBase.PRODUCT_CATEGORY_ID,productCategoryBase.Product_Category,branchBase.BID,branchBase.branch_code,branchBase.branch_name,roleBase.RID,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING productCategorybranch join WFCONFIG.BRANCH_BASE branchBase on productCategorybranch.bid = branchBase.bid left join "
				+ " WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory on productCategorybranch.urpcid=leftProductCategory.urpcid join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " where productCategorybranch.urpcid ="+urpcid;
				
		log.debug("Query is "+productCategoryBranchQry);
		Query qry = entityManager.createNativeQuery(productCategoryBranchQry);
		List<Object[]> lstproductCategoryBranch  = qry.getResultList();
		if(lstproductCategoryBranch !=null)
		{
			log.debug("userIdLst size is "+lstproductCategoryBranch.size());
			userproductBranch =	new UserProductBranchRs();
			for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
			{
				if(null !=productCategoryBranchArray[0])
				{
					userproductBranch.setUrpcid((Integer)productCategoryBranchArray[0]);
					//productCategoryLst.add((String)productCategoryBranchArray[0]);							
				}
				if(null !=productCategoryBranchArray[1])
				{
					userproductBranch.setUid((Integer)productCategoryBranchArray[1]);
					//branchCodeLst.add((String)productCategoryBranchArray[1]);
				}
				if(null !=productCategoryBranchArray[2])
				{
					userproductBranch.setUserName((String)productCategoryBranchArray[2]);
					//branchNameLst.add((String)productCategoryBranchArray[2]);
				}
				if(null !=productCategoryBranchArray[3])
				{
					userproductBranch.setProductCategoryId((Integer)productCategoryBranchArray[3]);
					//roleNameLst.add((String)productCategoryBranchArray[3]);
				}
				if(null !=productCategoryBranchArray[4])
				{
					userproductBranch.setProductCategory((String)productCategoryBranchArray[4]);
					//branchNameLst.add((String)productCategoryBranchArray[2]);
				}
				if(null !=productCategoryBranchArray[5])
				{
					userproductBranch.setBid((Integer)productCategoryBranchArray[5]);
					//roleNameLst.add((String)productCategoryBranchArray[3]);
				}
				if(null !=productCategoryBranchArray[6])
				{
					userproductBranch.setBranchCode((String)productCategoryBranchArray[6]);
					//branchNameLst.add((String)productCategoryBranchArray[2]);
				}
				if(null !=productCategoryBranchArray[7])
				{
					userproductBranch.setBranchName((String)productCategoryBranchArray[7]);
					//branchNameLst.add((String)productCategoryBranchArray[2]);
				}
				if(null !=productCategoryBranchArray[8])
				{
					userproductBranch.setRid((Integer)productCategoryBranchArray[8]);
					//roleNameLst.add((String)productCategoryBranchArray[3]);
				}
				if(null !=productCategoryBranchArray[9])
				{
					userproductBranch.setRoleName((String)productCategoryBranchArray[9]);
					//branchNameLst.add((String)productCategoryBranchArray[2]);
				}
			}
		}
	}
	catch(HibernateException hex)
	{
		hex.printStackTrace();
		log.error("Error ", hex.fillInStackTrace());
		log.error(hex.getMessage());
		throw new DAOException("er.db.getProductCategoryByBranch",hex);
	}
		return userproductBranch;
	}
	
	public UserProductBranchRs getUserLstByUrpcId(String urpcid)
	{
		UserProductBranchRs userproductBranch = null; 
		try
		{
		String productCategoryBranchQry = "Select DISTINCT leftProductCategory.URPCID,userBase.UID,userBase.NT_ID,userBase.FIRST_NAME,userBase.LAST_NAME,productCategoryBase.PRODUCT_CATEGORY_ID,productCategoryBase.Product_Category,branchBase.BID,branchBase.branch_code,branchBase.branch_name,roleBase.RID,roleBase.role_name from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING leftProductCategory join WFCONFIG.USR_BASE userBase on leftProductCategory.uid=userBase.uid join WFCONFIG.ROLE_BASE roleBase on leftProductCategory.rid=roleBase.rid join WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on leftProductCategory.Product_Category_Id=productCategoryBase.Product_Category_Id "
					+ " join WFCONFIG.BRANCH_BASE branchBase on userBase.bid = branchBase.bid where leftProductCategory.URPCID="+urpcid;
				
		log.debug("Query is "+productCategoryBranchQry);
		Query qry = entityManager.createNativeQuery(productCategoryBranchQry);
		List<Object[]> lstproductCategoryBranch  = qry.getResultList();
		if(lstproductCategoryBranch !=null)
		{
			log.debug("userIdLst size is "+lstproductCategoryBranch.size());
			userproductBranch =	new UserProductBranchRs();
			for(Object[] productCategoryBranchArray:lstproductCategoryBranch)
			{
				if(null !=productCategoryBranchArray[0])
				{
					userproductBranch.setUrpcid((Integer)productCategoryBranchArray[0]);
				}
				if(null !=productCategoryBranchArray[1])
				{
					userproductBranch.setUid((Integer)productCategoryBranchArray[1]);
				}
				if(null !=productCategoryBranchArray[2])
				{
					userproductBranch.setUserName((String)productCategoryBranchArray[2]);
				}
				if(null !=productCategoryBranchArray[3])
				{
					userproductBranch.setFirstName((String)productCategoryBranchArray[3]);
				}
				if(null !=productCategoryBranchArray[4])
				{
					userproductBranch.setMiddleName((String)productCategoryBranchArray[4]);
				}
				if(null !=productCategoryBranchArray[5])
				{
					userproductBranch.setProductCategoryId((Integer)productCategoryBranchArray[5]);
				}
				if(null !=productCategoryBranchArray[6])
				{
					userproductBranch.setProductCategory((String)productCategoryBranchArray[6]);
				}
				if(null !=productCategoryBranchArray[7])
				{
					userproductBranch.setBid((Integer)productCategoryBranchArray[7]);
				}
				if(null !=productCategoryBranchArray[8])
				{
					userproductBranch.setBranchCode((String)productCategoryBranchArray[8]);
				}
				if(null !=productCategoryBranchArray[9])
				{
					userproductBranch.setBranchName((String)productCategoryBranchArray[9]);
				}
				if(null !=productCategoryBranchArray[10])
				{
					userproductBranch.setRid((Integer)productCategoryBranchArray[10]);
				}
				if(null !=productCategoryBranchArray[11])
				{
					userproductBranch.setRoleName((String)productCategoryBranchArray[11]);
				}
			}
		}
	}
	catch(HibernateException hex)
	{
		hex.printStackTrace();
		log.error("Error ", hex.fillInStackTrace());
		log.error(hex.getMessage());
		throw new DAOException("er.db.getProductCategoryByBranch",hex);
	}
		return userproductBranch;
	}
	
	public List<BranchBaseRs> getAllBranches()
	{
		List<BranchBaseRs> branchBaseRsLst = null;
		branchBaseRsLst = new ArrayList<BranchBaseRs>();
		String branchQry = "Select distinct bid,branchCode,branchName from BranchBase order by branchName";
		log.debug("Query is "+branchQry);
		Query qry = entityManager.createQuery(branchQry);
		List<Object[]> lstBranch  = qry.getResultList();
		if(lstBranch !=null)
		{
			//log.debug("branch List is "+lstBranch.size());
			
			for(Object[] branch:lstBranch)
			{
				BranchBaseRs branchBase = new BranchBaseRs();
				if(null !=branch[0])
				{
					branchBase.setBid((Integer)branch[0]);
				}
				if(null !=branch[1])
				{
					branchBase.setBranchCode((String)branch[1]);
				}
				if(null !=branch[2])
				{
					branchBase.setBranchName((String)branch[2]);
				}
				branchBaseRsLst.add(branchBase);
			}
			
		}
		return branchBaseRsLst;
	}
	
	public List<RoleBaseRs> getAllRoles()
	{
		List<RoleBaseRs> roleBaseRsLst = null;
		roleBaseRsLst = new ArrayList<RoleBaseRs>();
		String roleQry = "Select rid,roleName,roleLabel from com.boc.model.RoleBase";
		log.debug("Query is "+roleQry);
		Query qry = entityManager.createQuery(roleQry);
		List<Object[]> lstRoles  = qry.getResultList();
		if(lstRoles !=null)
		{
			//log.debug("Roles List is "+lstRoles.size());
			for(Object[] role:lstRoles)
			{
				RoleBaseRs roleBase = new RoleBaseRs();
				if(null !=role[0])
				{
					roleBase.setRid((Integer)role[0]);
				}
				if(null !=role[1])
				{
					roleBase.setRoleName((String)role[1]);
				}
				if(null !=role[2])
				{
					roleBase.setRoleLabel((String)role[2]);
				}
			
				roleBaseRsLst.add(roleBase);
			}
		}
		return roleBaseRsLst;
	}
	
	public List<ProductCategoryRs> getAllProductCategory()
	{
		List<ProductCategoryRs> productCategoryRsLst = null;
		productCategoryRsLst = new ArrayList<ProductCategoryRs>();
		String productCategoryQry = "Select productCategoryId,productCategory from com.boc.model.ProductCategoryBase";
		log.debug("Query is "+productCategoryQry);
		Query qry = entityManager.createQuery(productCategoryQry);
		List<Object[]> lstProductCategory  = qry.getResultList();
		if(lstProductCategory !=null)
		{
			//log.debug("Product Category List is "+lstProductCategory.size());
			for(Object[] productCategory:lstProductCategory)
			{
				ProductCategoryRs productCategoryRs = new ProductCategoryRs();
				if(null !=productCategory[0])
				{
					productCategoryRs.setProductCategoryId((Integer)productCategory[0]);
				}
				if(null !=productCategory[1])
				{
					productCategoryRs.setProductCategory((String)productCategory[1]);
				}
				
			
				productCategoryRsLst.add(productCategoryRs);
			}
		}
		return productCategoryRsLst;
	}
	
	public List<AreaBaseRs> getAllArea()
	{
		List<AreaBaseRs> areaBaseRsLst = null;
		areaBaseRsLst = new ArrayList<AreaBaseRs>();
		String areaQry = "Select aid,areaCode,areaName from com.boc.model.AreaBase";
		log.debug("Query is "+areaQry);
		Query qry = entityManager.createQuery(areaQry);
		List<Object[]> lstArea  = qry.getResultList();
		if(lstArea !=null)
		{
			//log.debug("Area List is "+lstArea.size());
			for(Object[] area:lstArea)
			{
				AreaBaseRs areaBaseRs = new AreaBaseRs();
				if(null !=area[0])
				{
					areaBaseRs.setAid((Integer)area[0]);
				}
				if(null !=area[1])
				{
					areaBaseRs.setAreaCode((String)area[1]);
				}
				if(null !=area[2])
				{
					areaBaseRs.setAreaName((String)area[2]);
				}
				areaBaseRsLst.add(areaBaseRs);
			}
		}
		return areaBaseRsLst;
	}
	
	public List<ProvinceBaseRs> getAllProvince()
	{
		List<ProvinceBaseRs> provinceBaseRsLst = null;
		provinceBaseRsLst = new ArrayList<ProvinceBaseRs>();
		String provinceQry = "Select pid,provinceCode,provinceName from com.boc.model.ProvinceBase";
		log.debug("Query is "+provinceQry);
		Query qry = entityManager.createQuery(provinceQry);
		List<Object[]> lstProvince  = qry.getResultList();
		if(lstProvince !=null)
		{
			//log.debug("lstProvince List is "+lstProvince.size());
			for(Object[] province:lstProvince)
			{
				ProvinceBaseRs provinceBaseRs = new ProvinceBaseRs();
				if(null !=province[0])
				{
					provinceBaseRs.setPid((Integer)province[0]);
				}
				if(null !=province[1])
				{
					provinceBaseRs.setProvinceCode((String)province[1]);
				}
				if(null !=province[2])
				{
					provinceBaseRs.setProvinceName((String)province[2]);
				}
				provinceBaseRsLst.add(provinceBaseRs);
			}
		}
		return provinceBaseRsLst;
	}
	
	public List<ProvinceBaseRs> getAllRLCProvince()
	{
		List<ProvinceBaseRs> provinceBaseRsLst = null;
		provinceBaseRsLst = new ArrayList<ProvinceBaseRs>();
		String provinceQry = "Select rlcId,rlcCode,rlcName from com.boc.model.RlcBase";
		log.debug("Query is "+provinceQry);
		Query qry = entityManager.createQuery(provinceQry);
		List<Object[]> lstProvince  = qry.getResultList();
		if(lstProvince !=null)
		{
			//log.debug("lstProvince List is "+lstProvince.size());
			for(Object[] province:lstProvince)
			{
				ProvinceBaseRs provinceBaseRs = new ProvinceBaseRs();
				if(null !=province[0])
				{
					provinceBaseRs.setPid((Integer)province[0]);
				}
				if(null !=province[1])
				{
					provinceBaseRs.setProvinceCode((String)province[1]);
				}
				if(null !=province[2])
				{
					provinceBaseRs.setProvinceName((String)province[2]);
				}
				provinceBaseRsLst.add(provinceBaseRs);
			}
		}
		return provinceBaseRsLst;
	}
	
	
	public List<Integer> getBranchIdByArea(Set<String> areaNameSet)
	{
		List<Integer> lstBid = null;
		List<String> areaNameLst = new ArrayList<String>();
		log.debug("areaNameSet is "+areaNameSet);
		areaNameLst.addAll(areaNameSet);
		String[] areaNameArray =  new String[areaNameLst.size()];
		areaNameArray = areaNameLst.toArray(areaNameArray);
		
		String areaNameStr = convert(areaNameArray);
		log.debug("areaNameArray size is "+areaNameArray);
		try
		{
			String areaIdQry = "Select areaBranchMapping.branchBase.bid from com.boc.model.AreaBranchMapping areaBranchMapping where areaBranchMapping.areaBase.areaName in("+areaNameStr+")";
			log.debug("Query is "+areaIdQry);
			Query qry = entityManager.createQuery(areaIdQry);//.set (areaNameArray, areaNameLst);
			lstBid  = qry.getResultList();
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return lstBid;
	}
	
	public List<Integer> getBranchIdByProvince(Set<String> provinceNameSet)
	{
		List<Integer> lstBid = null;
		List<String> provinceNameLst = new ArrayList<String>();
		log.debug("provinceNameSet is "+provinceNameSet);
		provinceNameLst.addAll(provinceNameSet);
		String[] provinceNameArray =  new String[provinceNameLst.size()];
		provinceNameArray = provinceNameLst.toArray(provinceNameArray);
		
		String provinceNameStr = convert(provinceNameArray);
		log.debug("provinceNameArray size is "+provinceNameArray);
		try
		{
			String provinceIdQry = "Select provinceBranchMapping.branchBase.bid from com.boc.model.ProvinceBranchMapping provinceBranchMapping where provinceBranchMapping.provinceBase.provinceName in("+provinceNameStr+")";
			log.debug("Query is "+provinceIdQry);
			Query qry = entityManager.createQuery(provinceIdQry);//.set (areaNameArray, areaNameLst);
			lstBid  = qry.getResultList();
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return lstBid;
	}
	
	public List<Integer> getRLCBranchIdByProvince(Set<String> provinceNameSet)
	{
		List<Integer> lstBid = null;
		List<String> provinceNameLst = new ArrayList<String>();
		log.debug("provinceNameSet is "+provinceNameSet);
		provinceNameLst.addAll(provinceNameSet);
		String[] provinceNameArray =  new String[provinceNameLst.size()];
		provinceNameArray = provinceNameLst.toArray(provinceNameArray);
		
		String provinceNameStr = convert(provinceNameArray);
		log.debug("provinceNameArray size is "+provinceNameArray);
		try
		{
			String provinceIdQry = "Select rlcBranchMapping.branchBase.bid from com.boc.model.RlcBranchMapping rlcBranchMapping where rlcBranchMapping.rlcBase.rlcName in("+provinceNameStr+")";
			log.debug("Query is "+provinceIdQry);
			Query qry = entityManager.createQuery(provinceIdQry);//.set (areaNameArray, areaNameLst);
			lstBid  = qry.getResultList();
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return lstBid;
	}
	
	public String getRole(int roleId)
	{
		RoleBase roleBase = entityManager.find(RoleBase.class, roleId);
		return roleBase.getRoleName();
	}
	
	public String getBranch(int bid)
	{
		BranchBase branchBase = entityManager.find(BranchBase.class, bid);
		return branchBase.getBranchName();
	}
	public RoleBase getRolebasebyRoleName(String roleName)
	{
		RoleBase roleBase=null;
		try
		{
			log.debug("roleName is "+roleName);
			String roleQry = "Select roleBase from com.boc.model.RoleBase roleBase where roleBase.roleName='"+roleName+"'";
			log.debug("Query is "+roleQry);
			Query qry = entityManager.createQuery(roleQry);
			List<RoleBase> qryResults  = qry.getResultList();
			if(qryResults.size()>0)
			{
				roleBase=qryResults.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return roleBase;
	}
	
	
	public UsrBase getUserBasebyUserName(String userName)
	{
		UsrBase usrBase=null;
		try
		{
			log.debug("userName is "+userName);
			String userQry = "Select usrBase from com.boc.model.UsrBase usrBase where usrBase.ntId='"+userName.toUpperCase()+"' and (usrBase.deleteflag is null or usrBase.deleteflag!=0)";
			log.debug("Query is "+userQry);
			Query qry = entityManager.createQuery(userQry);
			List<UsrBase> qryResults  = qry.getResultList();
			if(qryResults.size()>0)
			{
				usrBase=qryResults.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return usrBase;
	}
	
	
	public List<Integer> isExistsUserProductCategoryBranch(updateUserForm updateUserForm)
	{

		int count = 0;
		List<Integer> qryResults =null;
		String qryStr = "select urpcid from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+updateUserForm.getExistingBid()
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"		
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr).setParameter("usrName", updateUserForm.getNtId().toUpperCase());
			qryResults  = qry.getResultList();
			/*if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}*/
			log.debug("qryResults size is "+qryResults.size());
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return qryResults;
	}
	
	public List<Integer> isExistsUserProductCategoryBranch(updateUserForm updateUserForm,Integer bid)
	{

		int count = 0;
		List<Integer> qryResults =null;
		String qryStr = "select urpcid from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+bid
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"		
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr).setParameter("usrName", updateUserForm.getNtId().toUpperCase());
			qryResults  = qry.getResultList();
			if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return qryResults;
	}
	
	public int isExistsUserProductCategoryRole(updateUserForm updateUserForm, Integer productCategoryId )
	{
		int count = 0;
		List<Integer> qryResults =null;
		
		String qryStr = "select count(*) from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+updateUserForm.getExistingBid()
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"
		+ " and userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+productCategoryId 
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		
		/*String qryStr = "select count(*) from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
			+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+Integer.parseInt(productCategoryId)
			+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";*/
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr).setParameter("usrName", updateUserForm.getNtId().toUpperCase());
			qryResults  = qry.getResultList();
			if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return count;
	}
	
	public List<Integer> isExistsUserByRole(updateUserForm updateUserForm,Integer role )
	{
		int count = 0;
		List<Integer> qryResults =null;
		
		String qryStr = "select urpcId from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+updateUserForm.getExistingBid()
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"
		+ " and userRoleProductCategoryMapping.rid="+role
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		
		/*String qryStr = "select count(*) from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
			+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+Integer.parseInt(productCategoryId)
			+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";*/
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr).setParameter("usrName", updateUserForm.getNtId().toUpperCase());
			qryResults  = qry.getResultList();
			/*if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}*/
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return qryResults;
	}
	
	public List<Integer> isExistsUserProductCategoryRole(updateUserForm updateUserForm, Integer productCategoryId,Integer role )
	{
		int count = 0;
		List<Integer> qryResults =null;
		
		String qryStr = "select urpcId from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+updateUserForm.getExistingBid()
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID=:usrName and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"
		+ " and userRoleProductCategoryMapping.rid="+role+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+productCategoryId 
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		
		/*String qryStr = "select count(*) from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
			+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+Integer.parseInt(productCategoryId)
			+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";*/
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr).setParameter("usrName", updateUserForm.getNtId().toUpperCase());
			qryResults  = qry.getResultList();
			/*if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}*/
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return qryResults;
	}
	
	public int isExistsUserProductCategory(updateUserForm updateUserForm, Integer productCategoryId )
	{
		int count = 0;
		List<Integer> qryResults =null;
		
		String qryStr = "select userRoleProductCategoryMapping.URPCID from USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join USR_BASE usrBase on userRoleProductCategoryMapping.uid=usrBase.uid  "
				+ " join WFCONFIG.ROLE_BASE roleBase on userRoleProductCategoryMapping.rid=roleBase.rid join  WFCONFIG.PRODUCT_CATEGORY_BASE productCategoryBase on userRoleProductCategoryMapping.Product_Category_Id=productCategoryBase.Product_Category_Id "
				+ " join WFCONFIG.BRANCH_BASE branchBase on usrBase.bid =branchBase.bid where userRoleProductCategoryMapping.URPCID in (select urpcid from USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.bid="+updateUserForm.getExistingBid()
		+ " and (userRoleProductCategoryBranchMapping.DELETEFLAG is null or userRoleProductCategoryBranchMapping.DELETEFLAG!=0)) and usrBase.NT_ID='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.DELETEFLAG is null or userRoleProductCategoryMapping.DELETEFLAG!=0)"
		+ "  and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+productCategoryId 
		+ " and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";
		/*
		
		String qryStr = "select userRoleProductCategoryMapping.urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
			+ " where  userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+Integer.parseInt(productCategoryId)
			+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and  and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0)";*/
		try
		{
			log.debug("Query is "+qryStr);
			Query qry = entityManager.createNativeQuery(qryStr);
			qryResults  = qry.getResultList();
			if((qryResults.size()>0))
			{
				count = qryResults.get(0);
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
		return count;
	}
	
	public void updateUserProductCategoryRole(int urpcid,updateUserForm updateUserForm)
	{
		UserRoleProductCategoryMapping userRoleProductCategoryMapping = entityManager.find(UserRoleProductCategoryMapping.class, urpcid);
		userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
		userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
		entityManager.merge(userRoleProductCategoryMapping);
	}
	
	
	
	public void createUser(updateUserForm updateUserForm)
	{
		List<Object[]> qryResults =null;
		try
		{
		String qryStr = "select * from wfconfig.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING userRoleProductCategoryBranchMapping where userRoleProductCategoryBranchMapping.URPCID in (select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+Integer.parseInt(updateUserForm.getRole())+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+Integer.parseInt(updateUserForm.getProductCategory())
				+" and usrBase.nt_id='"+updateUserForm.getName()+"') and userRoleProductCategoryBranchMapping.BID="+Integer.parseInt(updateUserForm.getBranch())+" and (userRoleProductCategoryBranchMapping.deleteflag is null or userRoleProductCategoryBranchMapping.deleteflag!=0)";
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))
			{
			
			UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
			userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
			userRoleProductCategoryMapping.setUid((updateUserForm.getUid()));
			userRoleProductCategoryMapping.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			entityManager.persist(userRoleProductCategoryMapping);
			entityManager.flush();
			int urpcid = userRoleProductCategoryMapping.getUrpcid();
			//UserRoleProductCategoryBranchMapping userRoleProductCategoryBranchMapping = new UserRoleProductCategoryBranchMapping();
			//userRoleProductCategoryBranchMapping.set
			/*String insertQry = "insert into USER_ROLE_PRODUCT_CATEGORY_MAPPING(UID,RID,PRODUCT_CATEGORY_ID,EDITFLAG) values(?,?,?,0)";
			log.debug("insertQry is "+insertQry);
			Query qryInsert = entityManager.createNativeQuery(insertQry).setParameter(1, updateUserForm.getUid()).setParameter(2, updateUserForm.getRole()).setParameter(3, updateUserForm.getProductCategory());
			int urpcid = qryInsert.executeUpdate();*/
			log.debug("urpcid is "+urpcid);
			
			String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
			log.debug("insertQry1 is "+insertQry1);
			Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
			int urpcbid = qry1.executeUpdate();
			log.debug("urpcbid is "+urpcbid);
			
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
	}
	public List<Integer> getBranchIdByBranch(Set<String> branchNameSet)
	{
		List<Integer> lstBid = null;
		List<String> branchNameLst = new ArrayList<String>();
		branchNameLst.addAll(branchNameSet);
		String[] branchNameArray =  new String[branchNameLst.size()];
		branchNameArray = branchNameLst.toArray(branchNameArray);
		
		log.debug("branchNameLst is "+branchNameLst.size());
		String branchNameStr = convert(branchNameArray);
		log.debug("branchNameStr is "+branchNameStr);
		log.debug("branchNameArray size is "+branchNameArray);
		try
		{
			String provinceIdQry = "Select branchBase.bid from com.boc.model.BranchBase branchBase where branchBase.branchName in("+branchNameStr+")";
			log.debug("Query is "+provinceIdQry);
			Query qry = entityManager.createQuery(provinceIdQry);//.set (areaNameArray, areaNameLst);
			lstBid  = qry.getResultList();
			
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return lstBid;
	}
	
	public void updateProductCategoryBranch(UserProductBranchRs userProductBranchRs)
	{
		UserRoleProductCategoryMapping userRoleProductCategoryMapping = entityManager.find(UserRoleProductCategoryMapping.class, userProductBranchRs.getUrpcid());
		log.debug("updating UserRoleProdut "+userProductBranchRs.getRid());
		userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
		userRoleProductCategoryMapping.setProductCategoryId(userProductBranchRs.getProductCategoryId());
		userRoleProductCategoryMapping.setRid(userProductBranchRs.getRid());
		entityManager.merge(userRoleProductCategoryMapping);
		log.debug("Updated Successfully");
	}
	
	
	public void updateUserBranch(UserProductBranchRs userProductBranchRs)
	{
		UserRoleProductCategoryMapping userRoleProductCategoryMapping = entityManager.find(UserRoleProductCategoryMapping.class, userProductBranchRs.getUrpcid());
		log.debug("updating UserRoleProdut "+userProductBranchRs.getRid());
		userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
		userRoleProductCategoryMapping.setProductCategoryId(userProductBranchRs.getProductCategoryId());
		userRoleProductCategoryMapping.setRid(userProductBranchRs.getRid());
		entityManager.merge(userRoleProductCategoryMapping);
		int uid = userRoleProductCategoryMapping.getUid();
		
		BranchBase branchBase = entityManager.find(BranchBase.class, userProductBranchRs.getBid());
		
		UsrBase usrBase = entityManager.find(UsrBase.class, uid);
		log.debug("updating usrBase "+usrBase.getNtId());
		usrBase.setEditflag(Short.valueOf("0"));
		usrBase.setBranchBase(branchBase);
		entityManager.merge(usrBase);
		log.debug("Updated Successfully");
		
	}
	
	public void updateUser(updateUserForm updateUserForm)
	{
		BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
		
		UsrBase usrBase = entityManager.find(UsrBase.class, updateUserForm.getUid());
		log.debug("updating usrBase "+usrBase.getNtId());
		usrBase.setEditflag(Short.valueOf("0"));
		usrBase.setBranchBase(branchBase);
		entityManager.merge(usrBase);
		log.debug("Updated Successfully");
	}
	
	public void createUserBranchByProductCategory(updateUserForm updateUserForm,List<Integer>productCategoryIdLst,Integer roleId)
	{
		/*List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))*/
			{
			//UsrBase usrBase = entityManager.find(UsrBase.class, updateUserForm.getUid());
			
			//BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			//log.debug("branchBase is "+branchBase.getBid());
			/*UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setLastName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();*/
			
			//Set<String> productCategorySet = updateUserForm.getProductCategorySet();
			if(null!=productCategoryIdLst)
			{
				Iterator iter = productCategoryIdLst.iterator();
				while(iter.hasNext())
				{
					Integer productCategory = (Integer)iter.next();
				//}
				//for(String productCategory:productCategorySet)
				//{
					UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
					userRoleProductCategoryMapping.setRid((roleId));
					userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
					userRoleProductCategoryMapping.setProductCategoryId(productCategory);
					userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryMapping);
					entityManager.flush();
					log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
					int urpcid = userRoleProductCategoryMapping.getUrpcid();
					
					String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry1 is "+insertQry1);
					Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
					int urpcbid = qry1.executeUpdate();
					log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
					log.debug("urpcbid is "+urpcbid);
				}
			}
		}
	}
	
	public void createUserByProductCategoryRole(updateUserForm updateUserForm,List<Integer>productCategoryIdLst,Integer roleId)
	{
		/*List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))*/
			{
			//UsrBase usrBase = entityManager.find(UsrBase.class, updateUserForm.getUid());
			
			//BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			//log.debug("branchBase is "+branchBase.getBid());
			/*UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setLastName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();*/
			
			//Set<String> productCategorySet = updateUserForm.getProductCategorySet();
			if(null!=productCategoryIdLst)
			{
				Iterator iter = productCategoryIdLst.iterator();
				while(iter.hasNext())
				{
					Integer productCategory = (Integer)iter.next();
				//}
				//for(String productCategory:productCategorySet)
				//{
					UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
					userRoleProductCategoryMapping.setRid((roleId));
					userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
					userRoleProductCategoryMapping.setProductCategoryId(productCategory);
					userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryMapping);
					entityManager.flush();
					log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
					//int urpcid = userRoleProductCategoryMapping.getUrpcid();
					
					/*String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry1 is "+insertQry1);
					Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
					int urpcbid = qry1.executeUpdate();
					log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
					log.debug("urpcbid is "+urpcbid);*/
				}
			}
		}
	}
	
	public void createUserBranchByProductCategory(updateUserForm updateUserForm,Integer productCategoryId,Integer roleId)
	{
		/*List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))*/
			{
			//UsrBase usrBase = entityManager.find(UsrBase.class, updateUserForm.getUid());
			
			//BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			//log.debug("branchBase is "+branchBase.getBid());
			/*UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setLastName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();*/
			
			Set<String> productCategorySet = updateUserForm.getProductCategorySet();
			if(null!=productCategorySet)
			{
				Iterator iter = productCategorySet.iterator();
				//while(iter.hasNext())
				{
					//Integer productCategory = (Integer)iter.next();
				//}
				//for(String productCategory:productCategorySet)
				//{
					UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
					userRoleProductCategoryMapping.setRid((roleId));
					userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
					userRoleProductCategoryMapping.setProductCategoryId(productCategoryId);
					userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryMapping);
					entityManager.flush();
					log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
					int urpcid = userRoleProductCategoryMapping.getUrpcid();
					
					String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry1 is "+insertQry1);
					Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
					int urpcbid = qry1.executeUpdate();
					log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
					log.debug("urpcbid is "+urpcbid);
				}
			}
		}
	}
	
	public void createUserBranchByProductCategory(updateUserForm updateUserForm,List<Integer> productCategoryIdLst)
	{
		/*List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))*/
			{
			//UsrBase usrBase = entityManager.find(UsrBase.class, updateUserForm.getUid());
			
			//BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			//log.debug("branchBase is "+branchBase.getBid());
			/*UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setLastName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();*/
			
			//Set<String> productCategorySet = updateUserForm.getProductCategorySet();
			if(null!=productCategoryIdLst)
			{
				Iterator iter = productCategoryIdLst.iterator();
				while(iter.hasNext())
				{
					Integer productCategory = (Integer)iter.next();
				//}
				//for(String productCategory:productCategorySet)
				//{
					UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
					userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
					userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
					userRoleProductCategoryMapping.setProductCategoryId(productCategory);
					userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryMapping);
					entityManager.flush();
					log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
					int urpcid = userRoleProductCategoryMapping.getUrpcid();
					
					String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry1 is "+insertQry1);
					Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
					int urpcbid = qry1.executeUpdate();
					log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
					log.debug("urpcbid is "+urpcbid);
				}
			}
		}
	}
	
	public void createUserBranch(String ipAddress,String loggedInUser,updateUserForm updateUserForm)
	{
		/*List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))*/
			{
			BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			log.debug("branchBase is "+branchBase);
			UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setLastName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();
			
			UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
			userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
			userRoleProductCategoryMapping.setUid(uid);
			userRoleProductCategoryMapping.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
			entityManager.persist(userRoleProductCategoryMapping);
			entityManager.flush();
			log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
			int urpcid = userRoleProductCategoryMapping.getUrpcid();
			
			String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
			log.debug("insertQry1 is "+insertQry1);
			Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
			int urpcbid = qry1.executeUpdate();
			log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
			log.debug("urpcbid is "+urpcbid);
			log.info(loggedInUser+"		"+updateUserForm.getNtId()+" Created with role "+updateUserForm.getRole()+" for Product Category "+updateUserForm.getProductCategory()+" at branch "+updateUserForm.getBranch()+" from ipAddress "+ipAddress);
			}
		
	}
	
	public String updateUserProductCategoryBranch(updateUserForm updateUserForm,String productCategory)
	{
		
		String responseMessage = null;
		List<Integer> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and (userRoleProductCategoryMapping.deleteflag is null or userRoleProductCategoryMapping.deleteflag!=0) ";
		
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))
		{
			UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
			userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
			userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
			userRoleProductCategoryMapping.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
			entityManager.persist(userRoleProductCategoryMapping);
			entityManager.flush();
			log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
			int urpcid = userRoleProductCategoryMapping.getUrpcid();
			
			String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
			log.debug("insertQry1 is "+insertQry1);
			Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
			int urpcbid = qry1.executeUpdate();
			log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
			log.debug("urpcbid is "+urpcbid);
			responseMessage="User details added successfully";
		}
		else
		{
			int urpcid = qryResults.get(0);
			
			
			String deleteBranchQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcid+") and bid="+updateUserForm.getExistingBid();
			log.debug("Query is "+deleteBranchQry);
			Query qryBranch = entityManager.createNativeQuery(deleteBranchQry);
			qryBranch.executeUpdate();
			
			String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
			log.debug("insertQry1 is "+insertQry1);
			Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
			int urpcbid = qry1.executeUpdate();
			log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
			log.debug("urpcbid is "+urpcbid);
			responseMessage="User details added successfully";
		}
		
		return null;
	}
	
	public String createUserProductCategory(updateUserForm updateUserForm)
	{
		String responseMessage = null;
		List<Object[]> qryResults =null;
		String qryStr = "select urpcid from wfconfig.USER_ROLE_PRODUCT_CATEGORY_MAPPING userRoleProductCategoryMapping join wfconfig.usr_base usrBase on userRoleProductCategoryMapping.uid=usrBase.uid "
				+ " where  userRoleProductCategoryMapping.rid="+updateUserForm.getRole()+" and userRoleProductCategoryMapping.PRODUCT_CATEGORY_ID="+updateUserForm.getProductCategory()
				+" and usrBase.nt_id='"+updateUserForm.getNtId().toUpperCase()+"' and usrBase.BID="+updateUserForm.getBranch();
		log.debug("Query is "+qryStr);
		Query qry = entityManager.createNativeQuery(qryStr);
		qryResults  = qry.getResultList();
		if(!(qryResults.size()>0))
			{
			BranchBase branchBase = entityManager.find(BranchBase.class, Integer.parseInt(updateUserForm.getBranch()));
			log.debug("branchBase is "+branchBase);
			/*UsrBase usrBase = new UsrBase();
			usrBase.setBranchBase(branchBase);
			usrBase.setNtId(updateUserForm.getNtId().toUpperCase());
			usrBase.setFirstName(updateUserForm.getFirstName());
			usrBase.setMiddleName(updateUserForm.getMiddleName());
			usrBase.setIsActive('1');
			usrBase.setEditflag(Short.valueOf("0"));
			entityManager.persist(usrBase);
			entityManager.flush();
			log.debug("User Added Successfully "+usrBase.getUid());
			int uid = usrBase.getUid();*/
			
			UserRoleProductCategoryMapping userRoleProductCategoryMapping = new UserRoleProductCategoryMapping();
			userRoleProductCategoryMapping.setRid(Integer.parseInt(updateUserForm.getRole()));
			userRoleProductCategoryMapping.setUid(updateUserForm.getUid());
			userRoleProductCategoryMapping.setProductCategoryId(Integer.parseInt(updateUserForm.getProductCategory()));
			userRoleProductCategoryMapping.setEditflag(Short.valueOf("0"));
			entityManager.persist(userRoleProductCategoryMapping);
			entityManager.flush();
			log.debug("UserRoleProductCategoryMapping Added Successfully "+userRoleProductCategoryMapping.getUrpcid());
			int urpcid = userRoleProductCategoryMapping.getUrpcid();
			
			/*String insertQry1 = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
			log.debug("insertQry1 is "+insertQry1);
			Query qry1 = entityManager.createNativeQuery(insertQry1).setParameter(1, urpcid).setParameter(2, Integer.parseInt(updateUserForm.getBranch()));
			int urpcbid = qry1.executeUpdate();
			log.debug("UserRoleProductCategoryMappingBranch Added Successfully");
			log.debug("urpcbid is "+urpcbid);*/
			responseMessage="User details added successfully";
			}
		else
		{
			log.debug("User already exists for the same role and branch ");
			responseMessage="User already exists for the same role and branch";
		}
		return responseMessage;
	}
	
	public void deleteProductCategoryBranchByArea(List<Integer> urpcIdLst,List bidLst)
	{
		try
		{
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
			Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
			urpcIdArray = urpcIdLst.toArray(urpcIdArray);
			String urpcIdStr= convertFromInteger(urpcIdArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			String deleteQry = "update USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") ";
			log.debug("Query is "+deleteQry);
			Query qry = entityManager.createNativeQuery(deleteQry);//.set (areaNameArray, areaNameLst);
			qry.executeUpdate();	
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
	}
	
	public void deleteProductCategoryByUrpcid(List<Integer> urpcIdLst)
	{
		try
		{
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				log.debug("Deletion Started");
				Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
				urpcIdArray = urpcIdLst.toArray(urpcIdArray);
				String urpcIdStr= convertFromInteger(urpcIdArray);
				log.debug("urpcIdStr is "+urpcIdStr);
				String deleteQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") ";
				log.debug("Query is "+deleteQry);
				Query qry = entityManager.createNativeQuery(deleteQry);//.set (areaNameArray, areaNameLst);
				qry.executeUpdate();
				String deleteBranchQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+")";
				log.debug("Query is "+deleteBranchQry);
				Query qryBranch = entityManager.createNativeQuery(deleteBranchQry);
				qryBranch.executeUpdate();
				log.debug("Deletion Completed");
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
	}
	public void deleteProductCategoryBranchByUrpcid(List<Integer> urpcIdLst)
	{
		try
		{
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				log.debug("Deletion Started");
				Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
				urpcIdArray = urpcIdLst.toArray(urpcIdArray);
				String urpcIdStr= convertFromInteger(urpcIdArray);
				log.debug("urpcIdStr is "+urpcIdStr);
				/*String deleteQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") ";
				log.debug("Query is "+deleteQry);
				Query qry = entityManager.createNativeQuery(deleteQry);//.set (areaNameArray, areaNameLst);
				qry.executeUpdate();*/
				String deleteBranchQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+")";
				log.debug("Query is "+deleteBranchQry);
				Query qryBranch = entityManager.createNativeQuery(deleteBranchQry);
				qryBranch.executeUpdate();
				log.debug("Deletion Completed");
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void deleteUser(String ntId)
	{
		UsrBase usrBase = getUserBasebyUserName(ntId);
		usrBase.setDeleteflag(Short.valueOf("0"));
	}
	
	public void deleteProductCategoryByUrpcid(List<Integer> urpcIdLst,int bid)
	{
		try
		{
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
				urpcIdArray = urpcIdLst.toArray(urpcIdArray);
				String urpcIdStr= convertFromInteger(urpcIdArray);
				log.debug("urpcIdStr is "+urpcIdStr);
				String deleteQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") ";
				log.debug("Query is "+deleteQry);
				Query qry = entityManager.createNativeQuery(deleteQry);//.set (areaNameArray, areaNameLst);
				qry.executeUpdate();
				String deleteBranchQry = "update WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") and bid="+bid;
				log.debug("Query is "+deleteBranchQry);
				Query qryBranch = entityManager.createNativeQuery(deleteBranchQry);
				qryBranch.executeUpdate();
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
	}
	
	
	public void updateProductCategoryBranch(Integer urpcid, int existingBid,int bid)
	{
		
		try
		{
			String selectQry = "Select urpcbid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING where URPCID ="+urpcid+" and bid="+existingBid;
			log.debug("Query is "+selectQry);
			Query qry = entityManager.createNativeQuery(selectQry);//.set (areaNameArray, areaNameLst);
			List<Integer> urpcbidLst = qry.getResultList();
			if(null!= urpcbidLst && urpcbidLst.size()>0)
			{
				for(Integer urpcbid:urpcbidLst)
				{
					com.boc.model.UserRoleProductCategoryBranchMapping userRoleProductCategoryBranchMapping =  entityManager.find(com.boc.model.UserRoleProductCategoryBranchMapping.class, urpcbid);
					if(null!=userRoleProductCategoryBranchMapping)
					{
						//if(null!=userProductBranchRs && userProductBranchRs.getBid())
						userRoleProductCategoryBranchMapping.setBid(bid);
						userRoleProductCategoryBranchMapping.setEditflag(Short.valueOf("0"));
						entityManager.merge(userRoleProductCategoryBranchMapping);
						entityManager.flush();
					}
				}
			}
					
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
	}
	
	/*public void updateProductCategoryBranchList(List<Integer> urpcidList, int existingBid,int bid)
	{
		
		try
		{
			String selectQry = "Select urpcbid from WFCONFIG.USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING where URPCID ="+urpcid+" and bid="+existingBid;
			log.debug("Query is "+selectQry);
			Query qry = entityManager.createNativeQuery(selectQry);//.set (areaNameArray, areaNameLst);
			List<Integer> urpcbidLst = qry.getResultList();
			if(null!= urpcbidLst && urpcbidLst.size()>0)
			{
				for(Integer urpcbid:urpcbidLst)
				{
					com.boc.model.UserRoleProductCategoryBranchMapping userRoleProductCategoryBranchMapping =  entityManager.find(com.boc.model.UserRoleProductCategoryBranchMapping.class, urpcbid);
					if(null!=userRoleProductCategoryBranchMapping)
					{
						//if(null!=userProductBranchRs && userProductBranchRs.getBid())
						userRoleProductCategoryBranchMapping.setBid(bid);
						userRoleProductCategoryBranchMapping.setEditflag(Short.valueOf("0"));
						entityManager.merge(userRoleProductCategoryBranchMapping);
						entityManager.flush();
					}
					else
					{
						UserRoleProductCategoryBranchMapping addUserRoleProductCategoryBranchMapping = new UserRoleProductCategoryBranchMapping();
						addUserRoleProductCategoryBranchMapping.setUrpcid(urpcid);
						addUserRoleProductCategoryBranchMapping.setBid(bid);
						addUserRoleProductCategoryBranchMapping.setEditflag(Short.valueOf("0"));
						entityManager.persist(addUserRoleProductCategoryBranchMapping);
						entityManager.flush();
					}
				}
			}
					
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		
	}*/
	
	public void insertProductCategoryBranchByArea(List<Integer> urpcIdLst,List<Integer> bidLst)
	{
		if(null!= urpcIdLst && urpcIdLst.size()>0)
		{
			Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
			urpcIdArray = urpcIdLst.toArray(urpcIdArray);
			String urpcIdStr= convertFromInteger(urpcIdArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			
			Integer[] bidArray =  new Integer[bidLst.size()];
			bidArray = bidLst.toArray(bidArray);
			String bidStr= convertFromInteger(bidArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			for(Integer urpcid:urpcIdLst)
			{
				for(Integer bid:bidLst)
				{
					String insertQry = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry is "+insertQry);
					Query qry = entityManager.createNativeQuery(insertQry).setParameter(1, urpcid).setParameter(2, bid);
					qry.executeUpdate();	
					/*UserRoleProductCategoryBranchMapping userRoleProductCategoryBranchMapping = new UserRoleProductCategoryBranchMapping();
					//userRoleProductCategoryBranchMapping.setUrpcbid(nextval);
					userRoleProductCategoryBranchMapping.setUrpcid(urpcid);
					userRoleProductCategoryBranchMapping.setBid(bid);
					userRoleProductCategoryBranchMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryBranchMapping);*/
				}
			}
		}
	}
	
	public void insertProductCategoryBranchByUser(List<Integer> urpcIdLst,List<Integer> bidLst)
	{
		if(null!=urpcIdLst && urpcIdLst.size()>0)
		{
			Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
			urpcIdArray = urpcIdLst.toArray(urpcIdArray);
			String urpcIdStr= convertFromInteger(urpcIdArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			
			Integer[] bidArray =  new Integer[bidLst.size()];
			bidArray = bidLst.toArray(bidArray);
			String bidStr= convertFromInteger(bidArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			for(Integer urpcid:urpcIdLst)
			{
				for(Integer bid:bidLst)
				{
					String insertQry = "insert into USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING(URPCID,BID,EDITFLAG) values(?,?,0)";
					log.debug("insertQry is "+insertQry);
					Query qry = entityManager.createNativeQuery(insertQry).setParameter(1, urpcid).setParameter(2, bid);
					qry.executeUpdate();	
					/*UserRoleProductCategoryBranchMapping userRoleProductCategoryBranchMapping = new UserRoleProductCategoryBranchMapping();
					//userRoleProductCategoryBranchMapping.setUrpcbid(nextval);
					userRoleProductCategoryBranchMapping.setUrpcid(urpcid);
					userRoleProductCategoryBranchMapping.setBid(bid);
					userRoleProductCategoryBranchMapping.setEditflag(Short.valueOf("0"));
					entityManager.persist(userRoleProductCategoryBranchMapping);*/
				}
			}
		}
	}
	
	public void deleteProductCategory(List<Integer> urpcIdLst,List bidLst)
	{
		try
		{
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
			Integer[] urpcIdArray =  new Integer[urpcIdLst.size()];
			urpcIdArray = urpcIdLst.toArray(urpcIdArray);
			String urpcIdStr= convertFromInteger(urpcIdArray);
			log.debug("urpcIdStr is "+urpcIdStr);
			String deleteQry = "update USER_ROLE_PRODUCT_CATEGORY_BRANCH_MAPPING set DELETEFLAG=0 where URPCID in ("+urpcIdStr+") ";
			log.debug("Query is "+deleteQry);
			Query qry = entityManager.createNativeQuery(deleteQry);//.set (areaNameArray, areaNameLst);
			qry.executeUpdate();		
			}
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
	}
	
	
	public static String convert(String[] name) { 
	    StringBuilder sb = new StringBuilder();
	    for (String st : name) { 
	        sb.append('\'').append(st).append('\'').append(',');
	    }
	    if (name.length != 0) sb.deleteCharAt(sb.length()-1);
	    return sb.toString();
	}
	
	public static String convertFromInteger(Integer[] name) { 
	    StringBuilder sb = new StringBuilder();
	    for (Integer st : name) { 
	        sb.append(st).append(',');
	    }
	    if (name.length != 0) sb.deleteCharAt(sb.length()-1);
	    return sb.toString();
	}
	
	/*
	public List<com.boc.model.UsrBase> getUserList(String maxLimit)
	{
		List<com.boc.model.UsrBase> usrList = null;
		String qryUserStr = null;
		log.debug("executing usrBase query");
		qryUserStr = "select usrBase from com.boc.model.UsrBase usrBase where usrBase.editflag=0";
		//System.out.println("Getting EntityManager "+ AbstractEntity.entityManager());
		//EntityManager entityManager = entityManagerFactory.createEntityManager();
		Query queryUsr = entityManager.createQuery(qryUserStr);
		usrList = queryUsr.setMaxResults(Integer.parseInt(maxLimit)).getResultList();
		entityManager.flush();
		return usrList;
	}
	
	public List<com.boc.model.RoleBase> getRoleList(String maxLimit)
	{
		List<com.boc.model.RoleBase> roleList = null;
		String qryRoleStr = null;
		qryRoleStr = "select roleBase from com.boc.model.RoleBase roleBase where roleBase.editflag=0";
		Query queryRole = entityManager.createQuery(qryRoleStr);
		roleList = queryRole.setMaxResults(Integer.parseInt(maxLimit)).getResultList();
		entityManager.flush();
		return roleList;
	}
	
	public List<com.boc.model.BranchBase> getBranchBaseList(String maxLimit)
	{
		List<com.boc.model.BranchBase> branchBaseList = null;
		String qryBranchStr = null;
		qryBranchStr = "select r from "+BranchBase.class.getSimpleName()+" r";// where branchBase.editflag=0";
		Query queryBranch = entityManager.createQuery(qryBranchStr);
		branchBaseList = queryBranch.getResultList();
		log.debug(" branchBaseList size is "+branchBaseList.size());
		entityManager.flush();
		return branchBaseList;
	}
	
	public void updateBranchBaseList(List<BranchBaseRs> branchBaseLstRs)
	{
		for(BranchBaseRs branchBaseRs:branchBaseLstRs)
		{
			com.boc.model.BranchBase branchBase =  entityManager.find(com.boc.model.BranchBase.class, branchBaseRs.getBid());
			if(null!=branchBase)
			{
				branchBase.setEditflag(Short.valueOf("1"));
				entityManager.merge(branchBase);
				entityManager.flush();
			}
		}
	}
	
	
	
	public void updateRoleBaseList(List<RoleBaseRs> roleBaseLstRs)
	{
		for(RoleBaseRs roleBaseRs:roleBaseLstRs)
		{
			com.boc.model.RoleBase  roleBase = entityManager.find(com.boc.model.RoleBase.class, roleBaseRs.getRid());
			if(null!=roleBase)
			{
				roleBase.setEditflag(Short.valueOf("1"));
				entityManager.merge(roleBase);
				entityManager.flush();
			}
		}
	}
	
	public void updateUsrBaseList(List<UserBaseRs> usrBaseLstRs)
	{
		for(UserBaseRs usrBaseRs:usrBaseLstRs)
		{
			com.boc.model.UsrBase  usrBase = entityManager.find(com.boc.model.UsrBase.class, usrBaseRs.getUid());
			if(null!=usrBase)
			{
				usrBase.setEditflag(Short.valueOf("1"));
				entityManager.merge(usrBase);
				entityManager.flush();
			}
		}
	}
	
	public List<com.boc.model.UserRoleProductCategoryMapping> getUserRoleProductList(String maxLimit)
	{
		
		List<com.boc.model.UserRoleProductCategoryMapping> usrRoleProductList = null;
		try
		{
			String strQry = "Select userRoleProduct from com.boc.model.UserRoleProductCategoryMapping userRoleProduct where userRoleProduct.editflag=0 ";
			Query qry = entityManager.createQuery(strQry);
			usrRoleProductList = qry.setMaxResults(Integer.parseInt(maxLimit)).getResultList();
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return usrRoleProductList;
	}
	public List<com.boc.model.UserRoleProductCategoryBranchMapping> getUserRoleProductBranchList(String maxLimit)
	{
		List<com.boc.model.UserRoleProductCategoryBranchMapping> usrRoleProductBranchList = null;
		try
		{
			String strQry = "Select userRoleProductBranch from com.boc.model.UserRoleProductCategoryBranchMapping userRoleProductBranch where userRoleProductBranch.editflag=0 ";
			Query qry = entityManager.createQuery(strQry);
			usrRoleProductBranchList = qry.setMaxResults(Integer.parseInt(maxLimit)).getResultList();
			log.debug("usrRoleProductBranchList size is "+usrRoleProductBranchList.size());
		}
		catch(HibernateException hex)
		{
			hex.printStackTrace();
			log.error("Error ", hex.fillInStackTrace());
			log.error(hex.getMessage());
			throw new DAOException("er.db.getProductCategoryByBranch",hex);
		}
		return usrRoleProductBranchList;
	}
	
	
	
	public void updateUserRoleProductList(List<UserRoleProductCategoryMappingRs> userRoleProductLstRs)
	{
		for(UserRoleProductCategoryMappingRs  userRoleProductCategoryMappingRs:userRoleProductLstRs)
		{
			com.boc.model.UserRoleProductCategoryMapping userRoleMapping=  entityManager.find(com.boc.model.UserRoleProductCategoryMapping.class, userRoleProductCategoryMappingRs.getUrpcid());
			if(null!=userRoleMapping)
			{
				log.debug("Entity exists");
				System.out.println("Entity exists");
				userRoleMapping.setEditflag(Short.valueOf("1"));
				entityManager.merge(userRoleMapping);
				System.out.println("Data Saved");
				entityManager.flush();
				System.out.println("Flushing data");
			}
		}
	}
	
	public void updateUserRoleProductBranchList(List<UserRoleProductCategoryBranchMappingRs> userRoleProductBranchLstRs)
	{
		for(UserRoleProductCategoryBranchMappingRs  userRoleProductCategoryBranchMappingRs:userRoleProductBranchLstRs)
		{
			com.boc.model.UserRoleProductCategoryBranchMapping userRoleBranchMapping=  entityManager.find(com.boc.model.UserRoleProductCategoryBranchMapping.class, userRoleProductCategoryBranchMappingRs.getUrpcbid());
			if(null!=userRoleBranchMapping)
			{
				log.debug("Entity exists");
				System.out.println("Entity exists");
				userRoleBranchMapping.setEditflag(Short.valueOf("1"));
				entityManager.persist(userRoleBranchMapping);
				System.out.println("Data Saved");
				entityManager.flush();
				System.out.println("Flushing data");
			}
		}
	}*/
	
	
}
