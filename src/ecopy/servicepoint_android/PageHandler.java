package ecopy.servicepoint_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import ecopy.inboxHandler.Inbox;
import ecopy.processXML.ParseXML;

public class PageHandler extends Fragment{
	public static final String ARG_SECTION_NUMBER = "section_number";
	public Inbox inbox = new Inbox();
	public ParseXML parse = new ParseXML();
	public static PageHandler newInstance(int sectionNumber) {
		PageHandler fragment = new PageHandler();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View rootView = null ;
		int section = getArguments().getInt(ARG_SECTION_NUMBER);
		switch (section){
		case 1:
			rootView = inflater.inflate(R.layout.rapidflows, container, false);
			inbox.Browser(rootView, "inbox");
			break;
		case 2:
			rootView = inflater.inflate(R.layout.xmlprocessview, container, false);
			parse.XMLProcessor(rootView);
			break;
		case 3:
			rootView = inflater.inflate(R.layout.rapidflows, container, false);
			inbox.Browser(rootView, "upload");
			//inbox.runUploader();
			break;
		}
		
        return rootView;
    }
	 @Override
	    public void onAttach(Activity activity) {
	        super.onAttach(activity);
	        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	    }
}
