package com.crime.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;

public class StatusPopup{
	
	static FlexTable flex;
	static DecoratedPopupPanel popupPanel;
	static HTML html;
	boolean isShowing = false;

	public static void showStatus(String s){
		
		if(popupPanel==null){
		
			popupPanel = new DecoratedPopupPanel(true);
			popupPanel.setAnimationEnabled(true);
			//popupPanel.setGlassEnabled(true);
			popupPanel.setStyleName("statuspop");
			//popupPanel.setPopupPosition(2, 2);
			
			flex = new FlexTable();
			//flex.setStyleName("");
			flex.setSize("350px", "24px");
			
			html = new HTML("");
			html.setStyleName("status");
			flex.setWidget(0, 0, html);
						
			popupPanel.setWidget(flex);
			popupPanel.setPopupPosition(Window.getClientWidth()/2-175, 0);
			
			
		}
		
		
		html.setText(s);
		popupPanel.setVisible(true);
		popupPanel.show();
		
//		Timer timer = new Timer() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				popupPanel.setVisible(false);
//			}
//		};
//		timer.schedule(1500);
		
		
	}

	public static void hideStatus(){
		
		if(popupPanel!=null&&popupPanel.isShowing()){
		
			popupPanel.setVisible(false);
		}
		
	}
	
	
	

}
