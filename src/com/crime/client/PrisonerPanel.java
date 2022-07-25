package com.crime.client;

import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.crime.shared.ChargesheetBean;
import com.crime.shared.CrimetypeBean;
import com.crime.shared.FirBean;
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

public class PrisonerPanel extends LazyPanel{
	
	List<PrisonerBean> listPrisoner;
	List<ChargesheetBean> listChch;
	
	PrisonerBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_nick, tb_aName, tb_mark, tb_name;
	RadioButton rb_male, rb_female;
	ListBox lb_chsh;
		
		
	DataGrid<PrisonerBean> dataGrid;
	
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
	

	
	private TextBox tb_ht;

	private TextBox tb_wt;

	private TextBox tb_color;

	private CheckBox cb_status;
	
	public PrisonerPanel() {
		
		
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
		
		l_title = new Label("Prisoner");
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
		
				
		Label label = new Label("Prisoner ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Charge Sheet");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
		
		lb_chsh = new ListBox();
		lb_chsh.setStyleName("listbox");
		loadChshList();
				
		flex.setWidget(2, 1, lb_chsh);
		formatter.setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(2, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Nick Name");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
			
		tb_nick = new TextBox();
		tb_nick.setStyleName("textbox");
		tb_nick.addStyleName("upper");
						
		flex.setWidget(3, 1, tb_nick);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Family Name");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
		
		tb_name = new TextBox();
		tb_name.setStyleName("textbox");
		
		flex.setWidget(4, 1, tb_name);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Identity Mark");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		tb_mark = new TextBox();
		tb_mark.setStyleName("textbox");
		tb_mark.addStyleName("upper");
						
		flex.setWidget(5, 1, tb_mark);
		formatter.setVerticalAlignment(5, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		label = new Label("Height");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_ht = new TextBox();
		tb_ht.setStyleName("textbox");
		tb_ht.addStyleName("upper");
						
		flex.setWidget(6, 1, tb_ht);
		formatter.setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		label = new Label("Weight");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
		
		tb_wt = new TextBox();
		tb_wt.setStyleName("textbox");
		tb_wt.addStyleName("upper");
						
		flex.setWidget(7, 1, tb_wt);
		formatter.setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Color");
		label.setStyleName("label");
		flex.setWidget(8, 0, label);
		
		tb_color = new TextBox();
		tb_color.setStyleName("textbox");
		tb_color.addStyleName("upper");
						
		flex.setWidget(8, 1, tb_color);
		formatter.setVerticalAlignment(8, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Status");
		label.setStyleName("label");
		flex.setWidget(9, 0, label);
		
		 cb_status = new CheckBox();
		 flex.setWidget(9, 1, cb_status);
		
		
		
		
		
		
		
		
		
		vp.add(flex);
				
		return vp;
	}
	
private void loadChshList(){
		
		greetingService.loadChshServer(new AsyncCallback<List<ChargesheetBean>>() {
			
			@Override
			public void onSuccess(List<ChargesheetBean> result) {
				
				
				listChch = result;
				
				for (Iterator<ChargesheetBean> iterator = result.iterator(); iterator
						.hasNext();) {
					ChargesheetBean entityBean = (ChargesheetBean) iterator.next();
					
					lb_chsh.addItem(entityBean.getChargesheetid()+"-"+entityBean.getAccusname());
					
				}
				
				if(listChch.size()>0){
					
					lb_chsh.setSelectedIndex(-1);
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
		
		greetingService.searchPrisonerServer(q, new AsyncCallback<List<PrisonerBean>>() {
			
			@Override
			public void onSuccess(List<PrisonerBean> result) {
				
		
				try{
					listPrisoner = result;
					
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
		
		dataGrid = new DataGrid<PrisonerBean>(20);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("100%");
		
		// Do not refresh the headers and footers every time the data is updated.
	    dataGrid.setAutoHeaderRefreshDisabled(true);
	    
	    // Set the message to display when the table is empty.
	    dataGrid.setEmptyTableWidget(new Label("Empty"));
	    
	    // Attach a column sort handler to the ListDataProvider to sort the list.
	    ListHandler<PrisonerBean> sortHandler =
	        new ListHandler<PrisonerBean>(listPrisoner);
	    
	    dataGrid.addColumnSortHandler(sortHandler);
	    
	    // Add a selection model so we can select cells.
	    final SelectionModel<PrisonerBean> selectionModel = new SingleSelectionModel<PrisonerBean>();
	    
	    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	        .<PrisonerBean> createDefaultManager());
	    
	    
	    // Initialize the columns.
		Column<PrisonerBean, String> groupColumn = new 
		Column<PrisonerBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PrisonerBean object) { 
				return object.getPrisonerid()+""; 
			} 
		};
		
										
		dataGrid.addColumn(groupColumn, "Prisoner ID");
		dataGrid.setColumnWidth(0, "100px");
		
		Column<PrisonerBean, String> chshColumn = new 
		Column<PrisonerBean, String>(new TextCell()) { 
			
			@Override 
				public String getValue(PrisonerBean object) { 
				return object.getChargesheetid().getChargesheetid()+""; 
			} 
		};
		dataGrid.addColumn(chshColumn, "Chargesheet No");
		dataGrid.setColumnWidth(1, "150px");
					
		Column<PrisonerBean, String> fnColumn = new 
		Column<PrisonerBean, String>(new TextCell()) { 
					
			@Override 
			public String getValue(PrisonerBean object) { 
				return object.getFamilyname(); 
			} 
		};
		dataGrid.addColumn(fnColumn, "Name");
		dataGrid.setColumnWidth(2, "150px");
		
		Column<PrisonerBean, String> nnColumn = new 
				Column<PrisonerBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PrisonerBean object) { 
				return object.getNickname(); 
			} 
		};
		dataGrid.addColumn(nnColumn, "Nickname");
		dataGrid.setColumnWidth(2, "150px");	
		
		Column<PrisonerBean, String> htColumn = new 
				Column<PrisonerBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PrisonerBean object) { 
				return object.getHeight(); 
			} 
		};
		dataGrid.addColumn(htColumn, "Height");
		
		Column<PrisonerBean, String> wtColumn = new 
				Column<PrisonerBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PrisonerBean object) { 
				return object.getWeight(); 
			} 
		};
		dataGrid.addColumn(wtColumn, "Width");
			
		
		VerticalPanel vpData = new VerticalPanel();
		//vpData.setBorderWidth(1);
		vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
		//vpData.setSize("100%", "100%");
		vpData.add(dataGrid);
		
		
		
		formWidget.spList.setWidget(vpData);
		
		
	}
	
	dataGrid.setRowData(listPrisoner);
	}
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			lb_chsh.setSelectedIndex(-1);
			tb_nick.setText("");
			tb_name.setText("");
			tb_mark.setText("");
			tb_ht.setText("");
			tb_wt.setText("");
			tb_color.setText("");
			cb_status.setValue(false);
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listPrisoner.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listPrisoner.get(i), false);
				}
				
				
			}
			
			
			lb_chsh.setFocus(true);
			
		
	}
	private void save() {
		
		if(lb_chsh.getSelectedIndex()<0||tb_name.getText().isEmpty()||tb_nick.getText().isEmpty()||tb_mark.getText().isEmpty()||tb_ht.getText().isEmpty()||tb_wt.getText().isEmpty()||tb_color.getText().isEmpty()) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new PrisonerBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setPrisonerid(Integer.parseInt(tb_id.getText()));
			}
			bean.setChargesheetid(listChch.get(lb_chsh.getSelectedIndex()));
			bean.setColor(tb_color.getText());
			bean.setFamilyname(tb_name.getText());
			bean.setFirdate(new Date());
			bean.setHeight(tb_ht.getText());
			bean.setIdentitymark(tb_mark.getText());
			bean.setNickname(tb_nick.getText());
			
			if(cb_status.getValue()){
				bean.setStatus(1);	
			}else{
				bean.setStatus(0);
			}
			
			bean.setWeight(tb_wt.getText());
			
		
			greetingService.savePrisonerServer(bean, new AsyncCallback<String[]>() {
				
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
			
				id = listPrisoner.get(dataGrid.getKeyboardSelectedRow()).getPrisonerid();
				
				
				greetingService.deletePrisonerServer(id, new AsyncCallback<String[]>() {
					
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
		
		greetingService.listPrisonerServer(id,new AsyncCallback<List<PrisonerBean>>() {
			
			@Override
			public void onSuccess(List<PrisonerBean> result) {
				
		
				try{
					listPrisoner = result;
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
