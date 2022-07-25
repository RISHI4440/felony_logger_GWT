package com.crime.client;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.crime.shared.ChargesheetBean;
import com.crime.shared.FirBean;
import com.crime.shared.FirtypeBean;
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

public class ChargeSheetPanel extends LazyPanel{
	
	List<ChargesheetBean> listChSh;
	
	ChargesheetBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_informer, tb_aName, tb_infoOcc, tb_infoAdd, tb_infoP;
	RadioButton rb_male, rb_female;
	ListBox lb_fir;
	CheckBox cb_wanted;
	
		
	DataGrid<ChargesheetBean> dataGrid;
	List<FirBean> listFir;
	
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
	

	private DateBox db_chsh;

	private TextBox tb_aAdd;
	
	public ChargeSheetPanel() {
		
		
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
		
		l_title = new Label("Charge Sheet");
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
		
				
		Label label = new Label("ChargeSh ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Fir No");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		lb_fir = new ListBox();
		lb_fir.setStyleName("listbox");
		loadFirList();
				
		flex.setWidget(2, 1, lb_fir);
		formatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Charge Date");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
		
		db_chsh = new DateBox();
		db_chsh.setStyleName("datebox");
		db_chsh.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flex.setWidget(3, 1, db_chsh);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		//customer name
		label = new Label("Informer Name");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
			
		tb_informer = new TextBox();
		tb_informer.setStyleName("textbox");
		tb_informer.addStyleName("upper");
						
		flex.setWidget(4, 1, tb_informer);
		formatter.setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Info Address");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		tb_infoAdd = new TextBox();
		tb_infoAdd.setStyleName("textbox");
		
		flex.setWidget(5, 1, tb_infoAdd);
		formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Informer Occ");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_infoOcc = new TextBox();
		tb_infoOcc.setStyleName("textbox");
		tb_infoOcc.addStyleName("upper");
						
		flex.setWidget(6, 1, tb_infoOcc);
		formatter.setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		label = new Label("Informer Partclr");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
		
		tb_infoP = new TextBox();
		tb_infoP.setStyleName("textbox");
		tb_infoP.addStyleName("upper");
						
		flex.setWidget(7, 1, tb_infoP);
		formatter.setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		label = new Label("Accus Name");
		label.setStyleName("label");
		flex.setWidget(8, 0, label);
		
		tb_aName = new TextBox();
		tb_aName.setStyleName("textbox");
		tb_aName.addStyleName("upper");
						
		flex.setWidget(8, 1, tb_aName);
		formatter.setVerticalAlignment(8, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Accus Add");
		label.setStyleName("label");
		flex.setWidget(9, 0, label);
		
		tb_aAdd = new TextBox();
		tb_aAdd.setStyleName("textbox");
		tb_aAdd.addStyleName("upper");
						
		flex.setWidget(9, 1, tb_aAdd);
		formatter.setVerticalAlignment(9, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(9, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		vp.add(flex);
				
		return vp;
	}
	
	private void loadFirList(){
		
		greetingService.loadFirServer(new AsyncCallback<List<FirBean>>() {
			
			@Override
			public void onSuccess(List<FirBean> result) {
				
				
				listFir = result;
				
				for (Iterator<FirBean> iterator = result.iterator(); iterator
						.hasNext();) {
					FirBean entityBean = (FirBean) iterator.next();
					
					lb_fir.addItem(entityBean.getFirid()+"");
					
				}
				
				if(listFir.size()>0){
					
					lb_fir.setSelectedIndex(-1);
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
		
		greetingService.searchChShServer(q, new AsyncCallback<List<ChargesheetBean>>() {
			
			@Override
			public void onSuccess(List<ChargesheetBean> result) {
				
		
				try{
					listChSh = result;
					
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
			
			dataGrid = new DataGrid<ChargesheetBean>(20);
			dataGrid.setWidth("100%");
			dataGrid.setHeight("100%");
			
			// Do not refresh the headers and footers every time the data is updated.
		    dataGrid.setAutoHeaderRefreshDisabled(true);
		    
		    // Set the message to display when the table is empty.
		    dataGrid.setEmptyTableWidget(new Label("Empty"));
		    
		    // Attach a column sort handler to the ListDataProvider to sort the list.
		    ListHandler<ChargesheetBean> sortHandler =
		        new ListHandler<ChargesheetBean>(listChSh);
		    
		    dataGrid.addColumnSortHandler(sortHandler);
		    
		    // Add a selection model so we can select cells.
		    final SelectionModel<ChargesheetBean> selectionModel = new SingleSelectionModel<ChargesheetBean>();
		    
		    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
		        .<ChargesheetBean> createDefaultManager());
		    
		    
		    // Initialize the columns.
			Column<ChargesheetBean, String> groupColumn = new 
			Column<ChargesheetBean, String>(new TextCell()) { 
				
				@Override 
				public String getValue(ChargesheetBean object) { 
					return object.getChargesheetid()+""; 
				} 
			};
			dataGrid.addColumn(groupColumn, "Chargesheet ID");
			dataGrid.setColumnWidth(0, "120px");
			
			
			Column<ChargesheetBean, String> firNO = new 
					Column<ChargesheetBean, String>(new TextCell()) { 
						
				@Override 
				public String getValue(ChargesheetBean object) { 
					return object.getFirid().getFirid()+""; 
				} 
			};
			dataGrid.addColumn(firNO, "Fir No");
			dataGrid.setColumnWidth(1, "100px");
			
						
			Column<ChargesheetBean, String> accuse = new 
					Column<ChargesheetBean, String>(new TextCell()) { 
						
				@Override 
				public String getValue(ChargesheetBean object) { 
					return object.getAccusname(); 
				} 
			};
			dataGrid.addColumn(accuse, "Accuse Name");
			dataGrid.setColumnWidth(2, "200px");
			
			Column<ChargesheetBean, String> accuseAdd = new 
					Column<ChargesheetBean, String>(new TextCell()) { 
						
				@Override 
				public String getValue(ChargesheetBean object) { 
					return object.getAccaddress(); 
				} 
			};
			dataGrid.addColumn(accuseAdd, "Accuse Address");
			dataGrid.setColumnWidth(2, "200px");
			
			VerticalPanel vpData = new VerticalPanel();
			//vpData.setBorderWidth(1);
			vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
			//vpData.setSize("100%", "100%");
			vpData.add(dataGrid);
			
			
			
			formWidget.spList.setWidget(vpData);
			
			
		}
		
		dataGrid.setRowData(listChSh);
	}
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			tb_informer.setText("");
			tb_infoP.setText("");
			tb_infoAdd.setText("");
			lb_fir.setSelectedIndex(-1);
			db_chsh.setValue(null);
			tb_infoOcc.setText("");
			tb_aAdd.setText("");
			tb_aName.setText("");
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listChSh.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listChSh.get(i), false);
				}
				
				
			}
			
			
			lb_fir.setFocus(true);
			
		
	}
	private void save() {
		
		if(db_chsh.getValue()==null||tb_informer.getText().isEmpty()||tb_infoAdd.getText().isEmpty()||tb_infoOcc.getText().isEmpty()||tb_infoP.getText().isEmpty()||tb_aName.getText().isEmpty()||tb_aAdd.getText().isEmpty()) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new ChargesheetBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setChargesheetid(Integer.parseInt(tb_id.getText()));
			}
			
			bean.setAccaddress(tb_aAdd.getText());
			bean.setAccusname(tb_aName.getText());
			bean.setChsdate(db_chsh.getValue());
			bean.setFirid(listFir.get(lb_fir.getSelectedIndex()));
			bean.setInfoadd(tb_infoAdd.getText());
			bean.setInfoname(tb_informer.getText());
			bean.setInfoocc(tb_infoOcc.getText());
			bean.setInfoperticular(tb_infoP.getText());
			
			
			greetingService.saveChShServer(bean, new AsyncCallback<String[]>() {
				
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
			
				id = listChSh.get(dataGrid.getKeyboardSelectedRow()).getChargesheetid();
				
				
				greetingService.deleteChshServer(id, new AsyncCallback<String[]>() {
					
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
		
		greetingService.loadChshServer(new AsyncCallback<List<ChargesheetBean>>() {
			
			@Override
			public void onSuccess(List<ChargesheetBean> result) {
				
		
				try{
					listChSh = result;
					
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
