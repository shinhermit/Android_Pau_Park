package fr.univpau.paupark.view.tab.fragment;

import fr.univpau.paupark.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TipsTabFragment extends Fragment {
    @Override
    public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container,
        Bundle savedInstanceState) 
    {
        // Inflate the layout for this fragment
    	// and return the root of the fragment layout
        return inflater.inflate(R.layout.tips_tab, container, false);
    }
}