package com.crime.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class FormWidget{

	public FlexTable flexMain, flexList;
	public VerticalPanel vpForm, vpDivider, vpList;
	ScrollPanel spForm, spList;
	public Label buttonNew, buttonSave, buttonDel, buttonNext, buttonPre, buttonFirst, buttonLast;
	public Button buttonHide;
	String formWidth, heading;
	int count=0;
	
	Label labelHead;
	boolean isHiding = false;
	
	public SimplePager.Resources pagerResources;
	public SimplePager pager;
	LayoutPanel layoutPanel;
	
	public Widget createWidget(Widget formWidget, String formWidth, String heading){
		
		this.formWidth = formWidth;
		this.heading = heading;
				
		flexMain = new FlexTable();
		flexMain.setCellPadding(0);
		flexMain.setCellSpacing(0);
		//flexMain.setBorderWidth(1);
		flexMain.setSize("100%", "100%");
		
		//Form
		flexMain.setWidget(0, 0, createForm(formWidget));
		flexMain.getFlexCellFormatter().setWidth(0, 0, formWidth);
		
		//Divider
		flexMain.setWidget(0, 1, createDivider());
		flexMain.getFlexCellFormatter().setWidth(0, 1, "8px");
				
		//List
		flexMain.setWidget(0, 2, createList());
		flexMain.getFlexCellFormatter().setWidth(0, 2, "100%");
		
		Event.addNativePreviewHandler(new Event.NativePreviewHandler() {
		    @Override
		    public void onPreviewNativeEvent(Event.NativePreviewEvent event) {
		        NativeEvent nativeEvent = event.getNativeEvent();
		        // Do all sort of cool stuff with nativeEvent
		        		        
		        if(nativeEvent.getAltKey()&&nativeEvent.getKeyCode()==78) {
		        
		        	//Window.alert("New");
		        	
		        	buttonNew.fireEvent( new GwtEvent<ClickHandler>() {
		                @Override
		                public com.google.gwt.event.shared.GwtEvent.Type<ClickHandler> getAssociatedType() {
		                	//Window.alert("first");
		                return ClickEvent.getType();
		                }
		                @Override
		                protected void dispatch(ClickHandler handler) {
		                	//Window.alert("second");
		                    handler.onClick(null);
		                }
		        	});
		        }else if(nativeEvent.getAltKey()&&nativeEvent.getKeyCode()==83){
		        	
		        	//Window.alert("Save");
		        	
		        	buttonSave.fireEvent( new GwtEvent<ClickHandler>() {
		                @Override
		                public com.google.gwt.event.shared.GwtEvent.Type<ClickHandler> getAssociatedType() {
		                return ClickEvent.getType();
		                }
		                @Override
		                protected void dispatch(ClickHandler handler) {
		                    handler.onClick(null);
		                }
		        	});
		        	
		        }else if(nativeEvent.getAltKey()&&nativeEvent.getKeyCode()==88) {
		        	
		        	buttonDel.fireEvent( new GwtEvent<ClickHandler>() {
		                @Override
		                public com.google.gwt.event.shared.GwtEvent.Type<ClickHandler> getAssociatedType() {
		                return ClickEvent.getType();
		                }
		                @Override
		                protected void dispatch(ClickHandler handler) {
		                    handler.onClick(null);
		                }
		        	});
		        }
		    }
		});
		
		return flexMain;
		
	}
	
	
	public Widget createForm(Widget formWidget){
		
		vpForm = new VerticalPanel();
		vpForm.setStyleName("formPanel");
		vpForm.setSize(formWidth, "100%");
		
		//Heading
		labelHead = new Label(heading);
		labelHead.setStyleName("labelHead");
		labelHead.setWidth(formWidth);
		
		spForm = new ScrollPanel(formWidget);
		spForm.setSize("100%", "100%");
		
		vpForm.add(spForm);
		
		//Buttons
		FlexTable flexB = new FlexTable();
		flexB.setCellPadding(0);
		flexB.setCellSpacing(0);
		flexB.setStyleName("bPanel");
		
			
		buttonNew = new Label("");
		buttonNew.setTitle("New");
		buttonNew.setStyleName("new-Button");
		flexB.setWidget(0, 0, buttonNew);
		
		buttonSave = new Label("");
		buttonSave.setTitle("Save");
		buttonSave.setStyleName("save-Button");
		flexB.setWidget(0, 1, buttonSave);
		
		buttonDel = new Label("");
		buttonDel.setTitle("Delete");
		buttonDel.setStyleName("del-Button");
		flexB.setWidget(0, 2, buttonDel);
		
		buttonFirst = new Label("");
		buttonFirst.setTitle("First");
		buttonFirst.setStyleName("first-Button");
		flexB.setWidget(0, 3, buttonFirst);
		
		buttonPre = new Label("");
		buttonPre.setTitle("Previous");
		buttonPre.setStyleName("pre-Button");
		flexB.setWidget(0, 4, buttonPre);
		
		buttonNext = new Label("");
		buttonNext.setTitle("Next");
		buttonNext.setStyleName("next-Button");
		flexB.setWidget(0, 5, buttonNext);
		
		buttonLast = new Label("");
		buttonLast.setTitle("Last");
		buttonLast.setStyleName("last-Button");
		buttonLast.addStyleName("noBorderR");
		flexB.setWidget(0, 6, buttonLast);
		
		vpForm.add(flexB);
		vpForm.setCellHeight(flexB, "25px");
		
		return vpForm;
	}
	
	public Widget createList() {
		
		vpList = new VerticalPanel();
		//vpList.setStyleName("listPanel");
		//vpList.setBorderWidth(1);
		vpList.setSize("100%", "100%");
		
		//List
		spList = new ScrollPanel();
		spList.setSize("100%", "100%");
		spList.setStyleName("listPanel");
		vpList.add(spList);
		
		//Pager
		// Create a Pager to control the table.
	    pagerResources = GWT.create(SimplePager.Resources.class);
	    pager = new SimplePager(TextLocation.CENTER, pagerResources, false, 0, true);
	    
	    HorizontalPanel hp = new HorizontalPanel();
	    hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
	    hp.setStyleName("pager");
	    hp.add(pager);
	    
	    vpList.add(hp);
	    vpList.setCellHorizontalAlignment(hp, HasHorizontalAlignment.ALIGN_CENTER);
	    vpList.setCellHeight(hp, "25px");
		
		
		return vpList;
		
	}
	
	public void setPager(DataGrid<?> dataGrid){
		
		pager.setDisplay(dataGrid);
	}
	
	public Widget createDivider(){
		
		buttonHide = new Button("");
		buttonHide.setStyleName("divider");
		buttonHide.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				if(isHiding){
					
					flexMain.setWidget(0, 0, vpForm);
					flexMain.getFlexCellFormatter().setWidth(0, 0, formWidth);
					isHiding = false;
													
					
				}else {
					
					flexMain.remove(vpForm);
					flexMain.getFlexCellFormatter().setWidth(0, 0, "0px");
					isHiding = true;
					
				}
			
				
			}
		});
		
		
		return buttonHide;
		
	}
	

}
