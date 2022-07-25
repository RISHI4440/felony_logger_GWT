package com.crime.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadingPopup{

	static HTML html;
	static PopupPanel popup;
	
	public static void showLoading(){
		
		if(popup==null){
		
			popup = new PopupPanel();
			popup.setStyleName("loadingPopup");
			popup.setGlassEnabled(true);
			popup.setSize("100px", "100px");
			
			html = new HTML("<div class='loading-wrap'><div class='triangle1'></div><div class='triangle2'></div><div class='triangle3'></div></div>");
			html.setSize("100px", "100px");
			
			popup.setWidget(html);
			
		}
		
		popup.center();
		popup.show();
		
		
		
	}
	
	public static void hideLoading(){
		
		if(popup!=null&&popup.isShowing())
		popup.hide();
	}
	

}
