package com.crime.client;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.crime.shared.CrimetypeBean;
import com.crime.shared.CriminalBean;
import com.crime.shared.FirBean;
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
import com.google.gwt.view.client.DefaultSelectionEventManager;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class CriminalPanel extends LazyPanel{
	
	List<CriminalBean> listCriminal;
	List<CrimetypeBean> listCrimetype;
	
	CriminalBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_name, tb_age, tb_occup, tb_address, tb_nick;
	RadioButton rb_male, rb_female;
	ListBox lb_ct;
	CheckBox cb_wanted;
	
		
	DataGrid<CriminalBean> dataGrid;
	
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
	
	public CriminalPanel() {
		
		
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
		
		l_title = new Label("Criminal");
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
		
				
		Label label = new Label("Criminal ID");
		label.setStyleName("label");
		flex.setWidget(1, 0, label);
		
		tb_id = new TextBox();
		tb_id.setStyleName("textbox");
		tb_id.setEnabled(false);
		flex.setWidget(1, 1, tb_id);
		formatter.setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//customer name
		label = new Label("Criminal Name");
		label.setStyleName("label");
		flex.setWidget(2, 0, label);
			
		tb_name = new TextBox();
		tb_name.setStyleName("textbox");
		tb_name.addStyleName("upper");
						
		flex.setWidget(2, 1, tb_name);
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
		
		label = new Label("Age");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
		
		tb_age = new TextBox();
		tb_age.setStyleName("textbox");
		tb_age.addStyleName("upper");
						
		flex.setWidget(4, 1, tb_age);
		formatter.setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(4, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Gender");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		HorizontalPanel hp = new HorizontalPanel();
		flex.setWidget(5, 1, hp);
		
		rb_male = new RadioButton("gender");
		rb_male.setHTML("Male");
		rb_male.setValue(true);
		hp.add(rb_male);
		
		rb_female = new RadioButton("gender");
		rb_female.setHTML("FeMale");
		hp.add(rb_female);
		
		
		label = new Label("Occupation");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_occup = new TextBox();
		tb_occup.setStyleName("textbox");
		tb_occup.addStyleName("upper");
						
		flex.setWidget(6, 1, tb_occup);
		formatter.setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Crime Type");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
		
		lb_ct = new ListBox();
		lb_ct.setStyleName("listbox");
		loadCrimetypeList();
				
		flex.setWidget(7, 1, lb_ct);
		formatter.setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
				
		label = new Label("Address");
		label.setStyleName("label");
		flex.setWidget(8, 0, label);
		
		tb_address = new TextBox();
		tb_address.setStyleName("textbox");
		
		flex.setWidget(8, 1, tb_address);
		formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//customer address
		 label = new Label("Description");
		 label.setStyleName("label");
		 flex.setWidget(9, 0, label);
		
		tb_desc = new TextArea();
		tb_desc.setHeight("80px");
		tb_desc.setStyleName("textbox");
				
		flex.setWidget(9, 1, tb_desc);
		formatter.setHorizontalAlignment(9, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		 label = new Label("Most Wanted");
		 label.setStyleName("label");
		 flex.setWidget(10, 0, label);
		
		 cb_wanted = new CheckBox();
		 flex.setWidget(10, 1, cb_wanted);
		
		
		
		
		vp.add(flex);
				
		return vp;
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
		
		greetingService.searchCriminalServer(q, new AsyncCallback<List<CriminalBean>>() {
			
			@Override
			public void onSuccess(List<CriminalBean> result) {
				
		
				try{
					listCriminal = result;
					
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
		
		dataGrid = new DataGrid<CriminalBean>(20);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("100%");
		
		// Do not refresh the headers and footers every time the data is updated.
	    dataGrid.setAutoHeaderRefreshDisabled(true);
	    
	    // Set the message to display when the table is empty.
	    dataGrid.setEmptyTableWidget(new Label("Empty"));
	    
	    // Attach a column sort handler to the ListDataProvider to sort the list.
	    ListHandler<CriminalBean> sortHandler =
	        new ListHandler<CriminalBean>(listCriminal);
	    
	    dataGrid.addColumnSortHandler(sortHandler);
	    
	    // Add a selection model so we can select cells.
	    final SelectionModel<CriminalBean> selectionModel = new SingleSelectionModel<CriminalBean>();
	    
	    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	        .<CriminalBean> createDefaultManager());
	    
	    
	    
	    // Initialize the columns.
		Column<CriminalBean, String> idColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getCriminalid()+""; 
			} 
		};
		dataGrid.addColumn(idColumn, "Criminal ID");
		dataGrid.setColumnWidth(0, "100px");
		
		Column<CriminalBean, String> nameColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getName(); 
			} 
		};
		dataGrid.addColumn(nameColumn, "Name");
		dataGrid.setColumnWidth(1, "200px");
					
		Column<CriminalBean, String> nickColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getNickname(); 
			} 
		};
		dataGrid.addColumn(nickColumn, "Nickname");
		dataGrid.setColumnWidth(2, "200px");
		
		Column<CriminalBean, String> ageColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getAge()+""; 
			} 
		};
		dataGrid.addColumn(ageColumn, "Age");
		dataGrid.setColumnWidth(3, "50px");

		Column<CriminalBean, String> gColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) {
				
				return object.getGender(); 
			} 
		};
		dataGrid.addColumn(gColumn, "Gender");
		dataGrid.setColumnWidth(4, "60px");

		Column<CriminalBean, String> cColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getCrimetypeid().getCrimename()+""; 
			} 
		};
		dataGrid.addColumn(cColumn, "Crime");
		dataGrid.setColumnWidth(5, "200px");

		Column<CriminalBean, String> mwColumn = new 
		Column<CriminalBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(CriminalBean object) { 
				return object.getIsmostwanted()+""; 
			} 
		};
		dataGrid.addColumn(mwColumn, "Most Wanted");
		dataGrid.setColumnWidth(6, "50px");

		
		VerticalPanel vpData = new VerticalPanel();
		//vpData.setBorderWidth(1);
		vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
		//vpData.setSize("100%", "100%");
		vpData.add(dataGrid);
		
		
		
		formWidget.spList.setWidget(vpData);
		
		
	}
	
	dataGrid.setRowData(listCriminal);
		
	}
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			tb_name.setText("");
			tb_nick.setText("");
			tb_age.setText("");
			tb_occup.setText("");
			lb_ct.setSelectedIndex(-1);
			tb_address.setText("");
			tb_desc.setText("");
			cb_wanted.setValue(false);
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listCriminal.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listCriminal.get(i), false);
				}
				
				
			}
			
			
			tb_name.setFocus(true);
			
		
	}
	private void save() {
		
		if(tb_name.getText().isEmpty()||tb_nick.getText().isEmpty()||tb_age.getText().isEmpty()||tb_occup.getText().isEmpty()||lb_ct.getSelectedIndex()<0||tb_address.getText().isEmpty()||tb_desc.getText().isEmpty()) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new CriminalBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setCriminalid(Integer.parseInt(tb_id.getText()));
			}
			
			bean.setName(tb_name.getText());
			bean.setNickname(tb_nick.getText());
			bean.setAge(Integer.parseInt(tb_age.getText()));
			
			if(rb_male.getValue()){
				
				bean.setGender("MALE");
			}else{
				bean.setGender("FEMALE");
			}
			bean.setOccupation(tb_occup.getText());
			bean.setCrimetypeid(listCrimetype.get(lb_ct.getSelectedIndex()));
			
			bean.setAddress(tb_address.getText());
			
		
			greetingService.saveCriminalServer(bean, new AsyncCallback<String[]>() {
				
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
			
				id = listCriminal.get(dataGrid.getKeyboardSelectedRow()).getCriminalid();
				
				
				greetingService.deleteCriminalServer(id, new AsyncCallback<String[]>() {
					
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
		
		greetingService.listCriminalServer(id,new AsyncCallback<List<CriminalBean>>() {
			
			@Override
			public void onSuccess(List<CriminalBean> result) {
				
		
				try{
					listCriminal = result;
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
