package com.crime.client;

import com.crime.shared.LoginBean;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CrimeLogger implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	
	DockLayoutPanel p;
	LoginBean loginBean = null;

	// header
	Anchor anc_register, anc_forgot;
	HorizontalPanel hp_setting, hp_logo, hp_settingl, hp_settingr;
	VerticalPanel vp;
	Button anc_signin;
	
	// footer
	Anchor anc_about, anc_terms, anc_privacy, anc_home, anc_voucher;
	HorizontalPanel hp;
	PopupPanel popupLogin;
	boolean isLoggenIn;
	
	//Anchor anc_ledger, anc_testorder, anc_reg, anc_patient, anc_holder, anc_permit, anc_testoreport, anc_voucher;
	Image img_home, img_cog;
		
	Anchor anc_fir, anc_criminal, anc_chsh, anc_prisoner, anc_pm ,anc_hist;

	// Navigation
	VerticalPanel vp_west, vp_east;
	LoginPanel loginPanel;
	
	private Image img_fir;

	private Image img_chsh;

	private Image img_pm;

	private Image img_prisoner;

	private Image img_criminal;

	private Image img_history;

	public void onModuleLoad() {

			p = new DockLayoutPanel(Unit.PX);
			p.setStyleName("dock");

			loginPanel = new LoginPanel();
			loginPanel.button_login.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					
					
					login();
					
					
					
				}
			});
			
			/* this code should be added */
			p.add(loginPanel);
			
			/* the code s hould be removed */
			RootPanel.get("logo").getElement().setInnerHTML("");
			//p.setStyleName("dock2");
			
//			p.addNorth(createHeader(), 40);
//			p.addSouth(createFooter(), 20);
//			p.addWest(createWest(), 41);
//			ContentPanel cPanel = new ContentPanel();
//			p.add(cPanel.createContent());

			RootLayoutPanel.get().add(p);
			RootPanel.get("main").clear();
			loginPanel.box_user.setFocus(true);
						
			
			Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
			    @Override
			    public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
			        NativeEvent nativeEvent = event.getNativeEvent();
			        // Do all sort of cool stuff with nativeEvent
			         
			        
//			        if(nativeEvent.getAltKey()&&(nativeEvent.getKeyCode()==77)) {
//			        	
//			        	
//			        }
			    }
			});
						 			
		}
	
