package com.crime.client;

import java.util.Iterator;
import java.util.List;

import com.crime.shared.CrimetypeBean;
import com.crime.shared.FirBean;
import com.crime.shared.FirregionBean;
import com.crime.shared.FirtypeBean;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LazyPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.tractionsoftware.gwt.user.client.ui.UTCTimeBox;

public class FirPanel extends LazyPanel{
	
	List<FirBean> listFir;
	
	FirBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	DateBox db_fir;
	UTCTimeBox utc_time;
	UTCTimeBox utc_rtime;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_place, tb_police, tb_infor, tb_dist;
	CheckBox cb_status;
	ListBox lb_firType;
	
		
	DataGrid<FirBean> dataGrid;
	List<FirtypeBean> listFirType;
	List<FirregionBean> listFirregion;
	List<CrimetypeBean> listCrimetype;
	
	Button b_new, b_save,b_delete, b_open;
	public boolean isUpdate;
	public boolean isSave;
	public boolean isReset = false;
	ClosableDialogBox box;
	int row = 0;
	
	Label l_title;
	int id=0;
		
	FormWidget formWidget;
		
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private TextArea tb_info;

	

	private ListBox lb_reg;

	private ListBox lb_ct;
	
	public FirPanel() {
		
		
	}
	
	@Override
	protected Widget createWidget() {
				
		
		dp = new DockLayoutPanel(Unit.PX);
		dp.setStyleName("dock3");
		
		dp.addNorth(createHeader(), 35);
		dp.add(createData());
				
		formWidget.buttonNew.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				reset();
				
				
			}
		});
		
		formWidget.buttonSave.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				//Window.alert("Save");
				save();
			}
		});

		formWidget.buttonDel.addClickHandler(new ClickHandler() {
	
			@Override
			public void onClick(ClickEvent event) {
		
				delete();
			}
		});
		
		formWidget.buttonNext.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
		
				row++;				
				navigate();
			}
		});
		
		formWidget.buttonPre.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
		
				row--;
				navigate();
			}
		});

		formWidget.buttonFirst.addClickHandler(new ClickHandler() {
	
			@Override
			public void onClick(ClickEvent event) {

				row = 0;
				navigate();
			}
		});

