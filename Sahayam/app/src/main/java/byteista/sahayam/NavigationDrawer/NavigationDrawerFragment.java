package byteista.sahayam.NavigationDrawer;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;


import java.util.ArrayList;
import java.util.List;

import byteista.sahayam.R;
import byteista.sahayam.Utils.ClickListener;
import byteista.sahayam.Utils.Display;
import byteista.sahayam.Utils.RecyclerTouchListener;


public class NavigationDrawerFragment extends Fragment {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mView;
    private RecyclerView drawerRecycler;
    public static DrawerAdapter adapter;
    private int action_bar_height;

    public NavigationDrawerFragment() {

    }

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;
    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    View layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        }
        catch (InflateException e)
        {

        }
        drawerRecycler=(RecyclerView) layout.findViewById(R.id.drawer_recycler);




        SpacesItemDecoration space=new SpacesItemDecoration(5);
        drawerRecycler.addItemDecoration(space);

        adapter=new DrawerAdapter(getActivity(),getData(),action_bar_height);
        drawerRecycler.setAdapter(adapter);

        GridLayoutManager grid=new GridLayoutManager(getActivity(),2);
        grid.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position==0)
                    return 2;
                else
                    return 1;
            }
        });



        drawerRecycler.setLayoutManager(grid);


       drawerRecycler.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), drawerRecycler, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                mDrawerLayout.closeDrawer(GravityCompat.START);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return layout;
    }

    public void setUp(int id, DrawerLayout drawer, Toolbar toolbar,int action_bar_height) {
        mDrawerLayout = drawer;
        mView = getActivity().findViewById(id);
        Display display=new Display(getActivity());
        ViewGroup.LayoutParams layoutParams=mView.getLayoutParams();
        layoutParams.width= display.getWidth()-action_bar_height;
        mView.setLayoutParams(layoutParams);
        this.action_bar_height=action_bar_height;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer, R.string.drawer_open, R.string.drwer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                getActivity().invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };
       if(!mUserLearnedDrawer&&!mFromSavedInstanceState)
           mDrawerLayout.openDrawer(GravityCompat.START);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    public void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferneces = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferneces.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public String readFromPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferneces = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferneces.edit();
        return sharedPreferneces.getString(preferenceName, preferenceValue);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));
        if (savedInstanceState != null)
            mFromSavedInstanceState = true;
    }
    public static List<DrawerLayoutManager> getData()
    {
        List<DrawerLayoutManager> data=new ArrayList<>();
        int[] icons={R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground,R.drawable.ic_launcher_foreground};
            String[] titles={"TRENDING","CHATS","VOICE & VIDEO CALLS","NOTIFICATIONS","PUBLIC PROFILE","SETTINGS"};
        for(int i=0;i<icons.length;i++)
        {
            DrawerLayoutManager current=new DrawerLayoutManager();
            current.iconId=icons[i];
            current.title=titles[i];
            data.add(current);
        }
        return data;
    }




}
