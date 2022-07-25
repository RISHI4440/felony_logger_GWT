package com.crime.client;

import java.util.List;

import com.crime.shared.ChargesheetBean;
import com.crime.shared.CrimetypeBean;
import com.crime.shared.CriminalBean;
import com.crime.shared.FirBean;
import com.crime.shared.FirregionBean;
import com.crime.shared.FirtypeBean;
import com.crime.shared.HostoryBean;
import com.crime.shared.LoginBean;
import com.crime.shared.PostmortamBean;
import com.crime.shared.PrisonerBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;

	String createUserServer(boolean isAdmin, String user, String pass1);

	List<FirtypeBean> loadFirTypeServer();

	List<FirregionBean> loadRegionServer();

	List<CrimetypeBean> loadCrimetypeServer();

	List<FirBean> loadFirServer();

	List<ChargesheetBean> loadChshServer();

	List<PrisonerBean> loadPrisonerServer();

	String[] saveFirServer(FirBean bean);

	String[] saveChShServer(ChargesheetBean bean);

	List<ChargesheetBean> listChShServer();

	String[] savePMServer(PostmortamBean bean);

	List<PostmortamBean> listPMServer(int id);

	String[] savePrisonerServer(PrisonerBean bean);

	List<PrisonerBean> listPrisonerServer(int id);

	String[] saveCriminalServer(CriminalBean bean);

	List<CriminalBean> listCriminalServer(int id);

	String[] saveHistoryServer(HostoryBean bean);

	List<HostoryBean> listHistoryServer(int id);

	LoginBean loginServer(LoginBean loginBean);

	String[] deleteFirServer(int id);

	String[] deleteChshServer(int id);

	String[] deletePMServer(int id);

	String[] deletePrisonerServer(int id);

	String[] deleteCriminalServer(int id);

	String[] deleteHistoryServer(int id);

	List<FirBean> searchFirServer(String q);

	List<ChargesheetBean> searchChShServer(String q);

	List<HostoryBean> searchHistoryServer(String q);

	List<CriminalBean> searchCriminalServer(String q);

	List<PostmortamBean> searchPMServer(String q);

	List<PrisonerBean> searchPrisonerServer(String q);

	String[] saveUserServer(String userid, String new1);
}
