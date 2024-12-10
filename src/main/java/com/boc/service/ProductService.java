

package com.boc.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.boc.dao.ProductDao;
import com.boc.form.searchAreaForm;
import com.boc.form.searchBranchForm;
import com.boc.form.searchProvinceForm;
import com.boc.form.updateUserForm;
import com.boc.ldap.UpmLdapEvents;
import com.boc.model.UserRoleProductCategoryBranchMapping;
import com.boc.model.UserRoleProductCategoryMapping;
import com.boc.model.UsrBase;
import com.boc.model.BranchBase;
import com.boc.model.RoleBase;
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
public class ProductService {
	private static Logger log =LoggerFactory.getLogger(ProductService.class);
	@Autowired
	private ProductDao productDao;
	
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<UserBaseRs> getUserIdList() throws Exception
	{
		List<UsrBase> userIdLst = productDao.getUserIdList();
		List<UserBaseRs> userBaseRsLst = null;
		userBaseRsLst = new ArrayList<UserBaseRs>();
		for(UsrBase userId: userIdLst)
		{
			UserBaseRs usrBaseRs = new UserBaseRs();
			usrBaseRs.setNtId(userId.getNtId());
			userBaseRsLst.add(usrBaseRs);
		}
		return userBaseRsLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<UserBaseRs> getUserIdListByRole(String userloggedIn) throws Exception
	{
		List<UserBaseRs> usrBaseRsLst = productDao.getUserIdListByRole(userloggedIn);
		return usrBaseRsLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<UserProductBranchRs> getProductCategoryBranchLst(String ntid) throws Exception
	{
		List<UserProductBranchRs> userproductBranchLst = productDao.getProductCategoryBranchLst(ntid);
		return userproductBranchLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<UserProductBranchRs> getUserLst(String ntid) throws Exception
	{
		List<UserProductBranchRs> userproductBranchLst = productDao.getUserLst(ntid);
		return userproductBranchLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public UserProductBranchRs getProductCategoryBranch(String urpcid) throws Exception
	{
		UserProductBranchRs userproductBranchRs = productDao.getProductCategoryBranch(urpcid);
		return userproductBranchRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public UserProductBranchRs getUserLstByUrpcId(String urpcid) throws Exception
	{
		UserProductBranchRs userproductBranchRs = productDao.getUserLstByUrpcId(urpcid);
		return userproductBranchRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<BranchBaseRs> getAllBranches()
	{
		 List<BranchBaseRs> branchBaseLst =  productDao.getAllBranches();
		return branchBaseLst;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public List<RoleBaseRs>  getAllRoles()
	{
		List<RoleBaseRs> roleBaseLst =  productDao.getAllRoles();
		return roleBaseLst;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public List<ProductCategoryRs> getAllProductCategory()
	{
		List<ProductCategoryRs> productCategoryLst = productDao.getAllProductCategory();
		return productCategoryLst;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public AreaBranchRs getUserAreaBranchList(String ntId)
	{
		
		AreaBranchRs areaBranchRs = productDao.getUserAreaBranchList(ntId);
		Set<String> productCategorySet = areaBranchRs.getProductCategory();
		/*Set<String> excludingBranchProductCategorySet = null;
		excludingBranchProductCategorySet = new HashSet<String>();
		for(String productCategory:productCategorySet)
		{
			int count = productDao.getProductCategoryBranchCount(ntId,productCategory.toUpperCase());
			log.debug("count for "+count+" productCategory is "+productCategory);
			if(count >1)
			{
				excludingBranchProductCategorySet.add(productCategory);
			}
		}
		areaBranchRs.setProductCategory(excludingBranchProductCategorySet);*/
		return areaBranchRs;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public ProvinceBranchRs getUserProvinceBranchList(String ntId,Set<String> roleSet,String rlcRoleBranchManager)
	{
		ProvinceBranchRs provinceBranchRs = productDao.getUserProvinceBranchList(ntId);
		ProvinceBranchRs rlcBranchRs = productDao.getUserRLCProvinceBranchList(ntId);
		log.debug("rlcRoleBranchManager is "+rlcRoleBranchManager);
		if(null!=rlcBranchRs && roleSet.contains(rlcRoleBranchManager))
		{
			/*Set provinceSet = provinceBranchRs.getProvinceName();
			if(null!= rlcBranchRs)
			{
			provinceSet.addAll(rlcBranchRs.getProvinceName());
			provinceBranchRs.setProvinceName(provinceSet);
			}*/
			return rlcBranchRs;
		}
		else
		{
			return provinceBranchRs;
		}
	}	
	@Transactional(value="transactionManager",readOnly=true)
	public BranchRs getUserBranchList(String ntId)
	{
		BranchRs branchRs = productDao.getUserBranchList(ntId);
		return branchRs;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public List<AreaBaseRs> getAllArea()
	{
		List<AreaBaseRs> areaBaseRsLst = productDao.getAllArea();
		return areaBaseRsLst;
	}
	@Transactional(value="transactionManager",readOnly=true)
	public List<ProvinceBaseRs> getAllProvince()
	{
		List<ProvinceBaseRs> provinceBaseRsLst = productDao.getAllProvince();
		List<ProvinceBaseRs> provinceBaseRsRLCLst = productDao.getAllRLCProvince();
		if(null!=provinceBaseRsRLCLst && provinceBaseRsRLCLst.size()>0)
			provinceBaseRsLst.addAll(provinceBaseRsRLCLst);
		return provinceBaseRsLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<Integer> getProductCategoryIdByName(Set<String> productCategorySet)
	{
		List<Integer> ProductCategoryIdLst = productDao.getProductCategoryIdByName(productCategorySet);
		return ProductCategoryIdLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<Integer> getRoleIdByName(Set<String> roleSet)
	{
		List<Integer> roleIdLst = productDao.getRoleIdByName(roleSet);
		return roleIdLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<Integer> getAreaIdByName(Set<String> areaSet)
	{
		List<Integer> areaIdLst = productDao.getAreaIdByName(areaSet);
		return areaIdLst;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<Integer> getProvinceIdByName(Set<String> provinceSet)
	{
		List<Integer> provinceIdLst = productDao.getProvinceIdByName(provinceSet);
		List<Integer> rlcProvinceIdLst  = productDao.getRLCProvinceIdByName(provinceSet);
		if(null!=rlcProvinceIdLst && rlcProvinceIdLst.size()>0)
			provinceIdLst.addAll(rlcProvinceIdLst);
		return provinceIdLst;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void updateProductCategoryBranchByArea(String ipAddress,searchAreaForm searchAreaForm,List<Integer>newProductCategoryIdLst,List<Integer> roleIdLst)
	{
		
		List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(searchAreaForm.getNtId());
		List<Integer> urpcIdLst = null;
		
		if(null!=userProductBranchRsLst)
		{
			urpcIdLst = new ArrayList<Integer>();
			for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
			{
				urpcIdLst.add(userProductBranchRs.getUrpcid());
			}
		}
		if(null!=urpcIdLst && urpcIdLst.size()>0)
		{
			log.debug("urpcIdLst size is "+urpcIdLst.size());;
			List<Integer> lstBid  = productDao.getBranchIdByArea(searchAreaForm.getAreaName());
			if(null!=lstBid && lstBid.size()>0)
			{
				productDao.deleteProductCategoryBranchByArea(urpcIdLst,lstBid);
				productDao.insertProductCategoryBranchByArea(urpcIdLst,lstBid);
			}
		}
	}
	@Transactional(value="transactionManager",readOnly=false)
	public void deleteUser(String ipAddress, String loggedInUser,updateUserForm updateUserForm)
	{
		productDao.deleteUser(updateUserForm.getNtId());
		
		//UserProductCategoryBranchRs UserProductCategoryBranchRs = productDao.getUserProductCategory(updateUserForm.getNtId(),updateUserForm.getExistingBid());
		List<UserRoleProductCategoryMappingRs> UserRoleProductCategoryMappingRsLst = productDao.getUserProductCategoryList(updateUserForm.getNtId());
		List<Integer> urpcIdLst = null;
		
		if(null!=UserRoleProductCategoryMappingRsLst)
		{
			urpcIdLst = new ArrayList<Integer>();
			for(UserRoleProductCategoryMappingRs userRoleProductCategoryMappingRs:UserRoleProductCategoryMappingRsLst)
			{
				log.debug("urpcid to delete is "+userRoleProductCategoryMappingRs.getUrpcid());
				urpcIdLst.add(userRoleProductCategoryMappingRs.getUrpcid());
			}
		}
		if(null!=urpcIdLst && urpcIdLst.size()>0)
		{
			log.debug("urpcIdLst size is "+urpcIdLst.size());
			
			productDao.deleteProductCategoryByUrpcid(urpcIdLst);
		}
		log.info(loggedInUser+"		"+updateUserForm.getNtId()+" User is deleted from ipAddress "+ipAddress);
		//log.debug("#############"+updateUserForm.getNtId()+" User is deleted by "+loggedInUser+" from ipAddress "+ipAddress);
	}
	@Transactional(value="transactionManager",readOnly=false)
	public void updateProductCategoryBranchByProvince(searchProvinceForm searchProvinceForm)
	{
		List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(searchProvinceForm.getNtId());
		List<Integer> urpcIdLst = null;
		
		if(null!=userProductBranchRsLst)
		{
			urpcIdLst = new ArrayList<Integer>();
			for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
			{
				urpcIdLst.add(userProductBranchRs.getUrpcid());
			}
		}
		if(null!=urpcIdLst && urpcIdLst.size()>0)
		{
			log.debug("urpcIdLst size is "+urpcIdLst.size());;
			List<Integer> lstBid  = productDao.getBranchIdByProvince(searchProvinceForm.getProvinceName());
			if(null!=lstBid && lstBid.size()>0)
			{
				productDao.deleteProductCategoryBranchByArea(urpcIdLst,lstBid);
				productDao.insertProductCategoryBranchByArea(urpcIdLst,lstBid);
			}

		}
			
		
		//return null;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void updateProductCategoryBranchByBranch(searchBranchForm searchBranchForm)
	{
		List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(searchBranchForm.getNtId());
		List<Integer> urpcIdLst = null;
		
		if(null!=userProductBranchRsLst)
		{
			urpcIdLst = new ArrayList<Integer>();
			for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
			{
				urpcIdLst.add(userProductBranchRs.getUrpcid());
			}
		}
		if(null!=urpcIdLst && urpcIdLst.size()>0)
		{
			log.debug("urpcIdLst size is "+urpcIdLst.size());;
			List<Integer> lstBid  = productDao.getBranchIdByBranch(searchBranchForm.getBranchName());
			if(null!=lstBid && lstBid.size()>0)
			{
				productDao.deleteProductCategoryBranchByArea(urpcIdLst,lstBid);
				productDao.insertProductCategoryBranchByArea(urpcIdLst,lstBid);
			}

		}
			
		
		//return null;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public RoleBase getRolebasebyRoleName(String roleName)
	{
		RoleBase roleBase =  productDao.getRolebasebyRoleName(roleName);
		return roleBase;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void updateProductCategoryBranchByUser(String loggedInUser,String ipAddress,updateUserForm updateUserForm,List<Integer> roleIdLst, List<Integer> newRoleIdLst,List<Integer> productCategoryIdLst,List<Integer> newProductCategoryIdLst,List<Integer> areaIdLst,List<Integer> newAreaIdLst,List<Integer> provinceIdLst,List<Integer> newProvinceIdLst)
	{
		String userName= updateUserForm.getName();
		userName = userName.replaceAll(",", "");
		log.debug("userName is "+userName);
		
		boolean productCategoryUpdated = false;
		
		boolean flag = false;
		boolean provinceRole = false;
		boolean areaRole = false;
		boolean branchRole = false;
		
		boolean roleFound = false;
		String roleSelected = null;
		ResourceBundle configMsgBundle = ResourceBundle.getBundle("config");
		String areaManager = configMsgBundle.getString("AREAMANAGERROLE");
		String branchRoleManager = configMsgBundle.getString("BRANCHROLE");
		Set<String> roleSet = updateUserForm.getRoleSet();
		log.debug("branchRoleManager "+branchRoleManager);
		log.debug("branchRoleManager "+branchRoleManager);
		log.debug(roleSet+" roleSet contains "+roleSet.contains(branchRoleManager));
		if(null!=roleSet)
		{
			/*if(roleSet.contains(areaManager))
			//if(roleSet.contains("AreaManager"))
			{
				roleSelected="AreaManager";
				roleFound = true;
				log.debug("roleSelected is "+roleSelected);
			}
			if(roleSet.contains("CreditAssistance,CreditOfficer,BranchManager"))
			//if(roleSet.contains(branchRoleManager))
			{
				roleSelected="Branch";
				roleFound = true;
				log.debug("roleSelected is "+roleSelected);
			}
			if(!roleFound)
			{
				roleSelected="Province";
				log.debug("roleSelected is "+roleSelected);
			}*/
			for(String role:roleSet)
			{
				log.debug("role is "+role);
				if(role.equalsIgnoreCase("AreaManager"))
				{
					roleSelected="AreaManager";
					areaRole = true;
				}
				if(role.equalsIgnoreCase("CreditAssistance") ||role.equalsIgnoreCase("CreditOfficer") || role.equalsIgnoreCase("BranchManager"))
				{
					roleSelected="Branch";
					branchRole = true;
				}				
			}
			if(!areaRole && !branchRole)
			{
				roleSelected="Province";
			}
		}
		log.debug("roleSelected is "+roleSelected);
		//if((null!=newProductCategoryIdLst && (newProductCategoryIdLst.size()>0)))
		//{
			//if province is changed 
			//if(roleSelected.equalsIgnoreCase("Province"))
			{
					Set<String> provinceSet = updateUserForm.getProvinceName();
					if(null!=provinceSet && provinceSet.size()>0)
					{
					 List<Integer> lstBid  = productDao.getBranchIdByProvince(provinceSet);
					 List<Integer> lstRlcBid  = productDao.getRLCBranchIdByProvince(provinceSet);
					 if(null!=lstRlcBid && lstRlcBid.size()>0)
						 lstBid.addAll(lstRlcBid);
					 if(null!=lstBid && lstBid.size()>0)
					 {
						 List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(userName);
							List<Integer> urpcIdLst = null;
							if(null!=userProductBranchRsLst)
							{
								urpcIdLst = new ArrayList<Integer>();
								for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
								{
									urpcIdLst.add(userProductBranchRs.getUrpcid());
								}
							}
						if(null!=urpcIdLst && urpcIdLst.size()>0)
							{
								log.debug("Deleting from Province urpcIdLst size is "+urpcIdLst.size());
						productDao.deleteProductCategoryByUrpcid(urpcIdLst);
						for(Integer roleId:roleIdLst)
						{
							productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
						}
						
						List<Integer> newUrpcIdLst = productDao.getUserProductCategoryIdList(userName);
						if(null!=newUrpcIdLst)
						{
							productDao.insertProductCategoryBranchByArea(newUrpcIdLst,lstBid);
							log.info(loggedInUser+"	"+userName+" Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" from ipadress "+ipAddress);
							log.info(loggedInUser+"	"+userName+" Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+"  from ipadress "+ipAddress);
							log.info(loggedInUser+"	"+userName+" Province from "+updateUserForm.getExistingProvinceNameSet() +" to "+updateUserForm.getProvinceName()+"  from ipadress "+ipAddress);
					
							/*log.debug("######### Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							log.debug("######### Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							log.debug("######### Province from "+updateUserForm.getExistingProvinceNameSet() +" to "+updateUserForm.getProvinceName()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);*/
							int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
							if(updatedBranchId==updateUserForm.getExistingBid())
							{
								log.debug("List Contains Branch"+lstBid.contains(Integer.parseInt(updateUserForm.getBranch())));
								if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
								{
									List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
									if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0 ) 
									{
										//productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
									}
									List<Integer> ExistingBranchLst = null;
									ExistingBranchLst  = new ArrayList<Integer>();
									ExistingBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
									productDao.insertProductCategoryBranchByArea(newUrpcIdLst,ExistingBranchLst);
									String existingBranch=null,updatedBranch=null;
									if(String.valueOf(updateUserForm.getExistingBid())!=null)
									{
										existingBranch = productDao.getBranch(updateUserForm.getExistingBid());
									}
									if(updateUserForm.getBranch()!=null)
									{
										updatedBranch = productDao.getBranch(Integer.parseInt(updateUserForm.getBranch()));
									}
									log.info(loggedInUser+"	"+userName	+" Branch updated from "+existingBranch+" to "+updatedBranch+" from ipadress "+ipAddress);
									//log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								}
							}
							else
							{
								productDao.updateUser(updateUserForm);
								log.debug("List Contains Branch"+lstBid.contains(Integer.parseInt(updateUserForm.getBranch())));
								if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
								{
									List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
									if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0)
									{
										//productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
									}
									List<Integer> newBranchLst = null;
									newBranchLst  = new ArrayList<Integer>();
									newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
									productDao.insertProductCategoryBranchByArea(newUrpcIdLst,newBranchLst);
									String existingBranch=null,updatedBranch=null;
									if(String.valueOf(updateUserForm.getExistingBid())!=null)
									{
										existingBranch = productDao.getBranch(updateUserForm.getExistingBid());
									}
									if(updateUserForm.getBranch()!=null)
									{
										updatedBranch = productDao.getBranch(Integer.parseInt(updateUserForm.getBranch()));
									}
									log.info(loggedInUser+"	"+userName	+" Branch updated from "+existingBranch+" to "+updatedBranch+" from ipadress "+ipAddress);
									//log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								}
							}
						}
						//log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser);
						flag =true;
						 provinceRole = true;
					 }
					}
				}
			}
			//else if(roleSelected.equalsIgnoreCase("AreaManager"))
			{
					Set<String> areaSet = updateUserForm.getAreaName();
					if(null!=areaSet && areaSet.size()>0)
					{
					List<Integer> lstBid  = productDao.getBranchIdByArea(areaSet);
					if(null!=lstBid && lstBid.size()>0)
					{
						List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(updateUserForm.getNtId());
						List<Integer> urpcIdLst = null;
						if(null!=userProductBranchRsLst)
						{
							urpcIdLst = new ArrayList<Integer>();
							for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
							{
								urpcIdLst.add(userProductBranchRs.getUrpcid());
							}
						}
						if(null!=urpcIdLst && urpcIdLst.size()>0)
						{
							log.debug("urpcIdLst size is "+urpcIdLst.size());
						productDao.deleteProductCategoryByUrpcid(urpcIdLst);
						for(Integer roleId:roleIdLst)
						{
							productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
						}
						List<Integer> newUrpcIdLst = productDao.getUserProductCategoryIdList(updateUserForm.getNtId());
						if(null!=newUrpcIdLst)
						{
							productDao.insertProductCategoryBranchByArea(newUrpcIdLst,lstBid);
							log.info(loggedInUser+"	"+userName+" Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" from ipadress "+ipAddress);
							log.info(loggedInUser+"	"+userName+" Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+"  from ipadress "+ipAddress);
							log.info(loggedInUser+"	"+userName+" Area from "+updateUserForm.getExistingAreaNameSet() +" to "+updateUserForm.getAreaName()+"  from ipadress "+ipAddress);
					
							/*log.debug("######### Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							log.debug("######### Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							log.debug("######### Province from "+updateUserForm.getExistingProvinceNameSet() +" to "+updateUserForm.getProvinceName()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							*/
							int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
							if(updatedBranchId==updateUserForm.getExistingBid())
							{
								if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
								{
									List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
									log.debug("existingUrpcIdLst is "+existingUrpcIdLst);
									if(null!=existingUrpcIdLst && existingUrpcIdLst.size()>0)
									{
										//productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
										//updateProductCategoryBranchList()
									}
										List<Integer> ExistingBranchLst = null;
										ExistingBranchLst  = new ArrayList<Integer>();
										ExistingBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
										productDao.insertProductCategoryBranchByArea(newUrpcIdLst,ExistingBranchLst);
										//productDao.updateProductCategoryBranch(newUrpcIdLst,updateUserForm.getExistingBid(),updatedBranchId);
										
								}
								String existingBranch=null,updatedBranch=null;
								if(String.valueOf(updateUserForm.getExistingBid())!=null)
								{
									existingBranch = productDao.getBranch(updateUserForm.getExistingBid());
								}
								if(updateUserForm.getBranch()!=null)
								{
									updatedBranch = productDao.getBranch(Integer.parseInt(updateUserForm.getBranch()));
								}
								log.info(loggedInUser+"	"+userName	+" Branch updated from "+existingBranch+" to "+updatedBranch+" from ipadress "+ipAddress);
								//log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							}
							else
							{
								productDao.updateUser(updateUserForm);
								if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
								{
									List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
									log.debug("existingUrpcIdLst is "+existingUrpcIdLst);
									if(null!=existingUrpcIdLst && existingUrpcIdLst.size()>0)
									{
									//productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
									}
									List<Integer> newBranchLst = null;
									newBranchLst  = new ArrayList<Integer>();
									newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
									productDao.insertProductCategoryBranchByArea(newUrpcIdLst,newBranchLst);
									
								}
								String existingBranch=null,updatedBranch=null;
								if(String.valueOf(updateUserForm.getExistingBid())!=null)
								{
									existingBranch = productDao.getBranch(updateUserForm.getExistingBid());
								}
								if(updateUserForm.getBranch()!=null)
								{
									updatedBranch = productDao.getBranch(Integer.parseInt(updateUserForm.getBranch()));
								}
								log.info(loggedInUser+" "+userName	+ "Branch updated from "+existingBranch+" to "+updatedBranch+" from ipadress "+ipAddress);
								//log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
							}
							
						}
						//log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser);
						flag = true;
						areaRole = true;
						}					
				 }
				}
				
			}
			if(!areaRole && !provinceRole) //(roleSelected.equalsIgnoreCase("Branch")) //Updated Role & ProductCategory only for Branch
			{
				log.debug("Only branch has updated");
				//List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
				List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(updateUserForm.getNtId());
				List<Integer> existingUrpcIdLst = null;
				if(null!=userProductBranchRsLst)
				{
					existingUrpcIdLst = new ArrayList<Integer>();
					for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
					{
						existingUrpcIdLst.add(userProductBranchRs.getUrpcid());
					}
				}
				if(null!=existingUrpcIdLst && existingUrpcIdLst.size()>0)
				{
				//productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
				productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst);
				for(Integer roleId:roleIdLst)
				{
					productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
				}
				List<Integer> urpcIdLst = null;
				urpcIdLst = new ArrayList<Integer>();
				urpcIdLst = productDao.getUserProductCategoryIdList(updateUserForm.getNtId());
				if(null!=urpcIdLst && urpcIdLst.size()>0)
				{
					productDao.updateUser(updateUserForm);
					List<Integer> newBranchLst = null;
					newBranchLst  = new ArrayList<Integer>();
					newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
					productDao.insertProductCategoryBranchByArea(urpcIdLst,newBranchLst);
					log.info(loggedInUser+"	"+userName+" Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" from ipadress "+ipAddress);
					log.info(loggedInUser+"	"+userName+" Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" from ipadress "+ipAddress);
/*					log.debug("######### Product Category from "+updateUserForm.getExistingProductCategorySet() +" to "+updateUserForm.getProductCategorySet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					log.debug("######### Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
*/					String existingBranch=null,updatedBranch=null;
					if(String.valueOf(updateUserForm.getExistingBid())!=null)
					{
						existingBranch = productDao.getBranch(updateUserForm.getExistingBid());
					}
					if(updateUserForm.getBranch()!=null)
					{
						updatedBranch = productDao.getBranch(Integer.parseInt(updateUserForm.getBranch()));
					}
					log.info(loggedInUser+"	"+userName	+" Branch updated from "+existingBranch+" to "+updatedBranch+" from ipadress "+ipAddress);
					//log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
				}
				flag = true;
				}
			}
		//}
		/*if(!flag && (null!=newAreaIdLst && newAreaIdLst.size()>0)) 
		{
			log.debug("only area is changed"); 
			List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(updateUserForm.getNtId());
			List<Integer> urpcIdLst = null;
			if(null!=userProductBranchRsLst)
			{
				urpcIdLst = new ArrayList<Integer>();
				for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
				{
					urpcIdLst.add(userProductBranchRs.getUrpcid());
				}
			}
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				log.debug("Found Records to delete "+urpcIdLst.size());;
				List<Integer> lstBid  = productDao.getBranchIdByArea(updateUserForm.getAreaName());
				if(null!=lstBid && lstBid.size()>0)
				{
					log.debug("Deleting Records");
					productDao.deleteProductCategoryByUrpcid(urpcIdLst);
					for(Integer roleId:roleIdLst)
					{
						productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
					}
					log.debug("************************************************************************************************************");
					log.debug("######### Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					log.debug("######### Area from "+updateUserForm.getExistingAreaNameSet() +" to "+updateUserForm.getAreaName()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					List<Integer> newUrpcIdLst = productDao.getUserProductCategoryIdList(updateUserForm.getNtId());
					if(null!=newUrpcIdLst &&  newUrpcIdLst.size()>0)
					{
						productDao.insertProductCategoryBranchByArea(newUrpcIdLst,lstBid);
						int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
						if(updatedBranchId==updateUserForm.getExistingBid())
						{
							if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
							{
								List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
								log.debug("Found Exisinting  Records for Branch "+existingUrpcIdLst);
								if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0)
									productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
								List<Integer> ExistingBranchLst = null;
								ExistingBranchLst  = new ArrayList<Integer>();
								ExistingBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
								productDao.insertProductCategoryBranchByArea(newUrpcIdLst,ExistingBranchLst);
								log.debug("#########branch has been updated from "+updateUserForm.getExistingBranch()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								log.debug("************************************************************************************************************");
							}
						}
						else
						{
							productDao.updateUser(updateUserForm);
							if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
							{
								List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
								log.debug("Found Exisinting  Records for Branch "+existingUrpcIdLst);
								if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0)
									productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
								List<Integer> newBranchLst = null;
								newBranchLst  = new ArrayList<Integer>();
								newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
								productDao.insertProductCategoryBranchByArea(newUrpcIdLst,newBranchLst);
								log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								log.debug("************************************************************************************************************");
							}
							
							
							
						}
						
					}
					//log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser);
				}
			}
		}*/
		/*if(!flag && (null!=newProvinceIdLst && newProvinceIdLst.size()>0)) //This handles both role & province if Areamanager has promoted province, provincelist updated
		{
			List<UserProductBranchRs> userProductBranchRsLst = productDao.getProductCategoryBranchLst(userName);
			List<Integer> urpcIdLst = null;
			if(null!=userProductBranchRsLst)
			{
				urpcIdLst = new ArrayList<Integer>();
				for(UserProductBranchRs userProductBranchRs:userProductBranchRsLst)
				{
					urpcIdLst.add(userProductBranchRs.getUrpcid());
				}
			}
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				log.debug("urpcIdLst size is "+urpcIdLst.size());;
				List<Integer> lstBid  = productDao.getBranchIdByProvince(updateUserForm.getProvinceName());
				if(null!=lstBid && lstBid.size()>0)
				{
					productDao.deleteProductCategoryByUrpcid(urpcIdLst);
					for(Integer roleId:roleIdLst)
					{
						productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
					}
					log.debug("************************************************************************************************************");
					log.debug("######### Role from "+updateUserForm.getExistingRoleSet() +" to "+updateUserForm.getRoleSet()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					log.debug("######### Province from "+updateUserForm.getExistingProvinceNameSet() +" to "+updateUserForm.getProvinceName()+" of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					List<Integer> newUrpcIdLst = productDao.getUserProductCategoryIdList(userName);
					if(null!=newUrpcIdLst)
					{
						productDao.insertProductCategoryBranchByArea(newUrpcIdLst,lstBid);
						int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
						if(updatedBranchId==updateUserForm.getExistingBid())
						{
							if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
							{
								List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
								if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0)
									productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
								List<Integer> ExistingBranchLst = null;
								ExistingBranchLst  = new ArrayList<Integer>();
								ExistingBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
								productDao.insertProductCategoryBranchByArea(newUrpcIdLst,ExistingBranchLst);
								log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+"  of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								log.debug("************************************************************************************************************");
							}
						}
						else
						{
							productDao.updateUser(updateUserForm);
							if(!lstBid.contains(Integer.parseInt(updateUserForm.getBranch())))
							{
								List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
								if(null!= existingUrpcIdLst && existingUrpcIdLst.size()>0)
									productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
								List<Integer> newBranchLst = null;
								newBranchLst  = new ArrayList<Integer>();
								newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
								productDao.insertProductCategoryBranchByArea(newUrpcIdLst,newBranchLst);
								log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+"  of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
								log.debug("************************************************************************************************************");
							}
							
							
							
						}
					}
					//log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+" of user "+userName+" by "+loggedInUser);
				}
			}
		}*/
		//If role change done other than branch, mandatorily asking for area and province to add, above code will work
		//if(!flag && roleSelected.equalsIgnoreCase("Branch")) // Handle only CA,CO,BM  //Updated only the role or branch but not product
		/*if(!flag && !areaRole && !provinceRole)
		{
			log.debug("branch is updated");
			int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
			log.debug(updatedBranchId+" "+updateUserForm.getExistingBid());
			if(updatedBranchId!=updateUserForm.getExistingBid())
			{
				productDao.updateUser(updateUserForm);			
			}
		
			
			List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
			productDao.deleteProductCategoryByUrpcid(existingUrpcIdLst,updateUserForm.getExistingBid());
			
			for(Integer roleId:roleIdLst)
			{
				productDao.createUserByProductCategoryRole(updateUserForm,productCategoryIdLst,roleId);
			}
			List<Integer> urpcIdLst = null;
			urpcIdLst = new ArrayList<Integer>();
			urpcIdLst = productDao.getUserProductCategoryIdList(updateUserForm.getNtId());
			
			if(null!=urpcIdLst && urpcIdLst.size()>0)
			{
				productDao.updateUser(updateUserForm);
				List<Integer> newBranchLst = null;
				newBranchLst  = new ArrayList<Integer>();
				newBranchLst.add(Integer.parseInt(updateUserForm.getBranch()));
				productDao.insertProductCategoryBranchByArea(urpcIdLst,newBranchLst);
				log.debug("************************************************************************************************************");
				log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+"  of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
				log.debug("************************************************************************************************************");
			}
		}*/
		/*if(!flag) //Updated only Branch of either AreaManager ot ProvinceManager
		{
			int updatedBranchId = Integer.parseInt(updateUserForm.getBranch());
			log.debug("updatedBranchId is "+updatedBranchId+" Existing BID "+updateUserForm.getExistingBid());
			if((updatedBranchId!=updateUserForm.getExistingBid()))
			{
				productDao.updateUser(updateUserForm);
				List<Integer> existingUrpcIdLst = productDao.isExistsUserProductCategoryBranch(updateUserForm);
				
				if(null!=existingUrpcIdLst && existingUrpcIdLst.size()>0)
				{
					for(Integer urpcid:existingUrpcIdLst)
					{
						productDao.updateProductCategoryBranch(urpcid,updateUserForm.getExistingBid(),updatedBranchId);
					}
					log.debug("************************************************************************************************************");
					log.debug("#########branch has been updated from "+updateUserForm.getExistingBid()+" to "+updateUserForm.getBranch()+"  of user "+userName+" by "+loggedInUser+" from ipadress "+ipAddress);
					log.debug("************************************************************************************************************");
				}
			}
		}*/
		
		
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void updateUsrProductCategory(UserProductBranchRs userProductBranchRs) {
		productDao.updateProductCategoryBranch(userProductBranchRs);
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void updateUserBranch(UserProductBranchRs userProductBranchRs) {
		productDao.updateUserBranch(userProductBranchRs);
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public String getRoleName(int roleId) {
		return productDao.getRole(roleId);
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public String getBranch(int bid) {
		return productDao.getBranch(bid);
	}
	
	
	
	@Transactional(value="transactionManager",readOnly=true)
	public UsrBase getUserBasebyUserName(String userName) {
		UsrBase usrBase=  productDao.getUserBasebyUserName(userName);
		return usrBase;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void createUser(updateUserForm updateUserForm) {
		productDao.createUser(updateUserForm);
		//return productDao.getRole(roleId);
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public void createUserBranch(String ipAddress,String loggedInUser, updateUserForm updateUserForm) {
		productDao.createUserBranch(ipAddress,loggedInUser,updateUserForm);
		//return productDao.getRole(roleId);
	}
	@Transactional(value="transactionManager",readOnly=false)
	public String  createUserProductCategory(updateUserForm updateUserForm) {
		String responseMessage = productDao.createUserProductCategory(updateUserForm);
		return responseMessage;
	}
	
	@Transactional(value="transactionManager",readOnly=false)
	public UserProductCategoryBranchRs  getUserProductCategory(String ntId,int bid) {
		UserProductCategoryBranchRs userProductCategoryBranchRs = productDao.getUserProductCategory(ntId,bid);
		return userProductCategoryBranchRs;
	}
	
	/*
	//Existing
	@Transactional(value="transactionManager",readOnly=true)
	public List<RoleBaseRs> getRoleBaseList(String maxLimit)
	{
		List<RoleBaseRs> roleBaseLstRs = null;
		List<com.boc.model.RoleBase> roleList = null;
		log.debug("Retrieving User List");
		roleList = productDao.getRoleList(maxLimit);
		if(null!=roleList)
		{
			roleBaseLstRs = new ArrayList<RoleBaseRs>();
			for(RoleBase roleBase:roleList)
			{
				RoleBaseRs roleBaseRs = new RoleBaseRs();
				roleBaseRs.setRid(roleBase.getRid());
				roleBaseRs.setRoleDescription(roleBase.getRoleDescription());
				roleBaseRs.setRoleLabel(roleBase.getRoleLabel());
				roleBaseRs.setRoleName(roleBase.getRoleDescription());
				roleBaseRs.setLevel(roleBase.getLevel());
				roleBaseLstRs.add(roleBaseRs);
			}
		}
		return roleBaseLstRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public void updateRoleBaseList(List<RoleBaseRs> roleBaseLstRs)
	{
		productDao.updateRoleBaseList(roleBaseLstRs);
	}
	@Transactional(value="transactionManager",readOnly=true)
	public List<BranchBaseRs> getBranchBaseList(String maxLimit)
	{
		List<BranchBaseRs> branchBaseLstRs = null;
		List<BranchBase> branchBaseLst = productDao.getBranchBaseList(maxLimit);
		if(null != branchBaseLst)
		{
			log.debug("Records Found to Update in BranchBase Table "+branchBaseLst.size());
			branchBaseLstRs = new ArrayList<BranchBaseRs>();
			for(BranchBase branchBase:branchBaseLst)
			{
				BranchBaseRs branchBaseRs = new BranchBaseRs();
				branchBaseRs.setBid(branchBase.getBid());
				branchBaseRs.setBranchCode(branchBase.getBranchCode());
				branchBaseRs.setBranchContactNumber(branchBase.getBranchContactNumber());
				branchBaseRs.setBranchGrade(branchBase.getBranchGrade());
				branchBaseRs.setBranchName(branchBase.getBranchName());
				branchBaseLstRs.add(branchBaseRs);
			}
		}
		return branchBaseLstRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public void updateBranchBaseList(List<BranchBaseRs> branchBaseLstRs)
	{
		productDao.updateBranchBaseList(branchBaseLstRs);
	}
	

	
	

	@Transactional(value="transactionManager",readOnly=true)
	public List<UserBaseRs> getUserList(String maxLimit)
	{
		List<UserBaseRs> usrBaseLstRs = null;
		List<com.boc.model.UsrBase> usrList = null;
		log.debug("Retrieving User List");
		usrList = productDao.getUserList(maxLimit);
		if(null!=usrList)
		{
			log.debug("Records Found to Update in UserBase Table "+usrList.size());
			usrBaseLstRs = new ArrayList<UserBaseRs>();
			for(UsrBase usrBase:usrList)
			{
				
				UserBaseRs userBaseRs = new UserBaseRs();
				userBaseRs.setFirstName(usrBase.getFirstName());
				userBaseRs.setLastName(usrBase.getLastName());
				userBaseRs.setMiddleName(usrBase.getMiddleName());
				userBaseRs.setNtDomain(usrBase.getNtDomain());
				userBaseRs.setNtId(usrBase.getNtId());
				userBaseRs.setUid(usrBase.getUid());
				userBaseRs.setBid(usrBase.getBranchBase().getBid());
				usrBaseLstRs.add(userBaseRs);
			}
		}
		return usrBaseLstRs;
	}
	
	
	
	@Transactional(value="transactionManager",readOnly=true)
	public List<UserRoleProductCategoryMappingRs> getUserRoleProductList(String maxLimit)
	{
		List<UserRoleProductCategoryMappingRs> userRoleProductLstRs= null;
		List<UserRoleProductCategoryMapping> userRoleProductLst = productDao.getUserRoleProductList(maxLimit);
		if(null!= userRoleProductLst)
		{
			log.debug("Records Found to Update in UserRoleProduct Table "+userRoleProductLst.size());
			userRoleProductLstRs = new ArrayList<UserRoleProductCategoryMappingRs>();
			for(UserRoleProductCategoryMapping userRoleProduct: userRoleProductLst)
			{
				UserRoleProductCategoryMappingRs userRoleProductCategoryRs = new UserRoleProductCategoryMappingRs();
				userRoleProductCategoryRs.setUrpcid(userRoleProduct.getUrpcid());
				userRoleProductCategoryRs.setUid(userRoleProduct.getUid());
				userRoleProductCategoryRs.setRid(userRoleProduct.getRid());
				userRoleProductCategoryRs.setProductCategoryId(userRoleProduct.getProductCategoryId());
				userRoleProductLstRs.add(userRoleProductCategoryRs);
			}
		}
		return userRoleProductLstRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public void updateUserRoleProductList(List<UserRoleProductCategoryMappingRs> userRoleProductLstRs)
	{
		productDao.updateUserRoleProductList(userRoleProductLstRs);
	}
	
	public List<UserRoleProductCategoryBranchMappingRs> getUserRoleProductBranchList(String maxLimit)
	{
		List<UserRoleProductCategoryBranchMappingRs> userRoleProductBranchLstRs= null;
		List<com.boc.model.UserRoleProductCategoryBranchMapping> userRoleProductBranchLst = productDao.getUserRoleProductBranchList(maxLimit);
		if(null!= userRoleProductBranchLst)
		{
			log.debug("Records Found to Update in UserRoleProductBranch Table "+userRoleProductBranchLst.size());
			userRoleProductBranchLstRs = new ArrayList<UserRoleProductCategoryBranchMappingRs>();
			for(com.boc.model.UserRoleProductCategoryBranchMapping userRoleProductBranch: userRoleProductBranchLst)
			{
				UserRoleProductCategoryBranchMappingRs userRoleProductCategoryBranchRs = new UserRoleProductCategoryBranchMappingRs();
				userRoleProductCategoryBranchRs.setUrpcid(userRoleProductBranch.getUrpcid());
				userRoleProductCategoryBranchRs.setUrpcbid(userRoleProductBranch.getUrpcbid());
				userRoleProductCategoryBranchRs.setBid(userRoleProductBranch.getBid());
				userRoleProductBranchLstRs.add(userRoleProductCategoryBranchRs);
			}
		}
		return userRoleProductBranchLstRs;
	}
	
	@Transactional(value="transactionManager",readOnly=true)
	public void updateUserRoleProductBranchList(List<UserRoleProductCategoryBranchMappingRs> userRoleProductBranchLstRs)
	{
		productDao.updateUserRoleProductBranchList(userRoleProductBranchLstRs);
	}
	*/
	
	
}
