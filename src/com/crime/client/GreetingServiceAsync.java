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
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void createUserServer(boolean isAdmin, String user, String pass1,
			AsyncCallback<String> asyncCallback);

	void loadFirTypeServer(AsyncCallback<List<FirtypeBean>> asyncCallback);

	void loadRegionServer(AsyncCallback<List<FirregionBean>> asyncCallback);

	void loadCrimetypeServer(AsyncCallback<List<CrimetypeBean>> asyncCallback);

	void loadFirServer(AsyncCallback<List<FirBean>> asyncCallback);

	void loadChshServer(AsyncCallback<List<ChargesheetBean>> asyncCallback);

	void loadPrisonerServer(AsyncCallback<List<PrisonerBean>> asyncCallback);

	void saveFirServer(FirBean bean, AsyncCallback<String[]> asyncCallback);

	void saveChShServer(ChargesheetBean bean,
			AsyncCallback<String[]> asyncCallback);

	void listChShServer(AsyncCallback<List<ChargesheetBean>> asyncCallback);

	void savePMServer(PostmortamBean bean, AsyncCallback<String[]> asyncCallback);

	void listPMServer(int id, AsyncCallback<List<PostmortamBean>> asyncCallback);

	void savePrisonerServer(PrisonerBean bean,
			AsyncCallback<String[]> asyncCallback);

	void listPrisonerServer(int id,
			AsyncCallback<List<PrisonerBean>> asyncCallback);

	void saveCriminalServer(CriminalBean bean,
			AsyncCallback<String[]> asyncCallback);

	void listCriminalServer(int id,
			AsyncCallback<List<CriminalBean>> asyncCallback);

	void saveHistoryServer(HostoryBean bean,
			AsyncCallback<String[]> asyncCallback);

	void listHistoryServer(int id,
			AsyncCallback<List<HostoryBean>> asyncCallback);

	void loginServer(LoginBean loginBean, AsyncCallback<LoginBean> asyncCallback);

	void deleteFirServer(int id, AsyncCallback<String[]> asyncCallback);

	void deleteChshServer(int id, AsyncCallback<String[]> asyncCallback);

	void deletePMServer(int id, AsyncCallback<String[]> asyncCallback);

	void deletePrisonerServer(int id, AsyncCallback<String[]> asyncCallback);

	void deleteCriminalServer(int id, AsyncCallback<String[]> asyncCallback);

	void deleteHistoryServer(int id, AsyncCallback<String[]> asyncCallback);

	void searchFirServer(String q, AsyncCallback<List<FirBean>> asyncCallback);

	void searchChShServer(String q,
			AsyncCallback<List<ChargesheetBean>> asyncCallback);

	void searchHistoryServer(String q,
			AsyncCallback<List<HostoryBean>> asyncCallback);

	void searchCriminalServer(String q,
			AsyncCallback<List<CriminalBean>> asyncCallback);

	void searchPMServer(String q,
			AsyncCallback<List<PostmortamBean>> asyncCallback);

	void searchPrisonerServer(String q,
			AsyncCallback<List<PrisonerBean>> asyncCallback);

	void saveUserServer(String userid, String new1,
			AsyncCallback<String[]> asyncCallback);
}
