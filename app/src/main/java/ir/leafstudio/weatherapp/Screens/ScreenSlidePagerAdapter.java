package ir.leafstudio.weatherapp.Screens;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import ir.leafstudio.weatherapp.SavedCity;
import ir.leafstudio.weatherapp.Screens.todayfragment.TodayFragment;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    List<SavedCity> savedCityList;
    Picasso picasso;

//    @Inject
    public ScreenSlidePagerAdapter(FragmentManager fm, List<SavedCity> savedCityList, Picasso picasso) {
        super(fm);
        this.savedCityList = savedCityList;
        this.picasso = picasso;
    }

    @Override
    public TodayFragment getItem(int position) {
        return TodayFragment.newInstance(savedCityList.get(position), picasso);
    }

    @Override
    public int getCount() {
        return savedCityList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return savedCityList.get(position).getName();// mContext.getString(R.string.category_usefulinfo);

    }

}