package com.chunsoft.baseframework.mvp.home.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chunsoft.baseframework.R;
import com.chunsoft.baseframework.base.MVPFragment;
import com.chunsoft.baseframework.mvp.home.model.HomeModel;
import com.chunsoft.baseframework.mvp.home.model.Model;
import com.chunsoft.baseframework.mvp.home.presenter.HomeListPresenterImpl;
import com.chunsoft.baseframework.mvp.home.ui.adapter.GridViewAdapter;
import com.chunsoft.baseframework.mvp.home.ui.adapter.HomeAdapter;
import com.chunsoft.baseframework.mvp.home.ui.adapter.ViewPagerAdapter;
import com.chunsoft.baseframework.mvp.home.view.HomeListView;
import com.chunsoft.baseframework.mvp.main.ui.MainActivity;
import com.chunsoft.baseframework.utils.view.DecoratorViewPager;
import com.chunsoft.baseframework.utils.view.XRecycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Developer：chunsoft on 2016/10/27 00:19
 * Email：chun_soft@qq.com
 * Content：实现Fragment，1.上拉加载更多，下拉刷新；2.RecycleView顶部是ViewPager;
 * 考虑：1.ViewPager和SwipeRefreshLayout冲突；2.RecycleView顶部添加ViewPager
 */

public class HomeFragment extends MVPFragment<HomeListPresenterImpl> implements HomeListView,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycle_view)
    XRecycleView mRecyclerView;

    private View mHeaderView;
    private LinearLayoutManager mLayoutManager;

    private HomeAdapter mAdapter;

    private List<HomeModel> mData;

    private int channel = 3;
    private int pageIndex = 0;


    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "外卖", "自助餐", "KTV", "机票/火车票", "周边游", "美甲美睫",
            "火锅", "生日蛋糕", "甜品饮品", "水上乐园", "汽车服务", "美发", "丽人", "景点", "足疗按摩", "运动健身", "健身", "超市", "买菜",
            "今日新单", "小吃快餐", "面膜", "洗浴/汗蒸", "母婴亲子", "生活服务", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配"};
    private DecoratorViewPager mPager;
    private List<View> mPagerList;
    private List<Model> mDatas;
    private LinearLayout mLlDot;
    private LayoutInflater inflaters;
    /**
     * 总的页数
     */
    private int pageCount;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 10;
    /**
     * 当前显示的是第几页
     */
    private int curIndex = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {
        channel = 3;
        mvpPresenter.loadData(channel);
    }

    @Override
    protected void initViews() {


    }

    private HomeAdapter.OnItemClickListtener mOnItemClickListtener = new HomeAdapter.OnItemClickListtener() {
        @Override
        public void onItemClick(View view, int position) {
            HomeModel.DataBean item = mAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra("Share_url",item.getShare_url());
            intent.putExtra("Title",item.getTitle());
            intent.putExtra("Author_avatar",item.getAuthor_avatar());
            View transition = view.findViewById(R.id.ivNews);
            ActivityOptionsCompat options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                            transition, getString(R.string.transition_news_img));
            ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home,null);

        ButterKnife.bind(this,view);

        mHeaderView = LayoutInflater.from(getActivity()).inflate(R.layout.home_header,null);
        mPager = (DecoratorViewPager) mHeaderView.findViewById(R.id.viewpager);
        mLlDot = (LinearLayout) mHeaderView.findViewById(R.id.ll_dot);

        mPager.setNestedpParent((ViewGroup) mPager.getParent());

        //初始化数据源
        initDatass();
        inflaters = LayoutInflater.from(getActivity());
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDatas.size() * 1.0 / pageSize);
        mPagerList = new ArrayList<View>();
        for (int i = 0; i < pageCount; i++) {
            //每个页面都是inflate出一个新实例
            GridView gridView = (GridView) inflaters.inflate(R.layout.gridview, mPager, false);
            gridView.setAdapter(new GridViewAdapter(getActivity(), mDatas, i, pageSize));
            mPagerList.add(gridView);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + curIndex * pageSize;
                    Toast.makeText(getActivity(), mDatas.get(pos).name, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(),DetailActivity.class);
                    getActivity().startActivity(intent);
                }
            });
        }
        //设置适配器
        mPager.setAdapter(new ViewPagerAdapter(mPagerList));
        //设置圆点
        setOvalLayout();


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addHeaderView(mHeaderView);
        mAdapter = new HomeAdapter(getActivity());

        mAdapter.setOnItemClickListtener(mOnItemClickListtener);

        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(mOnScrollListener);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        private int lastVisibleItem;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE
                    //&& lastVisibleItem + 1 == mAdapter.getItemCount()){
                && lastVisibleItem == mAdapter.getItemCount()){

                mvpPresenter.loadNextData(channel);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
        }
    };

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setSize(20);
        pageIndex = 0;
        if (mData != null){
            mData.clear();
            mvpPresenter.loadData(channel);
        }
    }

    @Override
    protected HomeListPresenterImpl createPresenter() {
        return new HomeListPresenterImpl(this);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.isShowFooter(false);

    }

    @Override
    public void loadFail(String msg) {

    }

    @Override
    public void addData(HomeModel model) {

        if (mData == null){
            mData = new ArrayList<>();
        }
        mData.add(model);
        if (pageIndex == 0){
            mAdapter.setData(mData);

        }else {
            mAdapter.addData(model);
            // 如果没有更多数据。则隐藏footer布局
            if (mData == null || mData.size() == 0){
                mAdapter.isShowFooter(false);
            }
            mAdapter.notifyDataSetChanged();
        }
        pageIndex = 2;
        hideProgress();
    }

    /**
     * 初始化数据源
     */
    private void initDatass() {
        mDatas = new ArrayList<Model>();
        for (int i = 0; i < titles.length; i++) {
            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
            int imageId = getResources().getIdentifier("ic_category_" + i, "drawable",
                    getActivity().getPackageName());
            mDatas.add(new Model(titles[i], imageId));
        }
    }
    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            mLlDot.addView(inflaters.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        mLlDot.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                // 取消圆点选中
                mLlDot.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                mLlDot.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
