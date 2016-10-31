package com.android.dekkoo.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.android.dekkoo.R;
import com.android.dekkoo.data.DataManager;
import com.android.dekkoo.data.model.Base;
import com.android.dekkoo.data.model.Response;
import com.android.dekkoo.ui.adapter.TittleAdapter;
import com.android.dekkoo.ui.adapter.ValuesAdapter;
import com.android.dekkoo.ui.adapter.VideosAdapter;
import com.android.dekkoo.util.AndroidComponentUtil;
import com.android.dekkoo.util.DataUtils;
import com.android.dekkoo.util.DialogFactory;
import com.android.dekkoo.util.ItemClickListener;
import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements ItemClickListener {
    @BindView(R.id.progress_indicator)
    ProgressBar mProgressBar;
    @BindView(R.id.bottom_tittle_view)
    RecyclerView mBottomView;
    @BindView(R.id.middle_values_view)
    RecyclerView mMiddleView;
    @BindView(R.id.top_level_view)
    RecyclerView mTopView;
    @Inject
    TittleAdapter mTittleAdapter;
    @Inject
    ValuesAdapter mValuesAdapter;
    @Inject
    DataManager mDataManager;
    private Subscription mSubscription;
    @Inject
    VideosAdapter mVideosAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_home_screen);
        ButterKnife.bind(this);
//        mSubscriptions = new CompositeSubscription();
        if (mSubscription != null) mSubscription.unsubscribe();
        setupRecyclerViewForTittle();
        setupRecyclerViewForValues();
        setupRecyclerViewForVideos();
        loadTittles();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupRecyclerViewForTittle() {
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mBottomView.setLayoutManager(layoutManager);
        mBottomView.addOnScrollListener(new CenterScrollListener());
        mBottomView.setAdapter(mTittleAdapter);
        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    Response response = mTittleAdapter.getItemForPosition(adapterPosition);
                    setValuesForTittleSelected(response.getValues());
                    mDataManager.getPreferencesHelper().setTittle(response.getTitle());
                }
            }
        });
    }

    private void setupRecyclerViewForValues() {
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mMiddleView.setLayoutManager(layoutManager);
        mMiddleView.addOnScrollListener(new CenterScrollListener());
        mMiddleView.setAdapter(mValuesAdapter);
        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition)
                    loadVideosForSelectedValue(mValuesAdapter.getItemForPosition(adapterPosition));
            }
        });
    }

    private void setupRecyclerViewForVideos() {
        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mTopView.setLayoutManager(layoutManager);
        mTopView.addOnScrollListener(new CenterScrollListener());
        mTopView.setAdapter(mVideosAdapter);
        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {
            @Override
            public void onCenterItemChanged(int adapterPosition) {
                //TODO
            }
        });
    }

    private void loadTittles() {
        if (DataUtils.isNetworkAvailable(this)) {
            AndroidComponentUtil.unsubscribe(mSubscription);
            mSubscription = mDataManager.getCategories()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Base>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable error) {
                            Timber.e("There was an error retrieving the characters " + error);
                            mProgressBar.setVisibility(View.GONE);
                            DialogFactory.createSimpleErrorDialog(MainActivity.this).show();
                        }

                        @Override
                        public void onNext(Base value) {
                            mProgressBar.setVisibility(View.GONE);
                            mTittleAdapter.setTittles(value.getResponse(), MainActivity.this);
                        }
                    });
        } else {
            mProgressBar.setVisibility(View.GONE);
            DialogFactory.createSimpleOkErrorDialog(
                    this,
                    getString(R.string.dialog_error_title),
                    getString(R.string.dialog_error_no_connection)
            ).show();
        }
    }

    @Override
    public void tittleItemSelected(int position, List<String> values, String tittle) {
        mBottomView.scrollToPosition(position);
        mTittleAdapter.notifyItemChanged(position);
        mDataManager.getPreferencesHelper().setTittle(tittle);
        setValuesForTittleSelected(values);
    }

    @Override
    public void valueItemSelected(int position, String value) {
        mMiddleView.scrollToPosition(position);
        mValuesAdapter.notifyItemChanged(position);
        loadVideosForSelectedValue(value);
    }

    public void setValuesForTittleSelected(List<String> values) {
        mValuesAdapter.setTittles(values, MainActivity.this);
    }

    public void loadVideosForSelectedValue(String videoValue) {
        if (DataUtils.isNetworkAvailable(this)) {
            AndroidComponentUtil.unsubscribe(mSubscription);
            if (progressDialog != null)
                progressDialog.dismiss();
            progressDialog = DialogFactory.createProgressDialog(MainActivity.this, "Loading Videos");
            progressDialog.setCancelable(false);
            progressDialog.show();
            mSubscription = mDataManager.getVideos(videoValue)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Subscriber<Base>() {
                        @Override
                        public void onCompleted() {
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onError(Throwable error) {
                            progressDialog.dismiss();
                            Timber.e("There was an error retrieving the characters " + error);
                            mProgressBar.setVisibility(View.GONE);
                            DialogFactory.createSimpleErrorDialog(MainActivity.this).show();
                        }

                        @Override
                        public void onNext(Base value) {
                            progressDialog.dismiss();
                            mVideosAdapter.setVideos(value.getResponse());
                        }
                    });
        } else {
            mProgressBar.setVisibility(View.GONE);
            DialogFactory.createSimpleOkErrorDialog(
                    this,
                    getString(R.string.dialog_error_title),
                    getString(R.string.dialog_error_no_connection)
            ).show();
        }
    }
}