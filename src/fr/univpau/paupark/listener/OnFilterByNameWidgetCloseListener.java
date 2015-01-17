package fr.univpau.paupark.listener;

import android.widget.SearchView;

public class OnFilterByNameWidgetCloseListener implements SearchView.OnCloseListener  {
	
	private OnFilterByNameTextListener textListener;
	
	public OnFilterByNameWidgetCloseListener(
			OnFilterByNameTextListener textListener) 
	{
		this.textListener = textListener;
	}
	
	@Override
	public boolean onClose() 
	{
		this.textListener.reset();
		
		return false;
	}

}
