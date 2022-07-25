package com.crime.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

public class LoginPanel extends Composite {

	Anchor anc_signup, anc_register, anc_forgot;
	public Button button_login;
	public TextBox box_user;
	public PasswordTextBox box_password;
	VerticalPanel vpMain;
	
	public LoginPanel() {
		
		vpMain = new VerticalPanel();
		vpMain.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vpMain.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		vpMain.setSize("100%", "100%");
		initWidget(vpMain);
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("topLine");
		vpMain.add(hp);
		vpMain.setCellHeight(hp, "4px");
		
		//form Panel
		VerticalPanel vp = new VerticalPanel();
		vp.setStyleName("login");
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("loginform");		
		flexTable.setCellPadding(5);
				//flexTable.setSize("155px", "70px");
		FlexCellFormatter formatter = flexTable.getFlexCellFormatter();
		
		SimplePanel sp = new SimplePanel();
		sp.setStyleName("user");
		flexTable.setWidget(0, 0, sp);
		formatter.setColSpan(0, 0, 2);
		
		Grid grid = new Grid(1, 2);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		flexTable.setWidget(1, 0, grid);
		formatter.setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		Label label = new Label("");
		label.setStyleName("usr");
		grid.setWidget(0, 0, label);
		
		box_user = new TextBox();
		box_user.setStyleName("userTB");
		grid.setWidget(0, 1, box_user);

		grid = new Grid(1, 2);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		flexTable.setWidget(2, 0, grid);
		formatter.setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_MIDDLE);		
		
		label = new Label("");
		label.setStyleName("pwd");
		grid.setWidget(0, 0, label);
		
		box_password = new PasswordTextBox();
		box_password.setStyleName("userTB");
		grid.setWidget(0, 1, box_password);
				
		
		button_login = new Button("Login");
		button_login.setStyleName("loginB");
		button_login.addStyleName("round");
				
		flexTable.setWidget(2, 1, button_login);
		formatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		HorizontalPanel hp2 = new HorizontalPanel();
		hp2.setBorderWidth(1);
		hp2.setSize("100%", "100%");
		
		HTML html = new HTML("");
		flexTable.setWidget(3, 0, html);
		
		anc_signup = new Anchor("SIGN UP", true);
		anc_signup.setStyleName("loganc");
		flexTable.setWidget(4, 0, anc_signup);
		anc_signup.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				SignupDialog dialog = new SignupDialog();
				dialog.createWidget();
				
			}
		});
		
		anc_forgot = new Anchor("forgot?", true);
		anc_forgot.setStyleName("loganc");
		flexTable.setWidget(4, 1, anc_forgot);
		anc_forgot.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				StatusPopup.showStatus("Please Contact Admin with your user ID");
				
			}
		});
		
		vp.add(flexTable);
		
		
				
		
		
		vpMain.add(vp);
		
		
	}

}
