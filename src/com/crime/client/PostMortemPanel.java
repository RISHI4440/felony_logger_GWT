package com.crime.client;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.crime.shared.FirBean;
import com.crime.shared.PostmortamBean;
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

public class PostMortemPanel extends LazyPanel{
	
	List<PostmortamBean> listPostmortem;
	List<FirBean> listFir;
	
	PostmortamBean bean;
	DockLayoutPanel dp;
	Anchor anc_manage, anc_search, anc_list;
	Label label;
	
	TextBox tb_search;
		
	/*for Dialog Box */
	FlexTable flex;
	VerticalPanel vp;
	public TextBox tb_id, tb_house, tb_aName, tb_ps, tb_doctor;
	RadioButton rb_male, rb_female;
	ListBox lb_fir;
		
		
	DataGrid<PostmortamBean> dataGrid;
	
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
	

	private DateBox db_death;

	
	private TextArea tb_result;

	private TextArea tb_desc;
	
	public PostMortemPanel() {
		
		
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
		
		l_title = new Label("Post Mortem");
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
		
				
		Label label = new Label("PostMortem ID");
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
		
		label = new Label("Result");
		label.setStyleName("label");
		flex.setWidget(3, 0, label);
		
		tb_result = new TextArea();
		tb_result.setHeight("80px");
		tb_result.setStyleName("textbox");
		flex.setWidget(3, 1, tb_result);
		formatter.setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Gender");
		label.setStyleName("label");
		flex.setWidget(4, 0, label);
		
		HorizontalPanel hp = new HorizontalPanel();
		flex.setWidget(4, 1, hp);
		
		rb_male = new RadioButton("gender");
		rb_male.setHTML("Male");
		rb_male.setValue(true);
		hp.add(rb_male);
		
		rb_female = new RadioButton("gender");
		rb_female.setHTML("FeMale");
		hp.add(rb_female);
		
		label = new Label("Death Date");
		label.setStyleName("label");
		flex.setWidget(5, 0, label);
		
		db_death = new DateBox();
		db_death.setStyleName("datebox");
		db_death.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd/MM/yyyy")));
		flex.setWidget(5, 1, db_death);
		formatter.setVerticalAlignment(5, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(5, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		label = new Label("Case Desc");
		label.setStyleName("label");
		flex.setWidget(6, 0, label);
		
		tb_desc = new TextArea();
		tb_desc.setHeight("80px");
		tb_desc.setStyleName("textbox");
		flex.setWidget(6, 1, tb_desc);
		formatter.setVerticalAlignment(6, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(6, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		//customer name
		label = new Label("House Name");
		label.setStyleName("label");
		flex.setWidget(7, 0, label);
			
		tb_house = new TextBox();
		tb_house.setStyleName("textbox");
		tb_house.addStyleName("upper");
						
		flex.setWidget(7, 1, tb_house);
		formatter.setVerticalAlignment(7, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		formatter.setHorizontalAlignment(7, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		label = new Label("Doctor Name");
		label.setStyleName("label");
		flex.setWidget(8, 0, label);
		
		tb_doctor = new TextBox();
		tb_doctor.setStyleName("textbox");
		
		flex.setWidget(8, 1, tb_doctor);
		formatter.setHorizontalAlignment(8, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		
		
		label = new Label("Police Station");
		label.setStyleName("label");
		flex.setWidget(9, 0, label);
		
		tb_ps = new TextBox();
		tb_ps.setStyleName("textbox");
		tb_ps.addStyleName("upper");
						
		flex.setWidget(9, 1, tb_ps);
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
		
		greetingService.searchPMServer(q, new AsyncCallback<List<PostmortamBean>>() {
			
			@Override
			public void onSuccess(List<PostmortamBean> result) {
				
		
				try{
					listPostmortem = result;
					
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
		
		dataGrid = new DataGrid<PostmortamBean>(20);
		dataGrid.setWidth("100%");
		dataGrid.setHeight("100%");
		
		// Do not refresh the headers and footers every time the data is updated.
	    dataGrid.setAutoHeaderRefreshDisabled(true);
	    
	    // Set the message to display when the table is empty.
	    dataGrid.setEmptyTableWidget(new Label("Empty"));
	    
	    // Attach a column sort handler to the ListDataProvider to sort the list.
	    ListHandler<PostmortamBean> sortHandler =
	        new ListHandler<PostmortamBean>(listPostmortem);
	    
	    dataGrid.addColumnSortHandler(sortHandler);
	    
	    // Add a selection model so we can select cells.
	    final SelectionModel<PostmortamBean> selectionModel = new SingleSelectionModel<PostmortamBean>();
	    
	    dataGrid.setSelectionModel(selectionModel, DefaultSelectionEventManager
	        .<PostmortamBean> createDefaultManager());
	    
	    
	    
	    // Initialize the columns.
		Column<PostmortamBean, String> groupColumn = new 
		Column<PostmortamBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PostmortamBean object) { 
				return object.getPmid()+""; 
			} 
		};
		dataGrid.addColumn(groupColumn, "Postmortem ID");
		dataGrid.setColumnWidth(0, "100px");
		
		Column<PostmortamBean, String> firColumn = new 
		Column<PostmortamBean, String>(new TextCell()) { 
			
			@Override 
			public String getValue(PostmortamBean object) { 
				return object.getFirid().getFirid()+""; 
			} 
		};
		dataGrid.addColumn(firColumn, "Fir No");
		dataGrid.setColumnWidth(1, "100px");
		
		Column<PostmortamBean, String> deathColumn = new 
		Column<PostmortamBean, String>(new TextCell()) { 
					
			@Override 
			public String getValue(PostmortamBean object) { 
				return DateTimeFormat.getFormat("dd/MM/yyyy").format(object.getDeathdate())+""; 
			} 
		};
		dataGrid.addColumn(deathColumn, "Death Date");
		dataGrid.setColumnWidth(2, "200px");
				
		Column<PostmortamBean, String> resultColumn = new 
		Column<PostmortamBean, String>(new TextCell()) { 
							
			@Override 
					public String getValue(PostmortamBean object) { 
						return object.getReslt(); 
					} 
				};
				dataGrid.addColumn(resultColumn, "Result");
				
		
		VerticalPanel vpData = new VerticalPanel();
		//vpData.setBorderWidth(1);
		vpData.setSize("100%", formWidget.spList.getOffsetHeight()+"px");
		//vpData.setSize("100%", "100%");
		vpData.add(dataGrid);
		
		
		
		formWidget.spList.setWidget(vpData);
		
		
	}
	
	dataGrid.setRowData(listPostmortem);
	}
	
	private void reset(){
				
			isUpdate = false;
			isReset = true;
			
			tb_id.setText("");
			lb_fir.setSelectedIndex(-1);
			tb_result.setText("");
			db_death.setValue(null);
			tb_desc.setText("");
			tb_house.setText("");
			tb_doctor.setText("");
			tb_ps.setText("");
			
			//dataGrid.setFocus(false);
			if(dataGrid!=null){
			
				if(listPostmortem.size()>0){
				
					int i = dataGrid.getKeyboardSelectedRow();
					dataGrid.getSelectionModel().setSelected(listPostmortem.get(i), false);
				}
				
				
			}
			
			
			lb_fir.setFocus(true);
			
		
	}
	private void save() {
		
		if(lb_fir.getSelectedIndex()<0||tb_result.getText().isEmpty()||db_death.getValue()==null||tb_desc.getText().isEmpty()||tb_house.getText().isEmpty()||tb_doctor.getText().isEmpty()||tb_ps.getText().isEmpty()) {
			
			StatusPopup.showStatus("Required fields cannot be empty!");
			
		}else {
			
			bean = new PostmortamBean();
			if(!tb_id.getText().isEmpty())
			{
				bean.setPmid(Integer.parseInt(tb_id.getText()));
			}
			
			bean.setFirid(listFir.get(lb_fir.getSelectedIndex()));
			bean.setReslt(tb_result.getText());
			bean.setCasedesc(tb_desc.getText());
			bean.setDeathdate(db_death.getValue());
			bean.setDoctorname(tb_doctor.getText());
			
			if(rb_male.getValue()){
			
				bean.setGender("Male");
			}else{
				bean.setGender("Female");
			}
			bean.setHomename(tb_house.getText());
			bean.setPolicestation(tb_ps.getText());
			
			
		
			greetingService.savePMServer(bean, new AsyncCallback<String[]>() {
				
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
			
				id = listPostmortem.get(dataGrid.getKeyboardSelectedRow()).getPmid();
				
				
				greetingService.deletePMServer(id, new AsyncCallback<String[]>() {
					
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
		
		greetingService.listPMServer(id,new AsyncCallback<List<PostmortamBean>>() {
			
			@Override
			public void onSuccess(List<PostmortamBean> result) {
				
		
				try{
					listPostmortem = result;
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
