package com.crime.client;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.crime.shared.CrimetypeBean;
import com.crime.shared.FirBean;
import com.crime.shared.HostoryBean;
import com.crime.shared.PrisonerBean;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class HistoryPanel extends LazyPanel{
	
	List<HostoryBean> listHistory;
	List<CrimetypeBean> listCrimetype;
	List<PrisonerBean> listPrisoner;
	
	HostoryBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_cno, tb_place;
	ListBox lb_pris;
			
	DataGrid<HostoryBean> dataGrid;
	
	Button b_new, b_save,b_delete, b_open;
	public boolean isUpdate;
	public boolean isSave;
	public boolean isReset = false;
	ClosableDialogBox box;
	
	
	Label l_title;
	int id=0;
	int row = 0;
		
	FormWidget formWidget;
		
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	

		
		private TextArea tb_desc;

	private ListBox lb_ct;

	private DateBox db_occ;
	
	public HistoryPanel() {
		
		
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
	
	private Widget createData() {
		
		formWidget = new FormWidget();
				
		return formWidget.createWidget(createForm(), "350px", "");
		
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
	
	private Widget createHeader() {
		
		HorizontalPanel hp = new HorizontalPanel();
		hp.setStyleName("controls");
		
		/* left panel */
		
		HorizontalPanel hp1 = new HorizontalPanel();
		hp.add(hp1);
		
		l_title = new Label("History");
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
		
				
		Label label = new Label("History ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Prisoner");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		lb_pris = new ListBox();
		lb_pris.setStyleName("listbox");
		loadPrisonerList();
				
		flex.setWidget(2, 1, lb_pris);
		formatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Crime No");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
			
		tb_cno = new TextBox();
		tb_cno.setStyleName("textbox");
		tb_cno.addStyleName("upper");
						
		flex.setWidget(3, 1, tb_cno);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Crime Type");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
		
		lb_ct = new ListBox();
		lb_ct.setStyleName("listbox");
		flex.setWidget(4, 1, lb_ct);
		formatter.setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		loadCrimetypeList();
		
		label = new Label("Occ Date");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		db_occ = new DateBox();
		db_occ.setStyleName("datebox");
		db_occ.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flex.setWidget(5, 1, db_occ);
		formatter.setVerticalAlignment(5, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Place");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_place = new TextBox();
		tb_place.setStyleName("textbox");
		tb_place.addStyleName("upper");
						
		flex.setWidget(6, 1, tb_place);
		formatter.setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Description");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
		
		tb_desc = new TextArea();
		tb_desc.setHeight("80px");
		tb_desc.setStyleName("textbox");
				
		flex.setWidget(7, 1, tb_desc);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		
		
		
		
		
		
		vp.add(flex);
				
		return vp;
	}
	
	private void loadPrisonerList(){
		
		greetingService.loadPrisonerServer(new AsyncCallback<List<PrisonerBean>>() {
			
			@Override
			public void onSuccess(List<PrisonerBean> result) {
				
				
				listPrisoner = result;
				
				for (Iterator<PrisonerBean> iterator = result.iterator(); iterator
						.hasNext();) {
					PrisonerBean entityBean = (PrisonerBean) iterator.next();
					
					lb_pris.addItem(entityBean.getFamilyname()+"-"+entityBean.getNickname());
					
				}
				
				if(listPrisoner.size()>0){
					
					lb_pris.setSelectedIndex(-1);
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
	
	private void search(String q) {
		
greetingService.searchHistoryServer(q, new AsyncCallback<List<HostoryBean>>() {
			
			@Override
			public void onSuccess(List<HostoryBean> result) {
				
		
				try{
					listHistory = result;
					
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
		
		dataGrid = new DataGrid<HostoryBean>(20);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("100%");
		
		// Do not refresh the headers and footers every time the data is updated.
	    dataGrid.setAutoHeaderRefreshDisabled(true);
	    
	    // Set the message to display when the table is empty.
	    dataGrid.setEmptyTableWidget(new Label("Empty"));
	    
	    // Attach a column sort handler to the ListDataProvider to sort the list.
	    ListHandler<HostoryBean> sortHandler =
	        new ListHandler<HostoryBean>(listHistory);
	    
	    dataGrid.addColumnSortHandler(sortHandler);
	    
	    // Add a selection model so we can select cells.
	    final SelectionModel<HostoryBean> selectionModel = new SingleSelectionModel<HostoryBean>();
	    
	    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	        .<HostoryBean> createDefaultManager());
	    
	   
	    // Initialize the columns.
		Column<HostoryBean, String> idColumn = new 
		Column<HostoryBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(HostoryBean object) { 
				return object.getHistoryid()+""; 
			} 
		};
		dataGrid.addColumn(idColumn, "History ID");
		dataGrid.setColumnWidth(0, "100px");
		
		Column<HostoryBean, String> prColumn = new 
		Column<HostoryBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(HostoryBean object) { 
				return object.getPrisonerid().getPrisonerid()+"-"+object.getPrisonerid().getFamilyname(); 
			} 
		};
		dataGrid.addColumn(prColumn, "Prisoner");
		dataGrid.setColumnWidth(1, "200px");	
		
		Column<HostoryBean, String> cnColumn = new 
				Column<HostoryBean, String>(new TextCell()) { 
					
					@Override 
					public String getValue(HostoryBean object) { 
						return object.getCrimeno()+" "; 
					} 
				};
				dataGrid.addColumn(cnColumn, "Crime No");
				dataGrid.setColumnWidth(2, "100px");									
					
		
		Column<HostoryBean, String> crColumn = new 
		Column<HostoryBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(HostoryBean object) { 
				return object.getCrimetypeid().getCrimename(); 
			} 
		};
		dataGrid.addColumn(crColumn, "Crime");
		dataGrid.setColumnWidth(3, "200px");									

		Column<HostoryBean, String> plColumn = new 
		Column<HostoryBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(HostoryBean object) { 
				return object.getPlaceofocc(); 
			} 
		};
		dataGrid.addColumn(plColumn, "Place");
		dataGrid.setColumnWidth(4, "200px");
		
		Column<HostoryBean, String> deColumn = new 
		Column<HostoryBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(HostoryBean object) { 
				return object.getDescription(); 
			} 
		};
		dataGrid.addColumn(deColumn, "Description");
										
			
			
		VerticalPanel vpData = new VerticalPanel();
		//vpData.setBorderWidth(1);
		vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
		//vpData.setSize("100%", "100%");
		vpData.add(dataGrid);
		
		
		
		formWidget.spList.setWidget(vpData);
		
		
	}
	
	dataGrid.setRowData(listHistory);
	}
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			lb_pris.setSelectedIndex(-1);
			tb_cno.setText("");
			lb_ct.setSelectedIndex(-1);
			db_occ.setValue(null);
			tb_place.setText("");
			tb_desc.setText("");
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listHistory.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listHistory.get(i), false);
				}
				
				
			}
			
			
			lb_pris.setFocus(true);
			
		
	}
	private void save() {
		
		if(lb_pris.getSelectedIndex()<0||lb_ct.getSelectedIndex()<0||tb_cno.getText().isEmpty()||tb_place.getText().isEmpty()||tb_desc.getText().isEmpty()||db_occ.getValue()==null) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new HostoryBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setHistoryid(Integer.parseInt(tb_id.getText()));
			}
			bean.setCrimeno(Integer.parseInt(tb_cno.getText()));
			bean.setCrimetypeid(listCrimetype.get(lb_ct.getSelectedIndex()));
			bean.setDateofocc(db_occ.getValue());
			bean.setDescription(tb_desc.getText());
			bean.setPlaceofocc(tb_place.getText());
			bean.setPrisonerid(listPrisoner.get(lb_pris.getSelectedIndex()));
					
			greetingService.saveHistoryServer(bean, new AsyncCallback<String[]>() {
				
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
			
				id = listHistory.get(dataGrid.getKeyboardSelectedRow()).getHistoryid();
				
				
				greetingService.deleteHistoryServer(id, new AsyncCallback<String[]>() {
					
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
		
		greetingService.listHistoryServer(id,new AsyncCallback<List<HostoryBean>>() {
			
			@Override
			public void onSuccess(List<HostoryBean> result) {
				
		
				try{
					listHistory = result;
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
}
