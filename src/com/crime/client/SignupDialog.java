package com.crime.client;


import com.crime.shared.LoginBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SignupDialog extends LazyPanel{
	
	FlexTable flex;
	Label label;
	PasswordTextBox tb_new1, tb_new2;
	TextBox tb_user;
	Button b_register, b_close;
	VerticalPanel vp;
	ClosableDialogBox box ;
	LoginBean bean;
	
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	
	public SignupDialog() {
	}
	
	@Override
	protected Widget createWidget() {
				
	    box = new ClosableDialogBox("<img src='img/tag_purple.png'></img> <span style='color:#189ec9;letter-spacing:1px'>Signup</span>", true);
		box.setAnimationEnabled(true);
		box.setGlassEnabled(true);
		vp = new VerticalPanel();
				
		flex = new FlexTable();
		flex.setWidth("100%");
		flex.setCellPadding(5);
		flex.setCellSpacing(0);
				
		FlexCellFormatter formatter = flex.getFlexCellFormatter();
		
		label = new Label("User Id");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_user = new TextBox();
		tb_user.setStyleName("textbox");
		
		flex.setWidget(1, 1, tb_user);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Password");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		tb_new1 = new PasswordTextBox();
		tb_new1.setStyleName("textbox");
		
		flex.setWidget(2, 1, tb_new1);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Re Password");
		label.setStyleName("label");
		label.setWidth("120px");
		flex.setWidget(3, 0, label);
		
		tb_new2 = new PasswordTextBox();
		tb_new2.setStyleName("textbox");
		
		flex.setWidget(3, 1, tb_new2);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				
		b_register = new Button("Register");
		b_register.setWidth("250px");
		b_register.setStyleName("cwt-Button");
		b_register.setAccessKey('C');
		b_register.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				String userid = tb_user.getText();
				String new1 = tb_new1.getText();
				String new2 = tb_new2.getText();
				
				if(userid.isEmpty()||new1.isEmpty()||new2.isEmpty()){
					
					Window.alert("All Fileds are compulsory");
					tb_user.setFocus(true);
					
				}else {
					
					if(new1.equals(new2)){
						
						greetingService.saveUserServer(userid, new1, new AsyncCallback<String[]>() {
							
							@Override
							public void onSuccess(String[] result) {
								
								
								
								if(result[0].equals("true")) {
									
									Window.alert("Signup Successfull");
									tb_user.setText("");
									tb_new1.setText("");
									tb_new2.setText("");
									tb_user.setFocus(true);
									
									
								}else {
								
									
									Window.alert("Failed");
									tb_user.setFocus(true);
								}
								
							}
							
							@Override
							public void onFailure(Throwable caught) {
								
								ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
								box.show();
								
							}
						});
												
					}else {
					
						Window.alert("Both Passwords should be same");
						tb_new1.setText("");
						tb_new2.setText("");
						tb_new1.setFocus(true);
						
					}
					
				}
				
				
			}
		});
		
		flex.setWidget(4, 0, b_register);
		formatter.setColSpan(4, 0, 2);
		formatter.setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		vp.add(flex);
		
		box.setWidget(vp);
		box.center();
		box.show();
		
		tb_user.setFocus(true);
		
		return box;
	}

}
