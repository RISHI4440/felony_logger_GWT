package com.crime.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.crime.client.GreetingService;
import com.crime.shared.ChargesheetBean;
import com.crime.shared.CrimetypeBean;
import com.crime.shared.CriminalBean;
import com.crime.shared.FieldVerifier;
import com.crime.shared.FirBean;
import com.crime.shared.FirregionBean;
import com.crime.shared.FirtypeBean;
import com.crime.shared.HostoryBean;
import com.crime.shared.LoginBean;
import com.crime.shared.PostmortamBean;
import com.crime.shared.PrisonerBean;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import entity.Chargesheet;
import entity.ChargesheetJpaController;
import entity.Fir;
import entity.Criminal;
import entity.CriminalJpaController;
import entity.Fir;
import entity.FirJpaController;
import entity.Crimetype;
import entity.CrimetypeJpaController;
import entity.Firregion;
import entity.FirregionJpaController;
import entity.Firtype;
import entity.FirtypeJpaController;
import entity.Hostory;
import entity.HostoryJpaController;
import entity.Login;
import entity.LoginJpaController;
import entity.Postmortam;
import entity.PostmortamJpaController;
import entity.Prisoner;
import entity.PrisonerJpaController;


/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String createUserServer(boolean isAdmin, String user, String pass1) {
		
		String status="";
		Login entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			LoginJpaController controller = new LoginJpaController(emf);
			
			entity = new Login();
			entity.setUserid(user);
			entity.setPassword(pass1);
			entity.setRole("user");
				
			
			controller.create(entity);
				
			
					
			status = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<FirtypeBean> loadFirTypeServer() {
		
		List<FirtypeBean> list = new ArrayList<FirtypeBean>();
		List<Firtype> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			FirtypeJpaController controller = new FirtypeJpaController(emf);
			
			listEntity = controller.findFirtypeEntities();
					
			
			for (Iterator<Firtype> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Firtype entity = (Firtype) iterator.next();
				
				FirtypeBean bean = new FirtypeBean();
				
				bean.setFirtypeid(entity.getFirtypeid());
				bean.setTypename(entity.getTypename());
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<FirregionBean> loadRegionServer() {
		List<FirregionBean> list = new ArrayList<FirregionBean>();
		List<Firregion> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			FirregionJpaController controller = new FirregionJpaController(emf);
			
			listEntity = controller.findFirregionEntities();
					
			
			for (Iterator<Firregion> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Firregion entity = (Firregion) iterator.next();
				
				FirregionBean bean = new FirregionBean();
				
				bean.setFirregionid(entity.getFirregionid());
				bean.setRegionname(entity.getRegionname());
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<CrimetypeBean> loadCrimetypeServer() {
		List<CrimetypeBean> list = new ArrayList<CrimetypeBean>();
		List<Crimetype> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			CrimetypeJpaController controller = new CrimetypeJpaController(emf);
			
			listEntity = controller.findCrimetypeEntities();
					
			
			for (Iterator<Crimetype> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Crimetype entity = (Crimetype) iterator.next();
				
				CrimetypeBean bean = new CrimetypeBean();
				
				bean.setCrimetypeid(entity.getCrimetypeid());
				bean.setCrimename(entity.getCrimename());
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<FirBean> loadFirServer() {
		
		List<FirBean> list = new ArrayList<FirBean>();
		List<Fir> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			FirJpaController controller = new FirJpaController(emf);
			
			listEntity = controller.findFirEntities();
					
			
			for (Iterator<Fir> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Fir entity = (Fir) iterator.next();
				
				FirBean bean = new FirBean();
				bean.setFirid(entity.getFirid());
				bean.setFirdate(entity.getFirdate());
				bean.setDistrict(entity.getDistrict());
				bean.setCrimetypeid(new CrimetypeBean(entity.getCrimetypeid().getCrimetypeid()	, entity.getCrimetypeid().getCrimename()));
				bean.setFirregionid(new FirregionBean(entity.getFirregionid().getFirregionid(), entity.getFirregionid().getRegionname()));
				bean.setFirtime(entity.getFirtime());
				bean.setFirtypeid(new FirtypeBean(entity.getFirtypeid().getFirtypeid(), entity.getFirtypeid().getTypename()));
				bean.setInforecieved(entity.getInforecieved());
				bean.setInformantname(entity.getInformantname());
				bean.setPlace(entity.getPlace());
				bean.setPolicename(entity.getPolicename());
				bean.setRecievedtime(entity.getRecievedtime());
				bean.setStatus(entity.getStatus());
											    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<ChargesheetBean> loadChshServer() {
		
		List<ChargesheetBean> list = new ArrayList<ChargesheetBean>();
		List<Chargesheet> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			ChargesheetJpaController controller = new ChargesheetJpaController(emf);
			
			listEntity = controller.findChargesheetEntities();
					
			
			for (Iterator<Chargesheet> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Chargesheet entity = (Chargesheet) iterator.next();
				
				ChargesheetBean bean = new ChargesheetBean();
				
				bean.setChargesheetid(entity.getChargesheetid());
				bean.setAccusname(entity.getAccusname());
				bean.setFirid(new FirBean(entity.getFirid().getFirid()));
				bean.setAccaddress(entity.getAccaddress());
				
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public List<PrisonerBean> loadPrisonerServer() {
		
		List<PrisonerBean> list = new ArrayList<PrisonerBean>();
		List<Prisoner> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PrisonerJpaController controller = new PrisonerJpaController(emf);
			
			listEntity = controller.findPrisonerEntities();
					
			
			for (Iterator<Prisoner> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Prisoner entity = (Prisoner) iterator.next();
				
				PrisonerBean bean = new PrisonerBean();
				bean.setPrisonerid(entity.getPrisonerid());
				bean.setChargesheetid(new ChargesheetBean(entity.getChargesheetid().getChargesheetid()));
				bean.setColor(entity.getColor());
				bean.setFamilyname(entity.getFamilyname());
				bean.setFirdate(entity.getFirdate());
				bean.setHeight(entity.getHeight());
				bean.setIdentitymark(entity.getIdentitymark());
				bean.setNickname(entity.getNickname());
				bean.setStatus(entity.getStatus());
				bean.setWeight(entity.getWeight());
											    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveFirServer(FirBean bean) {
		
		String status[]={"",""};
		Fir entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			FirJpaController controller = new FirJpaController(emf);
			
			if(bean.getFirid()!=null){
			
				entity = controller.findFir(bean.getFirid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Fir();
				entity.setCrimetypeid(new Crimetype(bean.getCrimetypeid().getCrimetypeid()));
				entity.setDistrict(bean.getDistrict());
				entity.setFirdate(bean.getFirdate());
				entity.setFirregionid(new Firregion(bean.getFirregionid().getFirregionid()));
				entity.setFirtime(bean.getFirtime());
				entity.setFirtypeid(new Firtype(bean.getFirtypeid().getFirtypeid()));
				entity.setInforecieved(bean.getInforecieved());
				entity.setInformantname(bean.getInformantname());
				entity.setPlace(bean.getPlace());
				entity.setPolicename(bean.getPolicename());
				entity.setRecievedtime(bean.getRecievedtime());
				entity.setStatus(bean.getStatus());
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getFirid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] saveChShServer(ChargesheetBean bean) {
		
		String status[]={"",""};
		Chargesheet entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			ChargesheetJpaController controller = new ChargesheetJpaController(emf);
			
			if(bean.getChargesheetid()!=null){
			
				entity = controller.findChargesheet(bean.getChargesheetid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Chargesheet();
				entity.setAccaddress(bean.getAccaddress());
				entity.setAccusname(bean.getAccusname());
				entity.setChsdate(bean.getChsdate());
				entity.setFirid(new Fir(bean.getFirid().getFirid()));
				entity.setInfoadd(bean.getInfoadd());
				entity.setInfoname(bean.getInfoname());
				entity.setInfoocc(bean.getInfoocc());
				entity.setInfoperticular(bean.getInfoperticular());
				
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getChargesheetid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<ChargesheetBean> listChShServer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] savePMServer(PostmortamBean bean) {
		
		String status[]={"",""};
		Postmortam entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PostmortamJpaController controller = new PostmortamJpaController(emf);
			
			if(bean.getPmid()!=null){
			
				entity = controller.findPostmortam(bean.getPmid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Postmortam();
				entity.setFirid(new Fir(bean.getFirid().getFirid()));
				entity.setCasedesc(bean.getCasedesc());
				entity.setDeathdate(bean.getDeathdate());
				entity.setDoctorname(bean.getDoctorname());
				entity.setGender(bean.getGender());
				entity.setHomename(bean.getHomename());
				entity.setPolicestation(bean.getPolicestation());
				entity.setReslt(bean.getReslt());
				
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getPmid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<PostmortamBean> listPMServer(int id) {
		
		List<PostmortamBean> list = new ArrayList<PostmortamBean>();
		List<Postmortam> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PostmortamJpaController controller = new PostmortamJpaController(emf);
			
			listEntity = controller.findPostmortamEntities();
					
			
			for (Iterator<Postmortam> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Postmortam entity = (Postmortam) iterator.next();
				
				PostmortamBean bean = new PostmortamBean();
				
				bean.setPmid(entity.getPmid());
				bean.setFirid(new FirBean(entity.getFirid().getFirid()));
				bean.setCasedesc(entity.getCasedesc());
				bean.setDeathdate(entity.getDeathdate());
				bean.setDoctorname(entity.getDoctorname());
				bean.setGender(entity.getGender());
				bean.setHomename(entity.getHomename());
				bean.setPolicestation(entity.getPolicestation());
				bean.setReslt(entity.getReslt());
				
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] savePrisonerServer(PrisonerBean bean) {
		
		String status[]={"",""};
		Prisoner entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PrisonerJpaController controller = new PrisonerJpaController(emf);
			
			if(bean.getPrisonerid()!=null){
			
				entity = controller.findPrisoner(bean.getPrisonerid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Prisoner();
				entity.setChargesheetid(new Chargesheet(bean.getChargesheetid().getChargesheetid()));
				entity.setColor(bean.getColor());
				entity.setFamilyname(bean.getFamilyname());
				entity.setFirdate(bean.getFirdate());
				entity.setHeight(bean.getHeight());
				entity.setIdentitymark(bean.getIdentitymark());
				entity.setNickname(bean.getNickname());
				entity.setStatus(bean.getStatus());
				entity.setWeight(bean.getWeight());
				
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getPrisonerid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<PrisonerBean> listPrisonerServer(int id) {
		
		List<PrisonerBean> list = new ArrayList<PrisonerBean>();
		List<Prisoner> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PrisonerJpaController controller = new PrisonerJpaController(emf);
			
			listEntity = controller.findPrisonerEntities();
					
			
			for (Iterator<Prisoner> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Prisoner entity = (Prisoner) iterator.next();
				
				PrisonerBean bean = new PrisonerBean();
				
				bean.setPrisonerid(entity.getPrisonerid());
				bean.setChargesheetid(new ChargesheetBean(entity.getChargesheetid().getChargesheetid()));
				bean.setColor(entity.getColor());
				bean.setFamilyname(entity.getFamilyname());
				bean.setFirdate(entity.getFirdate());
				bean.setHeight(entity.getHeight());
				bean.setIdentitymark(entity.getIdentitymark());
				bean.setNickname(entity.getNickname());
				bean.setStatus(entity.getStatus());
				bean.setWeight(entity.getWeight());
				
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveCriminalServer(CriminalBean bean) {
		
		String status[]={"",""};
		Criminal entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			CriminalJpaController controller = new CriminalJpaController(emf);
			
			if(bean.getCriminalid()!=null){
			
				entity = controller.findCriminal(bean.getCriminalid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Criminal();
				entity.setAddress(bean.getAddress());
				entity.setAge(bean.getAge());
				entity.setCrimetypeid(new Crimetype(bean.getCrimetypeid().getCrimetypeid()));
				entity.setDescription(bean.getDescription());
				entity.setGender(bean.getGender());
				entity.setIsmostwanted(bean.getIsmostwanted());
				entity.setName(bean.getName());
				entity.setNickname(bean.getNickname());
				entity.setOccupation(bean.getOccupation());
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getCriminalid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<CriminalBean> listCriminalServer(int id) {
		List<CriminalBean> list = new ArrayList<CriminalBean>();
		List<Criminal> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			CriminalJpaController controller = new CriminalJpaController(emf);
			
			listEntity = controller.findCriminalEntities();
					
			
			for (Iterator<Criminal> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Criminal entity = (Criminal) iterator.next();
				
				CriminalBean bean = new CriminalBean();
				bean.setCriminalid(entity.getCriminalid());
				bean.setAddress(entity.getAddress());
				bean.setAge(entity.getAge());
				bean.setCrimetypeid(new CrimetypeBean(entity.getCrimetypeid().getCrimetypeid(), entity.getCrimetypeid().getCrimename()));
				bean.setDescription(entity.getDescription());
				bean.setGender(entity.getGender());
				bean.setIsmostwanted(entity.getIsmostwanted());
				bean.setName(entity.getName());
				bean.setNickname(entity.getNickname());
				bean.setOccupation(entity.getOccupation());
				
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public String[] saveHistoryServer(HostoryBean bean) {
		
		String status[]={"",""};
		Hostory entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			HostoryJpaController controller = new HostoryJpaController(emf);
			
			if(bean.getHistoryid()!=null){
			
				entity = controller.findHostory(bean.getHistoryid());
			}
					
			if(entity!=null) {
										
					
					
//					controller.edit(entity);
					
				
			}else {
				
				entity = new Hostory();
				entity.setCrimeno(bean.getCrimeno());
				entity.setCrimetypeid(new Crimetype(bean.getCrimetypeid().getCrimetypeid()));
				entity.setDateofocc(bean.getDateofocc());
				entity.setDescription(bean.getDescription());
				entity.setPlaceofocc(bean.getPlaceofocc());
				entity.setPrisonerid(new Prisoner(bean.getPrisonerid().getPrisonerid()));
				
								
				controller.create(entity);
				
			}
					
			status[0] = "true";
			status[1] = ""+entity.getHistoryid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public List<HostoryBean> listHistoryServer(int id) {
		
		List<HostoryBean> list = new ArrayList<HostoryBean>();
		List<Hostory> listEntity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			HostoryJpaController controller = new HostoryJpaController(emf);
			
			listEntity = controller.findHostoryEntities();
					
			
			for (Iterator<Hostory> iterator = listEntity.iterator(); iterator.hasNext();) {
				
				Hostory entity = (Hostory) iterator.next();
				
				HostoryBean bean = new HostoryBean();
				bean.setHistoryid(entity.getHistoryid());
				bean.setCrimeno(entity.getCrimeno());
				bean.setCrimetypeid(new CrimetypeBean(entity.getCrimetypeid().getCrimetypeid(), entity.getCrimetypeid().getCrimename()));
				bean.setDateofocc(entity.getDateofocc());
				bean.setDescription(entity.getDescription());
				bean.setPlaceofocc(entity.getPlaceofocc());
				
				PrisonerBean pbean = new PrisonerBean(entity.getPrisonerid().getPrisonerid());
				pbean.setFamilyname(entity.getPrisonerid().getFamilyname());
				
				bean.setPrisonerid(pbean);
				
							    	
				list.add(bean);
				
			}
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return list;
	}

	@Override
	public LoginBean loginServer(LoginBean loginBean) {
		
		LoginBean bean = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			EntityManager em;
			
			em = emf.createEntityManager();
			
			Query query = em.createQuery("SELECT l FROM Login l WHERE l.userid = :userid and l.password = :password");
			query.setParameter("userid", loginBean.getUserid());
			query.setParameter("password", loginBean.getPassword());
			
			try{
			
				Object obj = query.getSingleResult();
				
				if(obj!=null){
					
					Login login = (Login) obj;
					
					bean = loginBean;
					bean.setRole(login.getRole());
				}
				
			}catch(NoResultException e){
				
				e.printStackTrace();
			}
						
			
					
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			
		}
		
		return bean;
	}

	@Override
	public String[] deleteFirServer(int id) {
		
		String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			FirJpaController recieptJpaController = new FirJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] deleteChshServer(int id) {
		
		String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			ChargesheetJpaController recieptJpaController = new ChargesheetJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] deletePMServer(int id) {
		
String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PostmortamJpaController recieptJpaController = new PostmortamJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;
	}

	@Override
	public String[] deletePrisonerServer(int id) {
		
String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			PrisonerJpaController recieptJpaController = new PrisonerJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;

	}

	@Override
	public String[] deleteCriminalServer(int id) {
		
String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			CriminalJpaController recieptJpaController = new CriminalJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;

	}

	@Override
	public String[] deleteHistoryServer(int id) {
String status[]={"",""};
				
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			HostoryJpaController recieptJpaController = new HostoryJpaController(emf);
							
				recieptJpaController.destroy(id);
										
					
			status[0] = "true";
			
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
		}
		
		finally {
						
			
		}
		
		return status;

	}

	@Override
	public List<FirBean> searchFirServer(String q) {
		EntityManager em =null;
		List<FirBean> list = new ArrayList<FirBean>();
		List<Fir> listFir = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT f FROM Fir f where f.place like '%"+q+"%' or f.district like '%"+q+"%' or f.policename like '%"+q+"%' or f.informantname like '%"+q+"%' or f.inforecieved like '%"+q+"%'";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + "or f.firid = :firid";
				
				
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			
			if(bno){
				
				query.setParameter("firid", no);
			}
						
			//query.setMaxResults(20);
			listFir = query.getResultList();
						
			for (Iterator<Fir> iterator = listFir.iterator(); iterator.hasNext();) {
				Fir fir = (Fir) iterator.next();
				
				FirBean bean = new FirBean();
				bean.setFirid(fir.getFirid());
				bean.setCrimetypeid(new CrimetypeBean(fir.getCrimetypeid().getCrimetypeid()));
				bean.setDistrict(fir.getDistrict());
				bean.setFirdate(fir.getFirdate());
				bean.setFirregionid(new FirregionBean(fir.getFirregionid().getFirregionid()));
				bean.setFirtime(fir.getFirtime());
				bean.setFirtypeid(new FirtypeBean(fir.getFirtypeid().getFirtypeid()));
				bean.setInforecieved(fir.getInforecieved());
				bean.setInformantname(fir.getInformantname());
				bean.setPlace(fir.getPlace());
				bean.setPolicename(fir.getPolicename());
				bean.setRecievedtime(fir.getRecievedtime());
				bean.setStatus(fir.getStatus());
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public List<ChargesheetBean> searchChShServer(String q) {
		
		EntityManager em =null;
		List<ChargesheetBean> list = new ArrayList<ChargesheetBean>();
		List<Chargesheet> listChargesheet = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT c FROM Chargesheet c where c.infoname like '%"+q+"%' or c.infoadd like '%"+q+"%' or c.infoocc like '%"+q+"%' or c.infoperticular like '%"+q+"%' or c.accusname like '%"+q+"%' or c.accaddress like '%"+q+"%'";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + "or c.chargesheetid = :chargesheetid";
				
				
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			
			if(bno){
				
				query.setParameter("chargesheetid", no);
			}
						
			//query.setMaxResults(20);
			listChargesheet = query.getResultList();
						
			for (Iterator<Chargesheet> iterator = listChargesheet.iterator(); iterator.hasNext();) {
				Chargesheet chargesheet = (Chargesheet) iterator.next();
				
				ChargesheetBean bean = new ChargesheetBean();
				bean.setChargesheetid(chargesheet.getChargesheetid());
				bean.setAccaddress(chargesheet.getAccaddress());
				bean.setAccusname(chargesheet.getAccusname());
				bean.setChsdate(chargesheet.getChsdate());
				bean.setFirid(new FirBean(chargesheet.getFirid().getFirid()));
				bean.setInfoadd(chargesheet.getInfoadd());
				bean.setInfoname(chargesheet.getInfoname());
				bean.setInfoocc(chargesheet.getInfoocc());
				bean.setInfoperticular(chargesheet.getInfoperticular());
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public List<HostoryBean> searchHistoryServer(String q) {
		
		EntityManager em =null;
		List<HostoryBean> list = new ArrayList<HostoryBean>();
		List<Hostory> listHistory = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT h FROM Hostory h where h.placeofocc like '%"+q+"%' or h.description like '%"+q+"%'";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + " or h.historyid = :historyid";
				sq = sq + " or h.crimeno = :crimeno";
				
								
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			
			if(bno){
				
				query.setParameter("historyid", no);
				query.setParameter("crimeno", no);
			}
						
			//query.setMaxResults(20);
			listHistory = query.getResultList();
						
			for (Iterator<Hostory> iterator = listHistory.iterator(); iterator.hasNext();) {
				
				Hostory history = (Hostory) iterator.next();
				
				HostoryBean bean = new HostoryBean();
				bean.setHistoryid(history.getHistoryid());
				bean.setCrimeno(history.getCrimeno());
				bean.setCrimetypeid(new CrimetypeBean(history.getCrimetypeid().getCrimetypeid(), history.getCrimetypeid().getCrimename()));
				bean.setDateofocc(history.getDateofocc());
				bean.setDescription(history.getDescription());
				bean.setPlaceofocc(history.getPlaceofocc());
				PrisonerBean bean2 = new PrisonerBean(history.getPrisonerid().getPrisonerid());
				bean2.setFamilyname(history.getPrisonerid().getFamilyname());
				bean.setPrisonerid(bean2);
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public List<CriminalBean> searchCriminalServer(String q) {
		
		EntityManager em =null;
		List<CriminalBean> list = new ArrayList<CriminalBean>();
		List<Criminal> listCriminal = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT c FROM Criminal c where c.name like '%"+q+"%' or c.nickname like '%"+q+"%' or c.occupation like '%"+q+"%' or c.address like '%"+q+"%' or c.description like '%"+q+"%'";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + " or c.criminalid = :criminalid";
				sq = sq + " or c.age = :age";
				
				
								
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			
			if(bno){
				
				query.setParameter("criminalid", no);
				query.setParameter("age", no);
			}
						
			//query.setMaxResults(20);
			listCriminal = query.getResultList();
						
			for (Iterator<Criminal> iterator = listCriminal.iterator(); iterator.hasNext();) {
				
				Criminal criminal = (Criminal) iterator.next();
				
				CriminalBean bean = new CriminalBean();
				bean.setCriminalid(criminal.getCriminalid());
				bean.setAddress(criminal.getAddress());
				bean.setAge(criminal.getAge());
				bean.setCrimetypeid(new CrimetypeBean(criminal.getCrimetypeid().getCrimetypeid(), criminal.getCrimetypeid().getCrimename()));
				bean.setDescription(criminal.getDescription());
				bean.setGender(criminal.getGender());
				bean.setIsmostwanted(criminal.getIsmostwanted());
				bean.setName(criminal.getName());
				bean.setNickname(criminal.getNickname());
				bean.setOccupation(criminal.getOccupation());
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public List<PostmortamBean> searchPMServer(String q) {
		
		EntityManager em =null;
		List<PostmortamBean> list = new ArrayList<PostmortamBean>();
		List<Postmortam> listPostmortam = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT p FROM Postmortam p where p.reslt like '%"+q+"%' or p.casedesc like '%"+q+"%' or p.homename like '%"+q+"%' or p.doctorname like '%"+q+"%' or p.policestation like '%"+q+"%'";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + " or p.pmid = :pmid";
				sq = sq + " or p.firid.firid = :firid";
				
				
				
								
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			
			if(bno){
				
				query.setParameter("pmid", no);
				query.setParameter("firid", no);
				
			}
						
			//query.setMaxResults(20);
			listPostmortam = query.getResultList();
						
			for (Iterator<Postmortam> iterator = listPostmortam.iterator(); iterator.hasNext();) {
				
				Postmortam postm = (Postmortam) iterator.next();
				
				PostmortamBean bean = new PostmortamBean();
				bean.setPmid(postm.getPmid());
				bean.setFirid(new FirBean(postm.getFirid().getFirid()));
				bean.setCasedesc(postm.getCasedesc());
				bean.setDeathdate(postm.getDeathdate());
				bean.setDoctorname(postm.getDoctorname());
				bean.setGender(postm.getGender());
				bean.setHomename(postm.getHomename());
				bean.setPolicestation(postm.getPolicestation());
				bean.setReslt(postm.getReslt());
				
				
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public List<PrisonerBean> searchPrisonerServer(String q) {
		
		EntityManager em =null;
		List<PrisonerBean> list = new ArrayList<PrisonerBean>();
		List<Prisoner> listPrisoner = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			em = emf.createEntityManager();
			
			String sq = "SELECT p FROM Prisoner p where p.nickname like '%"+q+"%' or p.familyname like '%"+q+"%' or p.identitymark like '%"+q+"%' or p.color like '%"+q+"%'  or p.height = :height  or p.weight = :weight";
			
			boolean bno = true;
			int no=0;
			try{
				
				no = Integer.parseInt(q);
				sq = sq + " or p.prisonerid = :prisonerid";
				sq = sq + " or p.chargesheetid.chargesheetid = :chargesheetid";
				
				
				
				
								
			}catch(NumberFormatException e){
				
				bno = false;
			}
			Query query = em.createQuery(sq);
			query.setParameter("height", q);
			query.setParameter("weight", q);
			
			if(bno){
				
				query.setParameter("prisonerid", no);
				query.setParameter("chargesheetid", no);
				
				
			}
						
			//query.setMaxResults(20);
			listPrisoner = query.getResultList();
						
			for (Iterator<Prisoner> iterator = listPrisoner.iterator(); iterator.hasNext();) {
				
				Prisoner prisoner = (Prisoner) iterator.next();
				
				PrisonerBean bean = new PrisonerBean();
				bean.setPrisonerid(prisoner.getPrisonerid());
				bean.setChargesheetid(new ChargesheetBean(prisoner.getChargesheetid().getChargesheetid()));
				bean.setColor(prisoner.getColor());
				bean.setFamilyname(prisoner.getFamilyname());
				bean.setFirdate(prisoner.getFirdate());
				bean.setHeight(prisoner.getHeight());
				bean.setIdentitymark(prisoner.getIdentitymark());
				bean.setNickname(prisoner.getNickname());
				bean.setStatus(prisoner.getStatus());
				bean.setWeight(prisoner.getWeight());
				list.add(bean);
				
			}
						
			
		}catch(Exception e) {
			
			e.printStackTrace();
						
		}
		
		finally {
						
			em.close();
		}
		
		return list;
	}

	@Override
	public String[] saveUserServer(String userid, String new1) {
		
		String status[]={"",""};
		Login entity = null;
		
		try {
		
			EntityManagerFactory emf = EMF.get();
			LoginJpaController controller = new LoginJpaController(emf);
				
			entity = new Login();
			entity.setPassword(new1);
			entity.setUserid(userid);
			entity.setRole("user");
				
								
				controller.create(entity);
				
			
					
			status[0] = "true";
			status[1] = ""+entity.getUserid();
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
			status[0] = "error";
			status[1] = "Error:"+e.getLocalizedMessage();
			
		}
		
		finally {
						
			
		}
		
		return status;
	}
}
