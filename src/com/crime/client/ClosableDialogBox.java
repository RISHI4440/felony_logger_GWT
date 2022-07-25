package com.crime.client;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.*;

public class ClosableDialogBox extends DialogBox {

    private Anchor closeAnchor;
    
    
    public ClosableDialogBox(String title, boolean autoHide) {
        super(autoHide);

        addclose(autoHide, title);
    }

    public ClosableDialogBox() {
		super();
		
		addclose(false, "");
	}

	public ClosableDialogBox(boolean autoHide, boolean modal,
			Caption captionWidget) {
		super(autoHide, modal, captionWidget);
		
		addclose(autoHide, "");
	}

	public ClosableDialogBox(boolean autoHide, boolean modal) {
		super(autoHide, modal);
		// TODO Auto-generated constructor stub
		addclose(autoHide, "");
	}

	public ClosableDialogBox(boolean autoHide) {
		super(autoHide);
		// TODO Auto-generated constructor stub
		addclose(autoHide,"");
	}

	public ClosableDialogBox(Caption captionWidget) {
		super(captionWidget);
		// TODO Auto-generated constructor stub
		addclose(false,"");
	}

	public void addCloseHandler(ClickHandler handler) {
        closeAnchor.addClickHandler(handler);
    }
	
	private void addclose(boolean autoHide, String title){
		
		closeAnchor = new Anchor("",true);
        closeAnchor.setStyleName("close");
        closeAnchor.setAccessKey('C');
        closeAnchor.setSize("16px", "16px");

        FlexTable captionLayoutTable = new FlexTable();
        captionLayoutTable.setWidth("100%");
        captionLayoutTable.setHTML(0, 0, title);
        //captionLayoutTable.setText(0, 0, title);
        captionLayoutTable.setWidget(0, 1, closeAnchor);
        captionLayoutTable.getCellFormatter().setHorizontalAlignment(0, 1,
        HasHorizontalAlignment.HorizontalAlignmentConstant.endOf(HasDirection.Direction.LTR));

        HTML caption = (HTML) getCaption();
        caption.getElement().appendChild(captionLayoutTable.getElement());

        caption.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                EventTarget target = event.getNativeEvent().getEventTarget();
                Element targetElement = (Element) target.cast();

                if (targetElement == closeAnchor.getElement()) {
                    closeAnchor.fireEvent(event);
                }
            }
        });

        
        addCloseHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    hide();
                }
        });
        
	}
	
	@Override
    protected void onPreviewNativeEvent(NativePreviewEvent event) {
        super.onPreviewNativeEvent(event);
        switch (event.getTypeInt()) {
            case Event.ONKEYDOWN:
                if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
                    hide();
                }
                break;
        }
    }
	
	
}
