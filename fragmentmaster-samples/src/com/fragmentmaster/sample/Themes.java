package com.fragmentmaster.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fragmentmaster.annotation.Configuration;
import com.fragmentmaster.app.MasterFragment;
import com.fragmentmaster.app.MasterListFragment;
import com.fragmentmaster.app.Request;
import com.fragmentmaster.sample.entry.Entry;

import java.util.ArrayList;
import java.util.List;

public class Themes extends MasterListFragment {
    private static final List<Entry> ENTRIES = new ArrayList<>();

    static {
        ENTRIES.add(new Entry("Dark Theme", new Request(
                DarkThemePage.class)));
        ENTRIES.add(new Entry("Light Theme", new Request(LightThemePage.class)));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, ENTRIES));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        startFragment(ENTRIES.get(position).mRequest);
    }

    public static class BaseThemePage extends MasterFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.theme_fragment, container, false);
        }
    }

    @Configuration(theme = R.style.Theme_AppCompat)
    public static class DarkThemePage extends BaseThemePage {

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            FragmentManager fragmentManager = getChildFragmentManager();
            if (fragmentManager.findFragmentByTag("TAG_CHILD") == null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.childContainer, new LightThemeChildPage(), "TAG_CHILD");
                ft.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    @Configuration(theme = R.style.Theme_AppCompat_Light)
    public static class LightThemePage extends BaseThemePage {

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            FragmentManager fragmentManager = getChildFragmentManager();
            if (fragmentManager.findFragmentByTag("TAG_CHILD") == null) {
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.add(R.id.childContainer, new DarkThemeChildPage(), "TAG_CHILD");
                ft.commitAllowingStateLoss();
                fragmentManager.executePendingTransactions();
            }
        }
    }

    public static class BaseThemeChildPage extends MasterFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.theme_fragment, container, false);
        }
    }

    @Configuration(theme = R.style.Theme_AppCompat)
    public static class DarkThemeChildPage extends BaseThemeChildPage {
    }

    @Configuration(theme = R.style.Theme_AppCompat_Light)
    public static class LightThemeChildPage extends BaseThemeChildPage {
    }
}