private void login(){
		
		StatusPopup.hideStatus();
		LoadingPopup.showLoading();
				
		if(loginPanel.box_user.getText().trim().isEmpty()||loginPanel.box_password.getText().trim().isEmpty()){
			
			StatusPopup.showStatus("User / Password cannot be empty");
			loginPanel.box_password.setFocus(true);
			
			LoadingPopup.hideLoading();
			
		}else {
			
			loginBean = new LoginBean();
			loginBean.setUserid(loginPanel.box_user.getText());
			loginBean.setPassword(loginPanel.box_password.getValue());
			
			greetingService.loginServer(loginBean, new AsyncCallback<LoginBean>() {
				
				@Override
				public void onSuccess(LoginBean result) {
					
					LoadingPopup.hideLoading();
					loginBean = result;
					
					
					
					if(loginBean!=null) {
																		
						//remove logo
						RootPanel.get("logo").getElement().setInnerHTML("");
						
						p.remove(0);
						p.setStyleName("dock2");
						
						p.addNorth(createHeader(), 40);
						p.addSouth(createFooter(), 20);
						p.addWest(createWest(), 41);
						//p.addEast(createEast(), 150);
						
						ContentPanel cPanel = new ContentPanel();
						p.add(cPanel.createContent());
						
						LoadingPopup.hideLoading();
						
												
					}else {
					
						LoadingPopup.hideLoading();
						StatusPopup.showStatus("invalid user or password");
						loginPanel.box_password.setText("");
						loginPanel.box_password.setFocus(true);
						
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
					box.show();
					LoadingPopup.hideLoading();
					
				}
			});
			
			
			
			
		}

	}

	public Widget createHeader() {

		vp = new VerticalPanel();
		// vp.setWidth("100%");
		// vp.setHeight("70px");
		vp.setStyleName("header");
		// vp.setBorderWidth(1);
		
		
		// Logo Panel
		hp_logo = new HorizontalPanel();
		hp_logo.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		//hp_logo.setBorderWidth(1);
		hp_logo.setStyleName("logo");
		Image logo = new Image("images/logo.png");
		hp_logo.add(logo);
		hp_logo.setCellWidth(logo, "150px");
		
		HorizontalPanel hpMenu = new HorizontalPanel();
		hp_logo.add(hpMenu);
		//hpMenu.setSize(
		
		
		//Menu Part
		anc_fir = new Anchor("FIR");
		anc_fir.setStyleName("menu");
		hpMenu.add(anc_fir);
		anc_fir.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				FirPanel panel = new FirPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		anc_chsh = new Anchor("CHARGESHEET");
		anc_chsh.setStyleName("menu");
		hpMenu.add(anc_chsh);
		
		anc_chsh.addClickHandler(new  ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				ChargeSheetPanel panel = new ChargeSheetPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		anc_pm = new Anchor("POSTMORTEM");
		anc_pm.setStyleName("menu");
		hpMenu.add(anc_pm);
		anc_pm.addClickHandler(new  ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				PostMortemPanel panel = new PostMortemPanel();
				p.add(panel.createWidget());
				
			}
		});

		anc_prisoner = new Anchor("PRISONER");
		anc_prisoner.setStyleName("menu");
		hpMenu.add(anc_prisoner);
		anc_prisoner.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				PrisonerPanel panel = new PrisonerPanel();
				p.add(panel.createWidget());
				
			}
		});

		anc_criminal = new Anchor("CRIMINAL");
		anc_criminal.setStyleName("menu");
		hpMenu.add(anc_criminal);
		anc_criminal.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				CriminalPanel panel = new CriminalPanel();
				p.add(panel.createWidget());
				
			}
		});

		anc_hist = new Anchor("HISTORY");
		anc_hist.setStyleName("menu");
		hpMenu.add(anc_hist);
		anc_hist.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				HistoryPanel panel = new HistoryPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		
		
		
		//Login/Logout
		// login
		anc_signin = new Button("");
		anc_signin.setStyleName("logout");
		anc_signin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				// logout
				p.remove(0);
				p.remove(0);
				p.remove(0);
				p.remove(0);

				p.setStyleName("dock");
				loginPanel.vpMain.setSize("100%", "100%");
				loginPanel.vpMain
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				loginPanel.vpMain
						.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				p.add(loginPanel);

				RootPanel
						.get("logo")
						.getElement()
						.setInnerHTML(
								"<div id='bsaHolder'>CRIME LOGGER</div>");

			}
		});

		hp_logo.add(anc_signin);
		hp_logo.setCellHorizontalAlignment(anc_signin,
				HasHorizontalAlignment.ALIGN_RIGHT);
		
		vp.add(hp_logo);
		
		//vp.add(menu);

		

		return vp;
	}

	// footer
	public Widget createFooter() {

		hp = new HorizontalPanel();
		hp.setWidth("100%");
		hp.setStyleName("footer");
		hp.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		// left
		HorizontalPanel hpLeft = new HorizontalPanel();

		// right
		HorizontalPanel hpright = new HorizontalPanel();

		anc_about = new Anchor("d & d by Vishal Suryawanshi, Patil Sumit, Tejas Deore");
		anc_about.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.open("#", "_blank", "");

			}
		});
		hpright.add(anc_about);
		
		hp.add(hpright);
		hp.setCellHorizontalAlignment(hpright, HasHorizontalAlignment.ALIGN_RIGHT);

		// right
		return hp;
	}

	public Widget createWest() {

		vp_west = new VerticalPanel();
		//vp_west.setBorderWidth(1);
		vp_west.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		
		
		vp_west.setStyleName("navigation");
		
		
		VerticalPanel vpTop = new VerticalPanel();
		vp_west.add(vpTop);
		
		img_home = new Image("img/home.png");
		img_home.setTitle("Home");
		img_home.setStyleName("buttons");
		vpTop.add(img_home);
		img_home.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				ContentPanel panel = new ContentPanel();
				p.add(panel.createContent());
				
			}
		});
		
		img_fir = new Image("img/fir.png");
		img_fir.setTitle("FIR");
		img_fir.setStyleName("buttons");
		vpTop.add(img_fir);
		img_fir.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				FirPanel panel = new FirPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		img_chsh = new Image("img/chsh.png");
		img_chsh.setTitle("Chargesheet");
		img_chsh.setStyleName("buttons");
		vpTop.add(img_chsh);
		img_chsh.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				ChargeSheetPanel panel = new ChargeSheetPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		img_pm = new Image("img/postm.png");
		img_pm.setTitle("Post Mortem");
		img_pm.setStyleName("buttons");
		vpTop.add(img_pm);
		img_pm.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				PostMortemPanel panel = new PostMortemPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		img_prisoner = new Image("img/prisoner.png");
		img_prisoner.setTitle("Prisoner");
		img_prisoner.setStyleName("buttons");
		vpTop.add(img_prisoner);
		img_prisoner.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				PrisonerPanel panel = new PrisonerPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		img_criminal = new Image("img/criminal.png");
		img_criminal.setTitle("Criminal");
		img_criminal.setStyleName("buttons");
		vpTop.add(img_criminal);
		img_criminal.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				CriminalPanel panel = new CriminalPanel();
				p.add(panel.createWidget());
				
			}
		});
		
		img_history = new Image("img/history.png");
		img_history.setTitle("History");
		img_history.setStyleName("buttons");
		vpTop.add(img_history);
		img_history.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				p.remove(3);
				HistoryPanel panel = new HistoryPanel();
				p.add(panel.createWidget());
				
			}
		});
		

		VerticalPanel vpBot = new VerticalPanel();
		//vpBot.setBorderWidth(1);
		vp_west.add(vpBot);
		vp_west.setCellVerticalAlignment(vpBot, HasVerticalAlignment.ALIGN_BOTTOM);
		
		img_cog = new Image("img/cog.png");
		img_cog.setStyleName("buttons");
		vpBot.add(img_cog);
		img_cog.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				UserDialog dialog = new UserDialog(loginBean);
				dialog.createWidget();
				
				
				
			}
		});
		
		

		
		return vp_west;
	}

	public Widget createEast() {

		vp_east = new VerticalPanel();
		// vp_east.setBorderWidth(1);
		// vp_east.setWidth("250px");
		// vp_east.setHeight("500px");

		vp_east.setStyleName("navigation");

		vp_east.add(new HTML("east"));

		return vp_east;
	}

}