formWidget.buttonLast.addClickHandler(new ClickHandler() {
	
	@Override
	public void onClick(ClickEvent event) {

		row = dataGrid.getRowCount();
		navigate();
	}
});
		
		
		
		//load companies
		list();
		
		return dp;
	}
	
	private void navigate(){
		
		if(row<0){
			
			row = 0;
		}
		
		if(row>dataGrid.getRowCount()){
			
			row = dataGrid.getRowCount();
		}
		
		dataGrid.setKeyboardSelectedRow(row);
	}
	
	private Widget createData() {
		
		formWidget = new FormWidget();
				
		return formWidget.createWidget(createForm(), "350px", "");
		
	}
	
	
	
	private Widget createHeader() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("controls");
		
		/* left panel */
		
		HorizontalPanel hp1 = new HorizontalPanel();
		hp.add(hp1);
		
		l_title = new Label("FIR");
		l_title.setStyleName("title");
		hp1.add(l_title);
		
		anc_manage = new Anchor("Manage");
		anc_manage.setStyleName("mynew");
		anc_manage.setAccessKey('M');
		anc_manage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				
				
			}
		});
		hp1.add(anc_manage);
		
		
		/* right Panel */
		HorizontalPanel hp2 = new HorizontalPanel();
		//hp2.setBorderWidth(1);
		hp.add(hp2);
		hp.setCellHorizontalAlignment(hp2, HasHorizontalAlignment.ALIGN_RIGHT);
		
		anc_list = new Anchor("List");
		anc_list.setStyleName("mylist");
		anc_list.setAccessKey('S');
		anc_list.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
                 list();	
			}
		});
		hp2.add(anc_list);
		
		/* search */
		tb_search = new TextBox();
		tb_search.setStyleName("searchbox");
		tb_search.addKeyDownHandler(new KeyDownHandler() {
			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				
				int code = event.getNativeKeyCode();
				
				if(code==13) {
				
					String q = tb_search.getText();
					
					if(!q.isEmpty()) {
						
						search(q);
					}
					
				}
				
			}
		});

		hp2.add(tb_search);
		hp2.setCellVerticalAlignment(tb_search, HasVerticalAlignment.ALIGN_MIDDLE);
		hp2.add(new HTML("&nbsp;&nbsp;"));
		
		anc_search = new Anchor("Search");
		anc_search.setStyleName("mysearch");
		anc_search.setAccessKey('S');
		anc_search.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				String q = tb_search.getText();
				
				if(!q.isEmpty()) {
					
					search(q);
				}
				
			}
		});
		hp2.add(anc_search);
		
		
		
		return hp;
		
	}
	
	
	
	private Widget createForm() {
		
		vp = new VerticalPanel();
				
		flex = new FlexTable();
		flex.setWidth("100%");
		flex.setCellPadding(5);
		flex.setCellSpacing(0);
				
		FlexCellFormatter formatter = flex.getFlexCellFormatter();
		
				
		Label label = new Label("FIr ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("FIr Date");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		db_fir = new DateBox();
		db_fir.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		db_fir.setStyleName("datebox");
		flex.setWidget(2, 1, db_fir);
		formatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("FIr Time");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
		
		utc_time = new UTCTimeBox();
		utc_time.setStyleName("datebox");
		
		flex.setWidget(3, 1, utc_time);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_CENTER);
		
//		db_time = new DateBox();
//		db_time.setStyleName("datebox");
//		db_time.setFormat(new DateBox.DefaultFormat(DateTimeFormat
//				.getFormat("HH:mm")));
//		flex.setWidget(3, 1, db_time);
//		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
//		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				
		//customer name
		label = new Label("Place");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
			
		tb_place = new TextBox();
		tb_place.setStyleName("textbox");
		tb_place.addStyleName("upper");
						
		flex.setWidget(4, 1, tb_place);
		formatter.setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("District");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		tb_dist = new TextBox();
		tb_dist.setStyleName("textbox");
		tb_dist.addStyleName("upper");
						
		flex.setWidget(5, 1, tb_dist);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Police Name");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_police = new TextBox();
		tb_police.setStyleName("textbox");
		tb_police.addStyleName("upper");
						
		flex.setWidget(6, 1, tb_police);
		formatter.setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Informant");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
		
		tb_infor = new TextBox();
		tb_infor.setStyleName("textbox");
		tb_infor.addStyleName("upper");
						
		flex.setWidget(7, 1, tb_infor);
		formatter.setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Recieved Time");
		label.setStyleName("label");
		flex.setWidget(8, 0, label);
		
		utc_rtime = new UTCTimeBox();
		utc_rtime.setStyleName("datebox");
		utc_rtime.setStyleName("datebox");
		
		flex.setWidget(8, 1, utc_rtime);
		formatter.setVerticalAlignment(8, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Information");
		label.setStyleName("label");
		flex.setWidget(9, 0, label);
		
		tb_info = new TextArea();
		tb_info.setHeight("80px");
		tb_info.setStyleName("textbox");
				
		flex.setWidget(9, 1, tb_info);
		formatter.setHorizontalAlignment(9, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Fir Type");
		label.setStyleName("label");
		flex.setWidget(10, 0, label);
		
		lb_firType = new ListBox();
		lb_firType.setStyleName("listbox");
		loadFirtypeList();
				
		flex.setWidget(10, 1, lb_firType);
		formatter.setVerticalAlignment(10, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(10, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Fir Region");
		label.setStyleName("label");
		flex.setWidget(11, 0, label);
		
		lb_reg = new ListBox();
		lb_reg.setStyleName("listbox");
		loadRegionList();
				
		flex.setWidget(11, 1, lb_reg);
		formatter.setVerticalAlignment(11, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(11, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Crime Type");
		label.setStyleName("label");
		flex.setWidget(12, 0, label);
		
		lb_ct = new ListBox();
		lb_ct.setStyleName("listbox");
		loadCrimetypeList();
				
		flex.setWidget(12, 1, lb_ct);
		formatter.setVerticalAlignment(12, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(12, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		 label = new Label("Status");
		 label.setStyleName("label");
		 flex.setWidget(13, 0, label);
		
		 cb_status = new CheckBox();
		 flex.setWidget(13, 1, cb_status);
		 		
		 
		 
		 vp.add(flex);
				
		return vp;
	}
	
	private void loadFirtypeList(){
		
		greetingService.loadFirTypeServer(new AsyncCallback<List<FirtypeBean>>() {
			
			@Override
			public void onSuccess(List<FirtypeBean> result) {
				
				
				listFirType = result;
				
				for (Iterator<FirtypeBean> iterator = result.iterator(); iterator
						.hasNext();) {
					FirtypeBean entityBean = (FirtypeBean) iterator.next();
					
					lb_firType.addItem(entityBean.getTypename());
					
				}
				
				if(listFirType.size()>0){
					
					lb_firType.setSelectedIndex(-1);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
	}
	
	private void search(String q) {
		
		greetingService.searchFirServer(q, new AsyncCallback<List<FirBean>>() {
			
			@Override
			public void onSuccess(List<FirBean> result) {
				
		
				try{
					listFir = result;
					
					displayList();
					
					
				}finally{
					LoadingPopup.hideLoading();
				}
				
				
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
		
		
	}
	
	private void loadRegionList(){
		
		greetingService.loadRegionServer(new AsyncCallback<List<FirregionBean>>() {
			
			@Override
			public void onSuccess(List<FirregionBean> result) {
				
				
				listFirregion = result;
				
				for (Iterator<FirregionBean> iterator = result.iterator(); iterator
						.hasNext();) {
					FirregionBean entityBean = (FirregionBean) iterator.next();
					
					lb_reg.addItem(entityBean.getRegionname());
					
				}
				
				if(listFirregion.size()>0){
					
					lb_reg.setSelectedIndex(-1);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
	}
	
	private void loadCrimetypeList(){
		
		greetingService.loadCrimetypeServer(new AsyncCallback<List<CrimetypeBean>>() {
			
			@Override
			public void onSuccess(List<CrimetypeBean> result) {
				
				
				listCrimetype = result;
				
				for (Iterator<CrimetypeBean> iterator = result.iterator(); iterator
						.hasNext();) {
					CrimetypeBean entityBean = (CrimetypeBean) iterator.next();
					
					lb_ct.addItem(entityBean.getCrimename());
					
				}
				
				if(listCrimetype.size()>0){
					
					lb_ct.setSelectedIndex(-1);
				}
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
	}
	
	
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			utc_time.setValue(null);
			db_fir.setValue(null);
			tb_place.setText("");
			tb_dist.setText("");
			tb_police.setText("");
			tb_infor.setText("");
			utc_rtime.setValue(null);
			tb_info.setText("");
			
			lb_firType.setSelectedIndex(-1);
			lb_ct.setSelectedIndex(-1);
			lb_reg.setSelectedIndex(-1);
			
			db_fir.setFocus(true);
						
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listFir.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listFir.get(i), false);
				}
				
				
			}
			
			
			tb_place.setFocus(true);
			
		
	}
	private void save() {
		
		if(tb_place.getText().isEmpty()||db_fir.getValue()==null||utc_rtime.getValue()==null||utc_time.getValue()==null||tb_police.getText().isEmpty()||tb_info.getText().isEmpty()) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new FirBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setFirid(Integer.parseInt(tb_id.getText()));
			}
			bean.setCrimetypeid(listCrimetype.get(lb_ct.getSelectedIndex()));
			bean.setDistrict(tb_dist.getText());
			bean.setFirdate(db_fir.getValue());
			bean.setFirregionid(listFirregion.get(lb_reg.getSelectedIndex()));
			bean.setFirtime(utc_time.getText());
			bean.setFirtypeid(listFirType.get(lb_firType.getSelectedIndex()));
			bean.setInforecieved(tb_info.getText());
			bean.setInformantname(tb_infor.getText());
			bean.setPlace(tb_place.getText());
			bean.setPolicename(tb_police.getText());
			bean.setRecievedtime(utc_rtime.getText());
			
			if(cb_status.getValue()){
			
				bean.setStatus(1);
			}else{
				bean.setStatus(0);
			}
								
			greetingService.saveFirServer(bean, new AsyncCallback<String[]>() {
				
				@Override
				public void onSuccess(String[] result) {
					
					if(result[0].equals("true")) {
						tb_id.setText(result[1]);
						StatusPopup.showStatus("Saved Successfully");
						list();
																		
					}else {
						
						ErrorBox box = new ErrorBox(result[1]);
						box.show();
						
					}
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					
					ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
					box.show();
					
				}
			});
			
		}
}				
	
					
	private void delete(){
		
		boolean isDel = Window.confirm("Do you want to delete?");
		
		if(isDel){
			
				id = listFir.get(dataGrid.getKeyboardSelectedRow()).getFirid();
				
				
				greetingService.deleteFirServer(id, new AsyncCallback<String[]>() {
					
					@Override
					public void onSuccess(String[] result) {
						
						if(result[0].equals("true")) {
							tb_id.setText(result[1]);
							
							StatusPopup.showStatus("Deleted Successfully");
													
							reset();
							list();
							//loadList();
													
						}else {
							
							ErrorBox box = new ErrorBox(result[1]);
							box.show();
							
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						
						ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
						box.show();
						
					}
				});
				
			
			
		}
		
		
	}
	private void list(){
		
		LoadingPopup.showLoading();
		greetingService.loadFirServer(new AsyncCallback<List<FirBean>>() {
			
			@Override
			public void onSuccess(List<FirBean> result) {
				
		
				try{
					listFir = result;
					
					displayList();
					
					
				}finally{
					LoadingPopup.hideLoading();
				}
				
				
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				ErrorBox box = new ErrorBox(caught.getLocalizedMessage());
				box.center();
				box.show();
			}
		});
		
	
	}
	
	private void displayList(){
		
		if(dataGrid==null){
			
			dataGrid = new DataGrid<FirBean>(20);
			dataGrid.setWidth("100%");
			dataGrid.setHeight("100%");
			
			// Do not refresh the headers and footers every time the data is updated.
		    dataGrid.setAutoHeaderRefreshDisabled(true);
		    
		    // Set the message to display when the table is empty.
		    dataGrid.setEmptyTableWidget(new Label("Empty"));
		    
		    // Attach a column sort handler to the ListDataProvider to sort the list.
		    ListHandler<FirBean> sortHandler =
		        new ListHandler<FirBean>(listFir);
		    
		    dataGrid.addColumnSortHandler(sortHandler);
		    
		    // Add a selection model so we can select cells.
		    final SelectionModel<FirBean> selectionModel = new SingleSelectionModel<FirBean>();
		    
		    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
		        .<FirBean> createDefaultManager());
		    
		    
		    
		    // Initialize the columns.
		    Column<FirBean, String> groupColumn = new 
					Column<FirBean, String>(new TextCell()) { 
						
						@Override 
						public String getValue(FirBean object) { 
							return object.getFirid()+""; 
						} 
					};
					
													
					dataGrid.addColumn(groupColumn, "Fir No");
					dataGrid.setColumnWidth(0, "200px");
		    
			groupColumn = new 
			Column<FirBean, String>(new TextCell()) { 
				
				@Override 
				public String getValue(FirBean object) { 
					return DateTimeFormat.getFormat("dd/MM/yyyy").format(object.getFirdate())+""; 
				} 
			};
			
											
			dataGrid.addColumn(groupColumn, "Date");
			dataGrid.setColumnWidth(0, "200px");
			groupColumn.setSortable(true);
			
			
						
			
			
			VerticalPanel vpData = new VerticalPanel();
			//vpData.setBorderWidth(1);
			vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
			//vpData.setSize("100%", "100%");
			vpData.add(dataGrid);
			
			
			
			formWidget.spList.setWidget(vpData);
			
			
		}
		
		dataGrid.setRowData(listFir);
	}
}
