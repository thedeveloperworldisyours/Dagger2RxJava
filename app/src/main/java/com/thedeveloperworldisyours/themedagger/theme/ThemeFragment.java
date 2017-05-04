package com.thedeveloperworldisyours.themedagger.theme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.thedeveloperworldisyours.themedagger.R;
import com.thedeveloperworldisyours.themedagger.data.Topics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThemeFragment extends Fragment implements ThemeContract.View, ThemeAdapter.MyClickListener {

    @BindView(R.id.theme_fragment_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.theme_fragment_retry_button)
    Button mRetry;

    @BindView(R.id.theme_fragment_relative_layout)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.theme_fragment_recycler_view)
    RecyclerView mRecyclerView;

    List<Topics> mListTopics;

//    private SharedPreference mSharedPreference;
    private ThemeContract.Presenter mPresenter;

    public ThemeFragment() {
        // Required empty public constructor
    }

    public static ThemeFragment newInstance() {
        return new ThemeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.theme_fragment, container, false);
        ButterKnife.bind(this, view);
        mPresenter.fetch();
//        mSharedPreference = SharedPreference.getInstance(getActivity().getApplicationContext());
        return view;
    }

    @Override
    public void setPresenter(ThemeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.theme_fragment_retry_button)
    public void retry(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetry.setVisibility(View.GONE);
        mPresenter.fetch();
    }

    @Override
    public void showTopics(List<Topics> list) {
        mListTopics = list;
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ThemeAdapter adapter = new ThemeAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mRetry.setVisibility(View.VISIBLE);
//        Utils.customSnackBar(getActivity().getString(R.string.no_connection), R.color.colorCancel, mRelativeLayout);
        mRetry.setText(getString(R.string.retry));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            mRetry.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onItemClick(int position, View v) {
//        mSharedPreference.put(THEME, String.valueOf(mListTopics.get(position).getId()));
//        startActivity(new Intent(getActivity(), LevelActivity.class));
    }

}
