package io.saytheirnames.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Collections;
import java.util.List;

import io.saytheirnames.R;
import io.saytheirnames.models.HeaderCard;

/**
 * Because RecyclerViews that use the Paging library don't work when inside a NestedScrollView,
 * we have to find some way to scroll the header on the home screen as well. One way to do that
 * is to add that into the Recyclerview as well. This is what this adapter aims to accomplish.
 */

public class HomeHeaderAdapter extends RecyclerView.Adapter<HomeHeaderAdapter.ViewHolder> {

    private HeaderCardRecyclerAdapter.HeaderCardClickListener listener;

    public HomeHeaderAdapter(HeaderCardRecyclerAdapter.HeaderCardClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public HomeHeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_header_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHeaderAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind();
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TabLayout tabLayout;
        private ViewPager2 viewPager;

        private List<HeaderCard> headerCards;
        private HeaderCardRecyclerAdapter headerCardRecyclerAdapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tabLayout = itemView.findViewById(R.id.header_tab_layout);
            viewPager = itemView.findViewById(R.id.header_view_pager);

        }

        public void bind() {
            headerCards = Collections.singletonList(new HeaderCard());
            headerCardRecyclerAdapter = new HeaderCardRecyclerAdapter(headerCards, listener);

            viewPager.setAdapter(headerCardRecyclerAdapter);
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tabLayout.selectTab(tabLayout.getTabAt(position));
                viewPager.setCurrentItem(tab.getPosition(), true);
            }).attach();
        }
    }
}
