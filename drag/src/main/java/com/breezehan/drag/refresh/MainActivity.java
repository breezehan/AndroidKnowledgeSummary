package com.breezehan.drag.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.breezehan.drag.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private PullToRefreshLayout ptrl;

    private TextView mainTV;
    /**
     * 上拉面板的布局
     */
    private SlidingUpPanelLayout slidingUpPanelLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
        listView = (ListView) findViewById(R.id.content_view);
        mainTV = (TextView) findViewById(R.id.tv_main);
        initListView();

        ptrl.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                //监听面板的下拉
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
            }
        });

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            //当滑动面板的位置变化调用
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //上面面板的缩放的效果
                mainTV.setScaleX(1 - slideOffset + 0.7F * slideOffset);
                mainTV.setScaleY(1 - slideOffset + 0.7F * slideOffset);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                //监听面板上拉
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    slidingUpPanelLayout.setTouchEnabled(false);
                    listView.setEnabled(true);
                }else if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){
                    slidingUpPanelLayout.setTouchEnabled(true);
                    listView.setEnabled(false);
                }else if(newState == SlidingUpPanelLayout.PanelState.ANCHORED){
                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    listView.setEnabled(false);
                }
            }
        });
    }

    /**
     * ListView初始化方法
     */
    private void initListView() {
        List<String> items = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            items.add("这里是item " + i);
        }
        MyAdapter adapter = new MyAdapter(this, items);
        listView.setAdapter(adapter);

    }
}
