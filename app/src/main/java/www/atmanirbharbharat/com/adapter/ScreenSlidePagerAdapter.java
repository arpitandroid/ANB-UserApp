package www.atmanirbharbharat.com.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragmentList;

    public ScreenSlidePagerAdapter(FragmentActivity fragment,List<Fragment> fragmentList) {
        super(fragment);
        this.fragmentList=fragmentList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
